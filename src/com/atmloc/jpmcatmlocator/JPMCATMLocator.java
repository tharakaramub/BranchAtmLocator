package com.atmloc.jpmcatmlocator;

import java.util.List;

import com.atmloc.jpmcatmlocator.data.Location;
import com.atmloc.jpmcatmlocator.data.LocationListData;

import android.app.Application;

public class JPMCATMLocator extends Application{
	
	public static String  loc_Name;
	public static Location selectedDetails_data;
	public static JPMCATMLocator mInstance;
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}
	public static synchronized JPMCATMLocator getInstance() {
		return mInstance;
	}
}
