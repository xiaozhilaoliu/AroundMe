package com.wlcg.aroundme.cc.fragments;

import com.wlcg.aroundme.cc.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ThirdFragment extends Fragment {
	private final static String TAG = "ThirdFragment";
	private TextView tv = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tv = new TextView(getActivity());
		tv.setText("Third");
		tv.setId(0x11);
		Log.i(TAG, "onCreate");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView"+container.getId());
		if(container.findViewById(0x11) != null){
			
		}
//		View v = inflater.inflate(R.layout.fragment_item,container);
//		TextView tv = new TextView(getActivity());
//		TextView tv = (TextView) v.findViewById(R.id.location);
//		tv.setText("Third");\
		return tv;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.i(TAG, "onAttach");
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "onActivityCreated");
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i(TAG, "onStart");
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, "onResume");
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(TAG, "onStop");
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.i(TAG, "onDestroyView");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Log.i(TAG, "onDetach");
	}
}
