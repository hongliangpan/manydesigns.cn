package com.manydesigns.portofino.report.jxl;

import java.io.File;

import jxl.Cell;
import jxl.Range;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Chaifen {
	private static final String S_NULL = "";// "无";

	private static void copyExcle(String srcFile, String destFile) {
		try {
			String fileName = srcFile;

			Workbook rwb = Workbook.getWorkbook(new File(fileName));

			WritableWorkbook wwb = Workbook.createWorkbook(new File(destFile), rwb);// copy
			WritableSheet[] sheets = wwb.getSheets();

			for (WritableSheet sheet0 : sheets) {
				// 如果第一单元格是空的则删除第一列
				if ("Empty".equals(sheet0.getCell(0, 0).getType().toString())) {
					sheet0.removeColumn(0);
				}

				Range[] ranges = sheet0.getMergedCells();

				for (jxl.Range space : ranges) {
					// 把标题放到第一行
					sheet0.unmergeCells(space);
					String con = sheet0.getCell(space.getTopLeft().getColumn(), space.getTopLeft().getRow())
							.getContents();

					for (int row = space.getTopLeft().getRow(); row <= space.getBottomRight().getRow(); row++) {
						for (int col = space.getTopLeft().getColumn(); col <= space.getBottomRight().getColumn(); col++) {
							// Label(列号,行号 ,内容 )
							Label label = new Label(col, row, con);

							sheet0.addCell(label);
						}
					}
				}

				int row = sheet0.getRows();

				for (int i = 0; i < row; i++) {

					Cell c[] = sheet0.getRow(i);
					if (null == c) {
						continue;
					}

					for (int j = 0; j < c.length; j++) {
						if ("Empty".equals(sheet0.getCell(j, i).getType().toString())) {
							// Label(列号,行号 ,内容 )
							Label label = new Label(j, i, S_NULL);

							sheet0.addCell(label);
						}
						if ("".equals(sheet0.getCell(j, i).getContents().trim())) {
							// Label(列号,行号 ,内容 )
							Label label = new Label(j, i, S_NULL);

							sheet0.addCell(label);
						}
					}
					for (int j = c.length; j < 5; j++) {
						// Label(列号,行号 ,内容 )
						Label label = new Label(j, i, S_NULL);

						sheet0.addCell(label);
					}

				}

			}

			wwb.write();
			wwb.close();
			rwb.close();
		} catch (Exception e) {
			System.out.println("--------->" + e.getMessage());
		}

	}

	public static void main(String[] args) {
		copyExcle("list.xls", "convert.xls");
	}
}
