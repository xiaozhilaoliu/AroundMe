package com.wlcg.aroundme.cc.baidumap;

import android.content.Context;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.wlcg.aroundme.cc.fragments.WeatherFragment.MyLocationListener;

public class LocationManager {
	
	private final static String Tag = LocationManager.class.getSimpleName();
	
	private LocationClient mLocationClient = null;

	public LocationManager(Context context,MyLocationListener myLocationListener) {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);	//设置定位的模式
		option.setCoorType("bd0911");
		option.setScanSpan(5000);
		option.setIsNeedAddress(true);
		option.setNeedDeviceDirect(true);  //返回的数据中包况手机机头的方向
		mLocationClient = new LocationClient(context);
		mLocationClient.registerLocationListener(myLocationListener);
		mLocationClient.setLocOption(option);
	}
	public void startLocation() {
		mLocationClient.start();
		if (mLocationClient != null && mLocationClient.isStarted()){
			mLocationClient.requestLocation();
		}else{ 
		 Log.d(Tag, "locClient is null or not started");
		}
	}
	
	public void stopLocation(){
		if(mLocationClient.isStarted()){
			mLocationClient.stop();
		}
	}
}
