package com.wlcg.aroundme.cc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	private Context context;
	private final String dataBasePath = "/data"+Environment.getDataDirectory().getAbsolutePath()
			+"/"+context.getPackageName()+"/databases/";
	public MyDatabaseHelper(Context context,String name,CursorFactory factory,int version){
		super(context, name, factory, version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
