package com.wlcg.aroundme.cc.callback;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Map;

import com.wlcg.aroundme.cc.SplashActivity;

import cc.wulian.ihome.wan.NetSDK;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class AppDownHandler implements UncaughtExceptionHandler{
	
	private static final String TAG = "AppDownHandler";
	
	private static AppDownHandler INSTANCE = new AppDownHandler();
	
	private Context mcontext;
	
	//system default uncaughtexception class
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	
	//storage exception infos
	private Map<String, String> infos = new HashMap<String, String>();
	
	public static AppDownHandler getInstance(){
		return INSTANCE;
	}
	
	public void init(Context context){
		mcontext = context;
		
		//get the default handler
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		
		//set the default handler
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.e(TAG, ex.toString());
		if(!handleException(ex) && mDefaultHandler != null)
			mDefaultHandler.uncaughtException(thread, ex);
		
		if(NetSDK.isConnected()){
			NetSDK.disconnect();
			NetSDK.uninit();
		}
		
//		android.os.Process.killProcess(android.os.Process.myPid());
//		System.exit(-1);
		
		//重新启动
		Intent intent = new Intent();
		intent.setClass(mcontext, SplashActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mcontext.startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
		
	}

	private boolean handleException(Throwable ex) {
		if(ex == null){
			return false;
		}
		
		//toast the exception
		new Thread(){
			public void run() {
				Looper.prepare();
				Toast.makeText(mcontext, "内存不给力，即将退出", Toast.LENGTH_LONG)
				.show();
				Looper.loop();
			};
		}.start();
		return true;
	}

}
