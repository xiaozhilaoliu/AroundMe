package com.wlcg.aroundme.cc.fragments;

import com.wlcg.aroundme.cc.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;

public class MainFragment extends Fragment implements OnClickListener{

	private Button humidity_button,
				temp_button,
				air_button,
				weather_button;
	public MainFragment(){}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View main_view = inflater.inflate(R.layout.main_activity,container,false);
		humidity_button = (Button) main_view.findViewById(R.id.humidity);
		temp_button = (Button) main_view.findViewById(R.id.temp);
		air_button = (Button) main_view.findViewById(R.id.air);
		weather_button = (Button) main_view.findViewById(R.id.weather);
		return main_view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		humidity_button.setOnClickListener(this);
		temp_button.setOnClickListener(this);
		air_button.setOnClickListener(this);
		weather_button.setOnClickListener(this);
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		
	}

	@Override
	public void onClick(View v) {
		if(R.id.humidity == v.getId()){
			
		}else if (R.id.temp == v.getId()) {
			
		}else if (R.id.air == v.getId()) {
			
		}else if (R.id.weather == v.getId()) {
			
		}
	}
}
