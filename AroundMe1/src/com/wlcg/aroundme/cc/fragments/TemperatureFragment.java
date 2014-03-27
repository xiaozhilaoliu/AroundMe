package com.wlcg.aroundme.cc.fragments;

import com.wlcg.aroundme.cc.callback.DataCallBack;
import com.wlcg.aroundme.cc.chartview.TemperatureChar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TemperatureFragment extends Fragment implements DataCallBack{
	private final static String TAG = "TemperatureFragment";
	private TemperatureChar tChar = null;
	private View v = null;
	
	public TemperatureFragment(Context c) {
		tChar = new TemperatureChar();
		v = tChar.getChartView(c);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return v;
	}
	@Override
	public void dataChanged(Object data) {
		Log.i(TAG, data+"");
	}
}