package com.manydesigns.portofino.report.demo;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.grid;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.manydesigns.portofino.report.Templates;

public class PdfReportServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/pdf");

		OutputStream out = resp.getOutputStream();

		try {
			StyleBuilder textStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point());

			TextColumnBuilder<String> itemColumn = col.column("Item", "item", type.stringType());
			TextColumnBuilder<Integer> quantityColumn = col.column("数量", "quantity", type.integerType());
			TextColumnBuilder<BigDecimal> unitPriceColumn = col.column("单价", "unitprice", type.bigDecimalType());
			TextColumnBuilder<Date> orderDateColumn = col.column("Order date", "orderdate", type.dateType());
			TextColumnBuilder<Date> orderDateFColumn = col.column("Order date", "orderdate",
					type.dateYearToFractionType());
			TextColumnBuilder<Date> orderYearColumn = col.column("Order year", "orderdate", type.dateYearType());
			TextColumnBuilder<Date> orderMonthColumn = col.column("Order month", "orderdate", type.dateMonthType());
			TextColumnBuilder<Date> orderDayColumn = col.column("Order day", "orderdate", type.dateDayType());

			report().setTemplate(Templates.reportTemplate)
					.setColumnStyle(textStyle)
					.columns(itemColumn, quantityColumn, unitPriceColumn, orderDateColumn, orderDateFColumn,
							orderYearColumn, orderMonthColumn, orderDayColumn)
					.columnGrid(
							grid.verticalColumnGridList(itemColumn,
									grid.horizontalColumnGridList(quantityColumn, unitPriceColumn)),
							grid.verticalColumnGridList(orderDateColumn,
									grid.horizontalColumnGridList(orderDateFColumn, orderYearColumn),
									grid.horizontalColumnGridList(orderMonthColumn, orderDayColumn)))
					.title(Templates.createTitleComponent("ColumnGrid")).pageFooter(Templates.footerComponent)
					.setDataSource(createDataSource())

					.toPdf(out);

		} catch (DRException e) {

			throw new ServletException(e);

		}

		out.close();

	}

	private JRDataSource createDataSource() {
		DRDataSource dataSource = new DRDataSource("item", "orderdate", "quantity", "unitprice");
		dataSource.add("Notebook", new Date(), 1, new BigDecimal(500));
		dataSource.add("Book", new Date(), 7, new BigDecimal(300));
		dataSource.add("PDA", new Date(), 2, new BigDecimal(250));
		return dataSource;
	}
}
