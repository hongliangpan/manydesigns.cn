package com.riil.itsboard.chart;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

public class StackedBarChart3DDemo2 extends ApplicationFrame {
	public StackedBarChart3DDemo2(String s) {
		super(s);
		CategoryDataset categorydataset = createDataset();
		JFreeChart jfreechart = createChart(categorydataset);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartpanel);
	}

	public CategoryDataset createDataset() {
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		defaultcategorydataset.addValue(10D, "Series   1 ", "C1 ");
		defaultcategorydataset.addValue(5D, "Series   1 ", "C2 ");
		defaultcategorydataset.addValue(6D, "Series   1 ", "C3 ");
		defaultcategorydataset.addValue(7D, "Series   1 ", "C4 ");
		defaultcategorydataset.addValue(8D, "Series   1 ", "C5 ");
		defaultcategorydataset.addValue(9D, "Series   1 ", "C6 ");
		defaultcategorydataset.addValue(10D, "Series   1 ", "C7 ");
		defaultcategorydataset.addValue(11D, "Series   1 ", "C8 ");
		defaultcategorydataset.addValue(3D, "Series   1 ", "C9 ");
		defaultcategorydataset.addValue(0D, "Series   2 ", "C1 ");
		defaultcategorydataset.addValue(7D, "Series   2 ", "C2 ");
		defaultcategorydataset.addValue(17D, "Series   2 ", "C3 ");
		defaultcategorydataset.addValue(15D, "Series   2 ", "C4 ");
		defaultcategorydataset.addValue(6D, "Series   2 ", "C5 ");
		defaultcategorydataset.addValue(8D, "Series   2 ", "C6 ");
		defaultcategorydataset.addValue(9D, "Series   2 ", "C7 ");
		defaultcategorydataset.addValue(13D, "Series   2 ", "C8 ");
		defaultcategorydataset.addValue(7D, "Series   2 ", "C9 ");

		return defaultcategorydataset;
	}

	private JFreeChart createChart(CategoryDataset categorydataset) {
		JFreeChart jfreechart = ChartFactory.createStackedBarChart3D("Stacked   Bar   Chart   3D   Demo   2 ",
				"Category ", "Value ", categorydataset, PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		StackedBarRenderer3D stackedbarrenderer3d = (StackedBarRenderer3D) categoryplot.getRenderer();
		stackedbarrenderer3d.setRenderAsPercentages(true);
		stackedbarrenderer3d.setDrawBarOutline(false);
		stackedbarrenderer3d.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{3} ", NumberFormat
				.getIntegerInstance(), new DecimalFormat("0.0% ")));
		stackedbarrenderer3d.setItemLabelsVisible(true);
		stackedbarrenderer3d.setPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER,
				TextAnchor.CENTER));
		stackedbarrenderer3d.setNegativeItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.CENTER,
				TextAnchor.CENTER));
		return jfreechart;
	}

	public static void main(String args[]) {
		StackedBarChart3DDemo2 stackedbarchart3ddemo2 = new StackedBarChart3DDemo2(
				"Stacked   Bar   Chart   3D   Demo   2 ");
		stackedbarchart3ddemo2.pack();
		RefineryUtilities.centerFrameOnScreen(stackedbarchart3ddemo2);
		stackedbarchart3ddemo2.setVisible(true);
	}
}