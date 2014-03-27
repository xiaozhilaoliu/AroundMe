package com.wlcg.aroundme.cc.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WeatherinfoDao {
	private SQLiteDatabase db = null;
	public WeatherinfoDao(Context context){
		if((db == null) || (!db.isOpen())){
			db =context.openOrCreateDatabase("cityname.db", Context.MODE_PRIVATE, null);
		}
	}
	
	public String getCityIdByName(String cityName){
		Cursor cursor = db.rawQuery("select city_num from citys where name="+"?", new String[]{cityName});
		cursor.moveToFirst();
		System.out.println(cursor.getString(0));
		return null;
	}
}
