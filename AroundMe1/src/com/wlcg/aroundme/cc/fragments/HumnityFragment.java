package com.wlcg.aroundme.cc.fragments;

import com.wlcg.aroundme.cc.callback.DataCallBack;
import com.wlcg.aroundme.cc.chartview.HuminityChart;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HumnityFragment extends Fragment implements DataCallBack{
	private final static String TAG = "HumnityFragment";
	private View v = null;
	private HuminityChart huminityChart = null;
	public HumnityFragment(Context context) {
		huminityChart = new HuminityChart();
		v = huminityChart.getChartView(context);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		return v;
	}

	@Override
	public void dataChanged(Object data) {
		Log.i(TAG, data+"");
		try {
			if(huminityChart != null){
				huminityChart.updateDataset(Double.parseDouble(data.toString()));
				v.invalidate();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			Log.i(TAG, e.toString());
		}
	}
}