package com.wlcg.aroundme.cc.chartview;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.DialRenderer.Type;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

public class HuminityChart extends AbstractChart{
	private CategorySeries category = null;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "huminity chart";
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "huminity chart";
	}
	
	public void updateDataset(double data){
		Log.i("HuminityChart", data+"");
		if(category != null)
			category.set(0, "Current",data);
	}

	@Override
	public View getChartView(Context context) {
		if(category == null){
			category = new CategorySeries("Huminity chart");
	    	category.add("Current", 50);
	    	category.add("Minimum", 80);
	    	category.add("Maximum", 20);
		}
	    DialRenderer renderer = new DialRenderer();
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setMargins(new int[] {20, 30, 15, 0});
	    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	    r.setColor(Color.BLUE);
	    renderer.addSeriesRenderer(r);
	    r = new SimpleSeriesRenderer();
	    r.setColor(Color.rgb(0, 150, 0));
	    renderer.addSeriesRenderer(r);
	    r = new SimpleSeriesRenderer();
	    r.setColor(Color.GREEN);
	    renderer.addSeriesRenderer(r);
	    renderer.setLabelsTextSize(10);
	    renderer.setLabelsColor(Color.WHITE);
	    renderer.setShowLabels(true);
	    renderer.setVisualTypes(new DialRenderer.Type[] {Type.ARROW, Type.NEEDLE, Type.NEEDLE});
	    renderer.setMinValue(0);
	    renderer.setMaxValue(100);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.parseColor("#F5C271"));
		
		return ChartFactory.getDialChartView(context, category, renderer);
	}

}
