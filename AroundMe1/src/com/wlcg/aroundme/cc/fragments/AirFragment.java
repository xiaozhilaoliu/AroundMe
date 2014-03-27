package com.wlcg.aroundme.cc.fragments;

import com.wlcg.aroundme.cc.callback.DataCallBack;
import com.wlcg.aroundme.cc.chartview.AirDoughnutChart;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AirFragment extends Fragment implements DataCallBack{
	private final static String TAG = "AirFragment";
	private AirDoughnutChart airDoughnutChart;
	private Handler handler ;
	private double tiemDate = 0;
	private View v = null;
	
	public AirFragment(Context context) {
		airDoughnutChart = new AirDoughnutChart();
		v = airDoughnutChart.getChartView(context);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		handler = new Handler();
		System.out.println("AirFragment is create");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		 View v = inflater.inflate(R.layout.show_waves, container, false);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		System.out.println("AirFragment is detach");
		v.setVisibility(View.GONE);
		v.invalidate();
	}
	@Override
	public void dataChanged(Object data) {
		Log.i(TAG, "ahhahahhahaha"+data.toString());
		if(airDoughnutChart != null){
			airDoughnutChart.updateDataset(Double.parseDouble(data.toString()) / 10000);
			v.invalidate();
		}
	}
}