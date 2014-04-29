package com.manydesigns.portofino.report.jxl;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Range;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnmergeCells {

	public static final Logger S_LOGGER = LoggerFactory.getLogger(UnmergeCells.class);
	private static final String S_NULL = "";// "无";

	private static void copyExcle(String srcFile, String destFile) {
		Workbook readWorkBook = null;
		WritableWorkbook writeWorkBook = null;
		try {
			readWorkBook = Workbook.getWorkbook(new File(srcFile));
			writeWorkBook = Workbook.createWorkbook(new File(destFile), readWorkBook);// copy
			WritableSheet[] sheets = writeWorkBook.getSheets();

			for (WritableSheet sheet0 : sheets) {
				// 如果第一单元格是空的则删除第一列
				if ("Empty".equals(sheet0.getCell(0, 0).getType().toString())) {
					sheet0.removeColumn(0);
				}
				unmerge(sheet0);
				// processNoData(sheet0);
			}

			writeWorkBook.write();
			// writeWorkBook.close();
			// readWorkBook.close();
		} catch (Exception e) {
			S_LOGGER.error(e.getMessage(), e);
		} finally {
			closeFile(readWorkBook, writeWorkBook);
		}

	}

	protected static void unmerge(WritableSheet sheet0) throws WriteException, RowsExceededException {
		Range[] ranges = sheet0.getMergedCells();
		for (jxl.Range space : ranges) {
			// 把标题放到第一行
			sheet0.unmergeCells(space);
			String con = sheet0.getCell(space.getTopLeft().getColumn(), space.getTopLeft().getRow()).getContents();
			for (int row = space.getTopLeft().getRow(); row <= space.getBottomRight().getRow(); row++) {
				for (int col = space.getTopLeft().getColumn(); col <= space.getBottomRight().getColumn(); col++) {
					// Label(列号,行号 ,内容 )
					Label label = new Label(col, row, con);
					sheet0.addCell(label);
				}
			}
		}
	}

	protected static void processNoData(WritableSheet sheet0) throws WriteException, RowsExceededException {
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

	public static void closeFile(Workbook readWorkBook, WritableWorkbook writeWorkBook) {
		if (null != readWorkBook) {
			readWorkBook.close();
		}
		if (null != writeWorkBook) {
			try {
				writeWorkBook.close();
			} catch (WriteException e) {
				S_LOGGER.error(e.getMessage(), e);
			} catch (IOException e) {
				S_LOGGER.error(e.getMessage(), e);
			}
		}
	}

	public static void main(String[] args) {
		copyExcle("list.xls", "convert.xls");
	}
}
