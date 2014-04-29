package com.manydesigns.portofino.report.jxl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xls转sql工具
 * 
 * @author greatghoul@gmail.com
 */
public class Excel2SqlConvertor {

	public static final Logger S_LOGGER = LoggerFactory.getLogger(UnmergeCells.class);

	/**
	 * 将xls文件按照指定的sql语句模板转换成sql脚本。
	 * 
	 * @param xlsFile xls文件路径
	 * @param sqlFile 要输出的sql脚本文件路径
	 * @param template sql语句模板
	 * @param sheetIndex xls中标签页的序号(从0开始)
	 * @param start 转换开始的行数 (从0开始)
	 * @param end 转换结束的行数 (从0开始)
	 * @throws IOException
	 * @throws BiffException
	 */
	public static void convert(String xlsFile, String sqlFile, String template, int sheetIndex, int start, int end)
			throws IOException, BiffException {
		// 获取工作薄
		Workbook workbook = Workbook.getWorkbook(new File(xlsFile));
		Sheet sheet = workbook.getSheet(sheetIndex);

		// 获取sql中的所有字段点位符
		Matcher matcher = Pattern.compile("(:\\d)").matcher(template);
		List<Integer> columns = new ArrayList();
		while (matcher.find()) {
			columns.add(Integer.valueOf(matcher.group().replace(":", "")));
		}

		// 输出sql脚本文件
		PrintWriter writer = new PrintWriter(new FileWriter(sqlFile));
		println("Writing sql statements to file: " + sqlFile);
		println("-------------------------------------------------------------------------------");
		int rowCount = 0;
		for (int i = 0, j = sheet.getRows(); i < j; i++) {
			if (i < start - 1 || i >= end) {
				continue;
			}
			// 组装sql语句
			String line = new String(template);
			for (Integer column : columns) {
				line = line.replace(":" + column, sheet.getCell(column, i).getContents());
			}
			println(line);
			writer.println(line);
			rowCount++;
		}
		writer.flush();
		writer.close();
		println("-------------------------------------------------------------------------------");
		System.out.format("Converting completed. %d row(s) in total.%n", rowCount);

		// Runtime.getRuntime().exec("notepad.exe " + sqlFile);
	}

	private static void println(String msg) {
		S_LOGGER.info(msg);
	}

	public static void main(String[] args) {
		String xlsFile = "d:\\规划所花名册(人力资源部提供）编制.XLS";
		String sqlFile = "d:\\规划所花名册(人力资源部提供）编制-事业.SQL";
		// 其中:0 :2 :6 为值所在excel的列号
		String template = "INSERT INTO PS_USER(ID, NAME, BIRTHDAY) VALUES(:0, ':2', ':6');";
		try {
			Excel2SqlConvertor.convert(xlsFile, sqlFile, template, 2, 2, 133);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}