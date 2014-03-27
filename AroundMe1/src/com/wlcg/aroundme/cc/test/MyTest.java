package com.wlcg.aroundme.cc.test;

import com.wlcg.aroundme.cc.dao.WeatherinfoDao;
import com.wlcg.aroundme.cc.util.FileUtil;

import android.test.AndroidTestCase;

public class MyTest extends AndroidTestCase {
	
	public void testFile(){
		FileUtil.copyDatabase(getContext());
		new WeatherinfoDao(getContext()).getCityIdByName("±±¾©");
	}
}
