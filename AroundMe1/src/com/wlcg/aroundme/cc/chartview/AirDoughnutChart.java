package com.wlcg.aroundme.cc.chartview;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

public class AirDoughnutChart extends AbstractChart{
	private double[] values = null;
	private int[]	colors = null;
	private CategorySeries  datasetCategorySeries;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Air  Chart Composition";
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "Air Composition About CO2 for this day";
	}

	public void updateDataset(double timeData){
		datasetCategorySeries.set(0, "二氧化碳浓度: "+timeData+"%", timeData);
		timeData = 100 - timeData;
		datasetCategorySeries.set(1, "其他: "+timeData+"%",timeData);
		
	}
	@Override
	public View getChartView(Context context) {
		if(datasetCategorySeries == null){
			values = new double[]{20,80};
		    datasetCategorySeries = buildCategoryDataset("air　Composition", values);
		}
		int[] colors = new int[]{Color.RED,Color.BLUE};
		DefaultRenderer renderer = buildCategoryRenderer(colors);
		renderer.setChartTitleTextSize(20);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.parseColor("#F5C271"));
		renderer.setDisplayValues(true);
		renderer.setShowLabels(false);
		
	    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(1);
	    r.setGradientEnabled(true);
	    r.setGradientStart(0, Color.BLUE);
	    r.setGradientStop(0, Color.GREEN);
	    
	    r.setHighlighted(true);
		
	    View view = ChartFactory.
	    		getPieChartView(context, datasetCategorySeries, renderer);
		return view;
	}
}
