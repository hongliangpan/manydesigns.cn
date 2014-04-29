package com.manydesigns.portofino.report.demo;

import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.cnd;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.exp;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.grid;
import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.sbt;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;

import javax.xml.transform.Templates;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.jasper.constant.ImageType;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.builder.column.PercentageColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.ConditionalStyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

public class SimpleReport_Step12 {

	public SimpleReport_Step12() {

		build();

	}

	private void build() {

		CurrencyType currencyType = new CurrencyType();

		StyleBuilder boldStyle = stl.style().bold();

		StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);

		StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)

		.setBorder(stl.pen1Point())

		.setBackgroundColor(Color.LIGHT_GRAY);

		StyleBuilder titleStyle = stl.style(boldCenteredStyle)

		.setVerticalAlignment(VerticalAlignment.MIDDLE)

		.setFontSize(15);

		// title, field name data type

		TextColumnBuilder<String> itemColumn = col.column("Item", "item", type.stringType()).setStyle(boldStyle);

		TextColumnBuilder<Integer> quantityColumn = col.column("数量", "quantity", type.integerType());

		TextColumnBuilder<BigDecimal> unitPriceColumn = col.column("单价", "unitprice", currencyType);

		// price = unitPrice * quantity

		TextColumnBuilder<BigDecimal> priceColumn = unitPriceColumn.multiply(quantityColumn).setTitle("Price")

		.setDataType(currencyType);

		PercentageColumnBuilder pricePercColumn = col.percentageColumn("Price %", priceColumn);

		TextColumnBuilder<Integer> rowNumberColumn = col.reportRowNumberColumn("No.")

		// sets the fixed width of a column, width = 2 * character width

				.setFixedColumns(2)

				.setHorizontalAlignment(HorizontalAlignment.CENTER);

		Bar3DChartBuilder itemChart = cht.bar3DChart()

		.setTitle("销售情况")

		.setCategory(itemColumn)

		.addSerie(

		cht.serie(unitPriceColumn), cht.serie(priceColumn));

		Bar3DChartBuilder itemChart2 = cht.bar3DChart()

		.setTitle("销售情况")

		.setCategory(itemColumn)

		.setUseSeriesAsCategory(true)

		.addSerie(

		cht.serie(unitPriceColumn), cht.serie(priceColumn));

		ColumnGroupBuilder itemGroup = grp.group(itemColumn);

		itemGroup.setPrintSubtotalsWhenExpression(exp.printWhenGroupHasMoreThanOneRow(itemGroup));

		ConditionalStyleBuilder condition1 = stl.conditionalStyle(cnd.greater(priceColumn, 150))

		.setBackgroundColor(new Color(210, 255, 210));

		ConditionalStyleBuilder condition2 = stl.conditionalStyle(cnd.smaller(priceColumn, 30))

		.setBackgroundColor(new Color(255, 210, 210));

		ConditionalStyleBuilder condition3 = stl.conditionalStyle(cnd.greater(priceColumn, 200))

		.setBackgroundColor(new Color(0, 190, 0))

		.bold();

		ConditionalStyleBuilder condition4 = stl.conditionalStyle(cnd.smaller(priceColumn, 20))

		.setBackgroundColor(new Color(190, 0, 0))

		.bold();

		StyleBuilder priceStyle = stl.style()

		.conditionalStyles(

		condition3, condition4);

		priceColumn.setStyle(priceStyle);

		try {

			JasperReportBuilder report = report()
					// create new report design

					.setColumnTitleStyle(columnTitleStyle)

					.setSubtotalStyle(boldStyle)

					.highlightDetailEvenRows()

					.columns(// add columns

							rowNumberColumn, itemColumn, quantityColumn, unitPriceColumn, priceColumn, pricePercColumn)

					.columnGrid(

					rowNumberColumn, quantityColumn, unitPriceColumn,
							grid.verticalColumnGridList(priceColumn, pricePercColumn))

					.groupBy(itemGroup)

					.subtotalsAtSummary(

					sbt.sum(unitPriceColumn), sbt.sum(priceColumn))

					.subtotalsAtFirstGroupFooter(

					sbt.sum(unitPriceColumn), sbt.sum(priceColumn))

					.detailRowHighlighters(

					condition1, condition2)

					.title(// shows report title

					cmp.horizontalList()

							.add(

							cmp.image(Templates.class.getResource("images/dynamicreports.png")).setFixedDimension(80,
									80),

									cmp.text("中文DynamicReports").setStyle(titleStyle)
											.setHorizontalAlignment(HorizontalAlignment.LEFT),

									cmp.text("中文Getting started").setStyle(titleStyle)
											.setHorizontalAlignment(HorizontalAlignment.RIGHT))

							.newRow()

							.add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))

					.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))// shows number of page at page footer

					.summary(

					cmp.horizontalList(itemChart, itemChart2))

					.setDataSource(createDataSource())// set datasource

			// .show()// create and show report

			;
			exportDoc(report);
			report.show();
		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void exportDoc(JasperReportBuilder report) throws FileNotFoundException {
		try {
			// 生成PDF文件
			report.toDocx(new FileOutputStream("d:/test/report.docx"));
			report.toImage(new FileOutputStream("d:/test/report.png"), ImageType.PNG);
			report.toPdf(new FileOutputStream("d:/test/report.pdf"));
			report.toXlsx(new FileOutputStream("d:/test/report.xlsx"));
			// report.toHtml(new FileOutputStream("d:/test/report.html"))
			;

			JasperHtmlExporterBuilder htmlExporter = export.htmlExporter(new FileOutputStream("d:/test/report.html"))
					.setImagesURI("image?image=");

			report.toHtml(htmlExporter);

			// 显示报表
			report.show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	private class CurrencyType extends BigDecimalType {

		private static final long serialVersionUID = 1L;

		@Override
		public String getPattern() {

			return "$ #,###.00";

		}

	}

	private JRDataSource createDataSource() {

		DRDataSource dataSource = new DRDataSource("item", "quantity", "unitprice");

		dataSource.add("笔记本", 1, new BigDecimal(500));

		dataSource.add("DVD", 5, new BigDecimal(30));

		dataSource.add("DVD", 1, new BigDecimal(28));

		dataSource.add("DVD", 5, new BigDecimal(32));

		dataSource.add("图书", 3, new BigDecimal(11));

		dataSource.add("图书", 1, new BigDecimal(15));

		dataSource.add("图书", 5, new BigDecimal(10));

		dataSource.add("图书", 8, new BigDecimal(9));

		return dataSource;

	}

	public static void main(String[] args) {

		new SimpleReport_Step12();

	}

}
