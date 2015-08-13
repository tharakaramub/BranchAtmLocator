package com.atmloc.jpmcatmlocator;
import java.io.IOException;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atmloc.jpmcatmlocator.data.LocationListData;
import com.atmloc.jpmcatmlocator.location.GPSLocation;
import com.atmloc.jpmcatmlocator.network.HttpTimeOut;
import com.atmloc.jpmcatmlocator.utils.Utils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends FragmentActivity {

	private Button getLocation;
	private GoogleMap googleMap;	
	private GPSLocation gps;
	private double latitude;
	private double longitude;
	private LinearLayout maps_lay,details_lay;
	private RelativeLayout main_lay;
	private static LocationListData locData;
	private  RequestQueue requestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		main_lay = (RelativeLayout)findViewById(R.id.main_lay);
		maps_lay =(LinearLayout)findViewById(R.id.map_lay);
		details_lay= (LinearLayout)findViewById(R.id.list_lay);
		getLocation=(Button)findViewById(R.id.getLocation_btn);

		getLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// create class object
				gps = new GPSLocation(MainActivity.this);

				if (gps.canGetLocation) {

					latitude = gps.getLatitude();
					longitude = gps.getLongitude();
					//latitude = 40.1373386;
					//longitude = -82.9880188;

					/*Toast.makeText(
							getApplicationContext(),
							"Your Location is - \nLat: " + latitude
							+ "\nLong: " + longitude, Toast.LENGTH_LONG)
							.show();*/
					String serverURL = "https://m.chase.com/PSRWeb/location/list.action?lat="+latitude+"&lng="+longitude;
					Log.d("serverURL", "serverURL"+serverURL);
					networkRequest(serverURL);

				} else {
					gps.showSettingsAlert();
				}

			}
		});

	}

	private void showLocationMarker(final LocationListData locListData){

		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
		if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
			dialog.show();

		}else {        	

			SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

			// Getting GoogleMap object from the fragment
			googleMap = fm.getMap();

			googleMap.setMyLocationEnabled(true);           

			double lat = 0;
			double lng = 0;

			for(int i=0;i<locListData.getLocations().size();i++){

				lat = locListData.getLocations().get(i).getLat();
				lng = locListData.getLocations().get(i).getLng();
				Log.d("Location", "Location "+locData.getLocations().get(i).getLat());
				drawMarker(new LatLng(lat, lng),locListData.getLocations().get(i).getName());    				
			}

			googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat("10"))); 
		}

		googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				if(marker.getPosition()!= null){
					JPMCATMLocator.getInstance().loc_Name = marker.getTitle();
					for(int i=0; i<locListData.getLocations().size();i++){
						if(locListData.getLocations().get(i).getName().equalsIgnoreCase(marker.getTitle())){
							JPMCATMLocator.getInstance().selectedDetails_data =  locListData.getLocations().get(i);
						}
					}
				}


				Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
				startActivity(intent);

				return true;
			}
		});

	}


	private void drawMarker(LatLng point,String name){
		// Creating an instance of MarkerOptions
		MarkerOptions markerOptions = new MarkerOptions();					
		markerOptions.position(point).title(name);
		googleMap.addMarker(markerOptions);    		
	}



	public  void networkRequest(String url){

		requestQueue = Volley.newRequestQueue(MainActivity.this);
		StringRequest req = new StringRequest(Request.Method.GET,url,onNetworkSuccess(),onNetworkError());
		req.setRetryPolicy(new HttpTimeOut(10000));
		requestQueue.add(req);		
	}


	private  Response.Listener<String> onNetworkSuccess() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("Response", "Response"+response);
				main_lay.setVisibility(View.GONE);
				maps_lay.setVisibility(View.VISIBLE);
				sendCallback(response);
			}
		};
	}

	private  Response.ErrorListener onNetworkError() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.d("VolleyError", "VolleyError"+error);
			}
		};
	}


	private void sendCallback(String response){
		try {
			locData = new LocationListData();
			locData = (LocationListData) Utils.fromJson(response, LocationListData.class);
			if(locData.getLocations() != null && locData.getLocations().size()>0){
				showLocationMarker(locData);
			}

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

