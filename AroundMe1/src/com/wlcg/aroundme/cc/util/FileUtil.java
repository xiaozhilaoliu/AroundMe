package com.wlcg.aroundme.cc.util;

import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;

public class FileUtil {
	public  static void copyDatabase(Context context) {
		String dataBasePath = "/data"+Environment.getDataDirectory().getAbsolutePath()
				+"/"+context.getPackageName()+"/databases/";
		InputStream inputStream = null;
		FileOutputStream fiOutputStream = null;
		try {
			inputStream = context.getAssets().open("cityname.db");
			fiOutputStream = new FileOutputStream(dataBasePath+"cityname.db");
			byte[] buffer = new byte[1024];
			int length = 0;
			while((length = inputStream.read(buffer)) != -1){
				fiOutputStream.write(buffer, 0, length);
			}
			fiOutputStream.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				inputStream.close();
				fiOutputStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
