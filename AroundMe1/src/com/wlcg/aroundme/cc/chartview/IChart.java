package com.wlcg.aroundme.cc.chartview;

import android.content.Context;
import android.view.View;

public interface IChart {
	
	/**
	 * define the chart
	 */
	
	/**chart name*/
	String NAME = "name";
	
	/**chart desc*/
	String DESC = "desc";
	
	/**
	 * return the chart name.
	 * @return return the chart name.
	 */
	String getName();
	
	/**
	 * return the char desc.
	 * @return	return the char desc.
	 */
	String getDesc();
	
	/**
	 * return the chart view
	 * @param context the context
	 * @return the build chart view
	 */
	View getChartView(Context context);
}
