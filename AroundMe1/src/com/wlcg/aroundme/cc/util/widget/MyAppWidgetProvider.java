package com.wlcg.aroundme.cc.util.widget;

import com.wlcg.aroundme.cc.AroundMeApplication;
import com.wlcg.aroundme.cc.R;
import com.wlcg.aroundme.cc.util.Constants;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyAppWidgetProvider extends AppWidgetProvider{
	private static RemoteViews remoteViews = null;
	private PendingIntent pendingIntent = null;
	private static AppWidgetManager manager = null;
	private static int[] appWidgetIds= null;

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if(intent.getAction().equals("com.wlcg.aroundme.widget.click")){
			Toast.makeText(context, "点击控件了", Toast.LENGTH_SHORT)
			.show();
		}

	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		manager = appWidgetManager;
		remoteViews = new RemoteViews(context.getPackageName()
				,R.layout.appwidget);
		MyAppWidgetProvider.appWidgetIds = appWidgetIds;
		Intent intent = new Intent("com.wlcg.aroundme.widget.click");
		pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		remoteViews.setOnClickPendingIntent(R.id.tv_air, pendingIntent);
		
		updateWidgetView(AroundMeApplication.air_data, Constants.AIR_UPDATE);
		updateWidgetView(AroundMeApplication.huminity_data, Constants.HUMINITY_UPDATE);
		updateWidgetView(AroundMeApplication.temp_data, Constants.TEMP_UPDATE);
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	public static void updateWidgetView(String data,byte argv){
		if(remoteViews != null && manager != null && appWidgetIds != null){
			switch (argv) {
			case Constants.HUMINITY_UPDATE:
				try {
					remoteViews.setTextViewText(R.id.tv_humi, "湿度是:"+data+" %RH");
					manager.updateAppWidget(appWidgetIds, remoteViews);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case Constants.AIR_UPDATE:
				try {
					remoteViews.setTextViewText(R.id.tv_air, "二氧化碳浓度:"+data+"PPM");
					manager.updateAppWidget(appWidgetIds, remoteViews);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case Constants.TEMP_UPDATE:
				try {
					remoteViews.setTextViewText(R.id.tv_temp, "温度:"+data+"°C");
					manager.updateAppWidget(appWidgetIds, remoteViews);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			Log.i("data", "data is "+data);

		}
		

	}
}
