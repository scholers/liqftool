package com.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.net.pic.util.FileBean;
import com.net.pic.util.FileUtil;
 
/**
 * 仅支持excel2007以及以后的版本
 * @author weique.lqf
 *
 */
public class ReadExcelToSQL {
	private static Properties p_sql = new Properties();
	static {
		try {

			// 加载配置文件
			InputStream in = ReadExcelToSQL.class
					.getResourceAsStream("/sqlConfig.properties");
			p_sql.load(in);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			System.out.println("没有/sqlConfig.properties配置文件。");
		}
	}

	/**
	 * 主方法
	 * 
	 * @param fileName
	 *            要读取的excel文件的地址
	 * @param tableName
	 *            导入的数据库表的名称
	 */
	public static void readExcel(String fileName, String tableName) {
		List<FileBean> fileList = new ArrayList<FileBean>();
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 读取excel中工作簿的总数
		int workBooks = workbook.getNumberOfSheets();
		// 循环这个文件所有的工作簿
		for (int i = 0; i < workBooks; i++) {
			XSSFSheet sheet = workbook.getSheetAt(i);
			Iterator<Row> rows = sheet.rowIterator();
			// 第一行通常存储sql的列名
			XSSFRow fristRow = null;
			if (rows.hasNext()) {
				fristRow = (XSSFRow) rows.next();
			} else {
				break;
			}
			// 拼接插入语句的前段
			StringBuffer starSqlTemp = new StringBuffer();
			starSqlTemp.append("insert into " + tableName + " (");
			Iterator<Cell> fristCells = fristRow.cellIterator();
			// 存放sql语句 的colum列名
			ArrayList<String> sqlColum = new ArrayList<String>();
			while (fristCells.hasNext()) {
				XSSFCell cell = (XSSFCell) fristCells.next();
				String columTemp = readCellValue(cell, null, false);
				starSqlTemp.append(columTemp + ",");// 拼接sql语句前段
				sqlColum.add(columTemp);
			}
			String starSql = starSqlTemp.substring(0, starSqlTemp.length() - 1)
					+ ") values (";
			while (rows.hasNext()) {
				StringBuffer sqlTemp = new StringBuffer();
				sqlTemp.append(starSql);
				XSSFRow row = (XSSFRow) rows.next();
				Iterator<Cell> cells = row.cellIterator();
				int columAt = 0;// 标记列的下标
				while (cells.hasNext()) {
					String columTemp = readCellValue((XSSFCell) cells.next(),
							sqlColum.get(columAt), true);
					String columDiscrpt = p_sql.get(sqlColum.get(columAt)
							+ "_REPLACE") != null ? p_sql.get(
							sqlColum.get(columAt) + "_REPLACE").toString()
							: null;
					// 如果在配置文件中有 关于此列的信息，则替换
					if (null != columDiscrpt) {
						sqlTemp.append(columDiscrpt.replace("?", columTemp
								.subSequence(0, columTemp.length() - 1))
								+ ",");
					} else {
						sqlTemp.append(columTemp);
					}
					columAt++;
				}
				String sql = sqlTemp.substring(0, sqlTemp.length() - 1) + ");";
				FileBean fileBean = new FileBean();
				fileBean.setFileName(sql);
				fileList.add(fileBean);
				// 输出sql
				System.out.println(sql);
			}
		}
		
		try {
			FileUtil.toFile(fileList, "d://", "input.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 取值
	 * 
	 * @param cell
	 *            列
	 * 
	 * @param columName
	 *            此列在数据库中的列名
	 * @param bool
	 *            是否根据类型拼接 引号 和 逗号，true 拼接
	 * @return
	 */
	private static String readCellValue(XSSFCell cell, String columName,
			Boolean bool) {
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_NUMERIC:
			Double d = cell.getNumericCellValue();
			// 获取配置文件中的 对此列的type类型
			String type = p_sql.get(columName + "_TYPE") != null ? p_sql.get(
					columName + "_TYPE").toString() : null;
			if (null == type) {
				return d.longValue() + ",";
			}
			if ("string".equals(type)) {
				return "\"" + d.longValue() + "\",";
			} else if ("double".equals(type)) {
				return d + ",";
			} else if (type.indexOf("string") != -1
					&& type.indexOf("double") != -1) {
				return "\"" + d + "\",";
			}

			return d.longValue() + ",";
		case XSSFCell.CELL_TYPE_STRING:
			String temp = cell.getStringCellValue().replace(" ", "");
			if (!bool) {
				return temp;
			}
			if (temp.startsWith("(") && temp.endsWith(")")) {
				return temp + ",";
			} else {
				return "\"" + temp + "\",";
			}
		case XSSFCell.CELL_TYPE_BOOLEAN:
			return bool ? "\"" + cell.getBooleanCellValue() + "\"," : cell
					.getBooleanCellValue() + "";
			// 公式
		case XSSFCell.CELL_TYPE_FORMULA:
			throw new RuntimeException("暂不支持公式！" + cell.getCellFormula());
			// break;
			// 未知类型
		case XSSFCell.CELL_TYPE_BLANK:
			return bool ? "''," : "";
		default:
			throw new RuntimeException("未知的类型！" + cell.getCellType());
			// System.out.println("unsuported sell type");
			// break;
		}
	}

	public static void main(String[] args) {
		readExcel("D:\\1.xlsx", "sg_dim_main_auc");
	}
}
