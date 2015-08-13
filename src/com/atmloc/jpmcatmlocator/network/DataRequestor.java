package com.atmloc.jpmcatmlocator.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class DataRequestor {

	public static RequestQueue requestQueue;
	
	public static void networkRequest(Context cont,String url){
		
		requestQueue = Volley.newRequestQueue(cont);
		StringRequest req = new StringRequest(Request.Method.GET,url,onNetworkSuccess(),onNetworkError());
		req.setRetryPolicy(new HttpTimeOut(10000));
		requestQueue.add(req);		
	}
	
	
	private static Response.Listener<String> onNetworkSuccess() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("Response", "Response"+response);
				//sendCallback(response);
			}
		};
	}

	private static Response.ErrorListener onNetworkError() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.d("VolleyError", "VolleyError"+error);
			}
		};
	}
	

	
}
