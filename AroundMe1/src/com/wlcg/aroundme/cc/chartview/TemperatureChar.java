package com.wlcg.aroundme.cc.chartview;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.RangeBarChart;
import org.achartengine.model.RangeCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

public class TemperatureChar extends AbstractChart {
	private XYMultipleSeriesDataset dataset = null;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Temperature chart";
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "show the temperature chart";
	}

	@Override
	public View getChartView(Context context) {
		double[] minValues = new double[] { -24, -19, -10, -1, 7, 12, 15, 14,
				9, 1, -11, -16 };
		double[] maxValues = new double[] { 7, 12, 24, 28, 33, 35, 37, 36, 28,
				19, 11, 4 };
		if (dataset == null) {
			dataset = new XYMultipleSeriesDataset();
			RangeCategorySeries series = new RangeCategorySeries("Temperature");
			int length = minValues.length;
			for (int k = 0; k < length; k++) {
				series.add(minValues[k], maxValues[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		int[] colors = new int[] { Color.BLUE };
		XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
		setChartSettings(renderer, "Day temperature range", "Time",
				"Temperature", 0, 12, -30, 45, Color.YELLOW, Color.BLUE);

		renderer.setBarSpacing(0.5);
		renderer.setXLabels(8);
		renderer.setYLabels(10);
		renderer.addXTextLabel(1, "0-1ʱ");
		renderer.addXTextLabel(2, "1-2ʱ");
		renderer.addXTextLabel(3, "2-3ʱ");
		renderer.addXTextLabel(4, "3-4ʱ");
		renderer.addXTextLabel(5, "4-5ʱ");
		renderer.addXTextLabel(6, "5-6ʱ");
		renderer.addXTextLabel(7, "6-7ʱ");
		renderer.addXTextLabel(8, "7-8ʱ");
		renderer.addXTextLabel(9, "8-9ʱ");
		renderer.addXTextLabel(10, "9-10ʱ");
		renderer.addXTextLabel(11, "10-11ʱ");
		renderer.addXTextLabel(12, "11-12ʱ");
		renderer.addXTextLabel(13, "12-13ʱ");
		renderer.addXTextLabel(14, "13-14ʱ");
		renderer.addXTextLabel(15, "14-15ʱ");
		renderer.addXTextLabel(16, "15-16ʱ");
		renderer.addXTextLabel(17, "16-17ʱ");
		renderer.addXTextLabel(18, "17-18ʱ");
		renderer.addXTextLabel(19, "18-19ʱ");
		renderer.addXTextLabel(20, "19-20ʱ");
		renderer.addXTextLabel(21, "20-21ʱ");
		renderer.addXTextLabel(22, "21-22ʱ");
		renderer.addXTextLabel(23, "22-23ʱ");
		renderer.addXTextLabel(24, "23-0ʱ");
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.parseColor("#F5C271"));
		renderer.setMarginsColor(Color.parseColor("#F5C271"));
		renderer.setFitLegend(true);
		renderer.setXLabelsColor(Color.BLUE);
		renderer.setYLabelsColor(0, Color.BLUE);
		// renderer.setMargins(new int[] {30, 70, 10, 0});

		renderer.setYLabelsAlign(Align.RIGHT);
		SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
		r.setDisplayChartValues(true);
		r.setChartValuesTextSize(12);
		r.setChartValuesSpacing(3);
		r.setGradientEnabled(true);
		r.setGradientStart(-20, Color.GREEN);
		r.setGradientStop(20, Color.RED);
		return ChartFactory.getRangeBarChartView(context, dataset, renderer,
				Type.STACKED);
	}

}
