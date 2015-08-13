package com.atmloc.jpmcatmlocator;

import com.atmloc.jpmcatmlocator.data.Location;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	private Location locationData;
	private TextView loc_name,loc_state,loc_pin,loc_type,loc_bank,location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_frag);
		loc_name =(TextView)findViewById(R.id.loca_name_value);
		location=(TextView)findViewById(R.id.loca_location_value);
		loc_state =(TextView)findViewById(R.id.loca_state_value);
		loc_pin =(TextView)findViewById(R.id.loca_pin_value);
		loc_type =(TextView)findViewById(R.id.loca_type_value);
		loc_bank =(TextView)findViewById(R.id.loca_bank_value);
		
		locationData = JPMCATMLocator.getInstance().selectedDetails_data;
		loc_name.setText(locationData.getName());
		location.setText(locationData.getLocType());
		loc_state.setText(locationData.getState());
		loc_pin.setText(locationData.getZip());
		if(locationData.getType()!=null){
			loc_type.setText(locationData.getType());	
		}else{
			loc_type.setVisibility(View.GONE);
		}
		
		loc_bank.setText(locationData.getBank());
		
		
		Log.d("locationData", "locationData"+locationData.getName());
		
	}
}
