package com.wlcg.aroundme.cc.util;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import cc.wulian.ihome.wan.NetSDK;
import cc.wulian.ihome.wan.entity.DeviceInfo;
import cc.wulian.ihome.wan.util.MD5Util;
import cc.wulian.ihome.wan.util.StringUtil;

import com.wlcg.aroundme.cc.AroundMeApplication;
import com.wlcg.aroundme.cc.callback.HandleCallBack;
import com.wlcg.aroundme.cc.fragments.AirFragment;
import com.wlcg.aroundme.cc.fragments.HumnityFragment;
import com.wlcg.aroundme.cc.fragments.TemperatureFragment;
import com.wlcg.aroundme.cc.fragments.WeatherFragment;
import com.wlcg.aroundme.cc.util.widget.MyAppWidgetProvider;

public class NetControl {
	private static NetControl 			netControl;
	public static HandleCallBack 		handleCallBack;
	public static MessageHandle 		mHandle;
	private AsyncTask<Void, Void, Void> aTask;
	private HeartTask 					mHeartTask;
	private Context 					context;
	private String     					mAppID;
	private Handler						netHandler;
	public static int 					isInitSuccess = -1;
	
	public NetControl(Context context){
		this.context = context;
	}

	public static NetControl getInstance(Context context){
		if(netControl == null){
			netControl = new NetControl(context);
		}
		
		return netControl;
	}
	public void InitUtil(Handler netHandler){
		mHandle = new MessageHandle();
		handleCallBack = new HandleCallBack(mHandle,netHandler);
		this.netHandler = handleCallBack.getNetHandler();
		
		isInitSuccess = NetSDK.init(handleCallBack);				//≥ı ºªØSDK
		mHeartTask = new HeartTask();
		mHeartTask.startTimer();
		
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		mAppID = tm.getDeviceId();
		if (StringUtil.isNullOrEmpty(mAppID)){
			mAppID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		}
	}
	
	public void attemptSignin(final String user_name,final String user_code){
		aTask = new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				netHandler.sendEmptyMessageDelayed(-1, 20 * 1000);
			}
			@Override
			protected Void doInBackground(Void... params) {
				if(!handleCallBack.isConnectSev){
					System.out.println("NetSDK.connect");// LOG
					NetSDK.connect();
				}
				
				System.out.println("NetSDK.sendConnectGwMsg");// LOG
				NetSDK.sendConnectGwMsg(mAppID, "1", user_name, MD5Util.encrypt(user_code));
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				aTask = null;
			}
		};
		aTask.execute();
	}
	
	
	private HumnityFragment humnityFragment = null;
	private AirFragment airFragment = null;
	private WeatherFragment weatherFragment = null;
	private TemperatureFragment temperatureFragment = null;
	
	public void registFragment(HumnityFragment humnityFragment,AirFragment airFragment,
			WeatherFragment weatherFragment,
			TemperatureFragment temperatureFragment){
		this.humnityFragment = humnityFragment;
		this.airFragment = airFragment;
		this.weatherFragment = weatherFragment;
		this.temperatureFragment = temperatureFragment;
	}
	public class MessageHandle extends Handler {
		public static final int MSG_CONNECT_FAILED = 0;
		public static final int MSG_CONNECT_SUCCESS = 1;
		public static final int MSG_DEVICE_UP = 2;
		public static final int MSG_DEVICE_DOWN = 3;
		public static final int MSG_DEVICE_DATA = 4;

		@Override
		public void handleMessage(Message msg) {
			if (MSG_CONNECT_FAILED == msg.what) {

			} else if (MSG_CONNECT_SUCCESS == msg.what) {
			} else if (MSG_DEVICE_UP == msg.what) {
				DeviceInfo deviceInfo = (DeviceInfo)msg.obj;
				if(airFragment!= null && deviceInfo.getType().equals("42")){
					airFragment.dataChanged(deviceInfo.getDevEPInfo().getEpData());
					AroundMeApplication.air_data = deviceInfo.getDevEPInfo().getEpData();
					MyAppWidgetProvider.updateWidgetView(deviceInfo.getDevEPInfo().getEpData(), Constants.AIR_UPDATE);
				
				}
				
				if(deviceInfo.getType().equals("17")){
					String data = deviceInfo.getDevEPInfo().getEpData();
					String[] datas = data.split(",");
					String temp = datas[0];
					String humnity = datas[1];
					
					MyAppWidgetProvider.updateWidgetView(humnity, Constants.HUMINITY_UPDATE);
					MyAppWidgetProvider.updateWidgetView(temp, Constants.TEMP_UPDATE);
					
					AroundMeApplication.temp_data = temp;
					AroundMeApplication.huminity_data = humnity;
					if(temperatureFragment != null){
						temperatureFragment.dataChanged(temp);
					}
					if(humnityFragment != null){
						humnityFragment.dataChanged(humnity);
					}
				}
			} else if (MSG_DEVICE_DOWN == msg.what) {
			} else if (MSG_DEVICE_DATA == msg.what) {
				DeviceInfo deviceInfo = (DeviceInfo) msg.obj;
				System.out.println("================="+deviceInfo.getName());
				if(airFragment!= null && deviceInfo.getDevEPInfo().getEpData().equals("42")){
					airFragment.dataChanged(deviceInfo.getDevEPInfo().getEpData());
					AroundMeApplication.air_data = deviceInfo.getDevEPInfo().getEpData();
					MyAppWidgetProvider.updateWidgetView(deviceInfo.getDevEPInfo().getEpData(), Constants.AIR_UPDATE);
				}
				
				if(deviceInfo.getDevEPInfo().getEpData().equals("17")){
					String data = deviceInfo.getDevEPInfo().getEpData();
					String[] datas = data.split(",");
					String temp = datas[0];
					String humnity = datas[1];
					AroundMeApplication.huminity_data = humnity;
					AroundMeApplication.temp_data = temp;
					
					MyAppWidgetProvider.updateWidgetView(temp, Constants.TEMP_UPDATE);
					MyAppWidgetProvider.updateWidgetView(humnity, Constants.HUMINITY_UPDATE);
					if(temperatureFragment != null){
						temperatureFragment.dataChanged(temp);
					}
					if(humnityFragment != null){
						humnityFragment.dataChanged(humnity);
					}
				}
			}
		}
	}
}
