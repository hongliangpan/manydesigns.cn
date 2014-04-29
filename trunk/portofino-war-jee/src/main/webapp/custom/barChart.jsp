<%@ page language="java" contentType="text/html;charset=GBK" pageEncoding="utf-8"%>
<%@ page import="org.jfree.chart.*,org.jfree.chart.plot.*,org.jfree.chart.labels.*,
org.jfree.data.category.*,java.awt.*,org.jfree.ui.*,org.jfree.chart.renderer.category.BarRenderer3D,
org.jfree.chart.servlet.*,org.jfree.chart.plot.PlotOrientation,org.jfree.data.general.DatasetUtilities"%>
<%
double[][] data = new double[][] {{1230,1110,1120,1210}, {720,750,860,800}, {830,780,790,700,}, {400,380,390,450}};
String[] rowKeys = {"苹果", "香蕉", "橘子", "梨子"};
String[] columnKeys = {"鹤壁","西安","深圳","北京"};
CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data); 

JFreeChart chart = ChartFactory.createBarChart3D("水果销量统计图", 
                  "水果",
                  "销量",
                  dataset,
                  PlotOrientation.VERTICAL,
                  true,
                  true,
                  false);
CategoryPlot plot = chart.getCategoryPlot();  
//设置网格背景颜色
plot.setBackgroundPaint(Color.white);
//设置网格竖线颜色
plot.setDomainGridlinePaint(Color.pink);
//设置网格横线颜色
plot.setRangeGridlinePaint(Color.pink);

//显示每个柱的数值，并修改该数值的字体属性
BarRenderer3D renderer = new BarRenderer3D();
renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
renderer.setBaseItemLabelsVisible(true);

//默认的数字显示在柱子中，通过如下两句可调整数字的显示
//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
renderer.setItemLabelAnchorOffset(10D);

//设置每个地区所包含的平行柱的之间距离
renderer.setItemMargin(0.4);
plot.setRenderer(renderer);

//设置地区、销量的显示位置
//将下方的“肉类”放到上方
//plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
//将默认放在左边的“销量”放到右方
//plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);


String filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, null, session);
System.out.println(filename);
String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;
%>
<img src="<%= graphURL %>" width=530 height=320 border=0>