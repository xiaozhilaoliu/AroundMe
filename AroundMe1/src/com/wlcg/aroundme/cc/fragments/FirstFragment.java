package com.wlcg.aroundme.cc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FirstFragment extends Fragment {
	
	public FirstFragment() {
		
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if(savedInstanceState != null){
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		View v = inflater.inflate(R.layout.fragment_item,container,false);
//		TextView tv = (TextView)v.findViewById(R.id.location);
		TextView tv = new TextView(getActivity());
		tv.setText("first");
		return tv;
	}
	
}
