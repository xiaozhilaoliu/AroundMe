package com.wlcg.aroundme.cc;

import com.wlcg.aroundme.cc.callback.AppDownHandler;

import android.app.Application;

public class AroundMeApplication extends Application{
	public static String temp_data = "";
	public static String huminity_data = "hahahaha";
	public static String air_data = "";
	@Override
	public void onCreate() {
		super.onCreate();
		AppDownHandler appDownHandler = AppDownHandler.getInstance();
		appDownHandler.init(getApplicationContext());
	}
}
