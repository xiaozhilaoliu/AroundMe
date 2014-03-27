package com.wlcg.aroundme.cc.fragments;

import com.wlcg.aroundme.cc.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		View v = inflater.inflate(R.layout.fragment_item,container,false);
		TextView tv = new TextView(getActivity());
		tv.setText("Second");
		return tv;
	}
	
}
