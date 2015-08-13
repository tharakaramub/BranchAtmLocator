package com.atmloc.jpmcatmlocator.network;

import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

public class HttpTimeOut implements RetryPolicy{
	private int mTimeOut = 2500;

	public HttpTimeOut(int timeOut){
		this.mTimeOut = timeOut;
	}
	@Override
	public int getCurrentTimeout() {
		return mTimeOut;
	}

	@Override
	public int getCurrentRetryCount() {
		return 0;
	}

	@Override
	public void retry(VolleyError error) throws VolleyError {
		
	}

}
