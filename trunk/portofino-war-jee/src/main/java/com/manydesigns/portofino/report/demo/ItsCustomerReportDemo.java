package com.manydesigns.portofino.report.demo;

import static net.sf.dynamicreports.report.builder.DynamicReports.export;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.jasper.constant.ImageType;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class ItsCustomerReportDemo {

	private static Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		return (Connection) DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/itsboard200?useUnicode=true&amp;amp;characterEncoding=UTF-8", "riil",
				"r4rfde32wsaq1");
	}

	public static void buildReport(Connection conn) throws FileNotFoundException {
		JasperReportBuilder report = DynamicReports.report();// 创建空报表
		// 样式
		StyleBuilder boldStl = DynamicReports.stl.style().bold();
		StyleBuilder boldCenteredStl = DynamicReports.stl.style(boldStl).setHorizontalAlignment(
				HorizontalAlignment.CENTER);
		StyleBuilder titleStl = DynamicReports.stl.style(boldCenteredStl).setFontSize(16);
		StyleBuilder columnTitleStl = DynamicReports.stl.style(boldCenteredStl)
				.setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);
		report.columns(
				Columns.column("ID", "c_id", DataTypes.integerType()).setFixedColumns(3)
						.setHorizontalAlignment(HorizontalAlignment.CENTER),// 列
				Columns.column("客户名称", "c_name", DataTypes.stringType()).setFixedColumns(20),
				Columns.column("客户简称", "c_short_name", DataTypes.stringType()),
				Columns.column("片区", "c_region1", DataTypes.stringType()),
				Columns.column("大区", "c_region2", DataTypes.stringType()),
				Columns.column("省份", "c_region3", DataTypes.stringType()),
				Columns.column("行业", "c_industry1", DataTypes.stringType()))

		.setColumnTitleStyle(columnTitleStl).setHighlightDetailEvenRows(true)
				.title(Components.text("客户信息").setStyle(titleStl))// 标题
				.pageFooter(Components.pageXofY().setStyle(boldCenteredStl))// 页角
				.setDataSource("SELECT * FROM t_customer limit 200", conn);// 数据源
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

	public static void main(String orgs[]) throws FileNotFoundException, ClassNotFoundException, SQLException {
		Connection conn = getConn();
		buildReport(conn);
	}
}