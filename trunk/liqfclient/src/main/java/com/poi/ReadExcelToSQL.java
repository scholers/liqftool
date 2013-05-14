package com.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.net.pic.util.FileBean;
import com.net.pic.util.FileUtil;
 
/**
 * ��֧��excel2007�Լ��Ժ�İ汾
 * @author weique.lqf
 *
 */
public class ReadExcelToSQL {
	private Map<String, ZhongjiangAcc> renMap = new HashMap<String, ZhongjiangAcc>();
	
	public ReadExcelToSQL(boolean isInit) {
		if(isInit) {
			renMap = readExcelAccount("D:\\temp\\2.xlsx");
		}
	}
	public ReadExcelToSQL() {
		
	}
	
	public Map<String, ZhongjiangAcc> getRenMap() {
		return renMap;
	}


	private static Properties p_sql = new Properties();
	static {
		try {

			// ���������ļ�
			InputStream in = ReadExcelToSQL.class
					.getResourceAsStream("/sqlConfig.properties");
			p_sql.load(in);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			System.out.println("û��/sqlConfig.properties�����ļ���");
		}
	}
	
	/**
	 * ������
	 * 
	 * @param fileName
	 *            Ҫ��ȡ��excel�ļ��ĵ�ַ
	 * @param tableName
	 *            ��������ݿ�������
	 */
	public  Map<String, ZhongjiangAcc> readExcelAccount(String fileName) {
		
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
		// ��ȡexcel�й�����������
		int workBooks = workbook.getNumberOfSheets();
		// ѭ������ļ����еĹ�����
		for (int i = 0; i < workBooks; i++) {
			XSSFSheet sheet = workbook.getSheetAt(i);
			Iterator<Row> rows = sheet.rowIterator();
			
			while (rows.hasNext()) {
				ZhongjiangAcc zhongjiangAcc = new ZhongjiangAcc();
				XSSFRow row = (XSSFRow) rows.next();
				Iterator<Cell> cells = row.cellIterator();
				int columAt = 0;// ����е��±�
				while (cells.hasNext()) {
					
					String columTemp = readCellValue2((XSSFCell) cells.next(), true);
					columTemp = columTemp.replaceAll("\"", "");
					columTemp = columTemp.replaceAll(",", "");
					if(columAt == 0) {
						zhongjiangAcc.setEmployee(columTemp);
					}
					if(columAt == 1) {
						zhongjiangAcc.setEmail(columTemp);
					}
					if(columAt == 2) {
						zhongjiangAcc.setAccount(columTemp);
					}
					columAt++;
				}
				//System.out.println(zhongjiangAcc.getAccount().trim());
				renMap.put(zhongjiangAcc.getAccount().trim(), zhongjiangAcc);
				
			}
		}
		return renMap;
	}
	
	
	/**
	 * ������
	 * 
	 * @param fileName
	 *            Ҫ��ȡ��excel�ļ��ĵ�ַ
	 * @param tableName
	 *            ��������ݿ�������
	 */
	public  void readExcelAccount2(String fileName) {
		
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
		// ��ȡexcel�й�����������
		int workBooks = workbook.getNumberOfSheets();
		
		// ѭ������ļ����еĹ�����
		for (int i = 0; i < workBooks; i++) {
			XSSFSheet sheet = workbook.getSheetAt(i);
			Iterator<Row> rows = sheet.rowIterator();
			
			while (rows.hasNext()) {
				StringBuilder strBuild = new StringBuilder("<li class=\"clearfix\">\n");
				ZhongjiangAcc zhongjiangAcc = new ZhongjiangAcc();
				XSSFRow row = (XSSFRow) rows.next();
				Iterator<Cell> cells = row.cellIterator();
				int columAt = 0;// ����е��±�
				while (cells.hasNext()) {
					
					String columTemp = readCellValue2((XSSFCell) cells.next(), true);
					columTemp = columTemp.replaceAll("\"", "");
					columTemp = columTemp.replaceAll(",", "");
					if(columAt == 1) {
						zhongjiangAcc.setName(columTemp);
						strBuild.append("<span class=\"u-nick\">"+columTemp+"</span>\n");
					}
					if(columAt == 2) {
						zhongjiangAcc.setDate(columTemp);
						strBuild.append("<span class=\"u-time\">�н�"+columTemp+"��</span>\n");
					}
					if(columAt == 3) {
						zhongjiangAcc.setGoods(columTemp);
						strBuild.append("<span class=\"u-getnum\">���"+columTemp+"���ֱ�</span>\n");
					}
					columAt++;
				}
				
				
				
				//renMap.put(zhongjiangAcc.getAccount().trim(), zhongjiangAcc);
				strBuild.append("</li>\n");
				System.out.println(strBuild.toString());
			}
		}
		
	}

	/**
	 * ������
	 * 
	 * @param fileName
	 *            Ҫ��ȡ��excel�ļ��ĵ�ַ
	 * @param tableName
	 *            ��������ݿ�������
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
		// ��ȡexcel�й�����������
		int workBooks = workbook.getNumberOfSheets();
		// ѭ������ļ����еĹ�����
		for (int i = 0; i < workBooks; i++) {
			XSSFSheet sheet = workbook.getSheetAt(i);
			Iterator<Row> rows = sheet.rowIterator();
			// ��һ��ͨ���洢sql������
			XSSFRow fristRow = null;
			if (rows.hasNext()) {
				fristRow = (XSSFRow) rows.next();
			} else {
				break;
			}
			// ƴ�Ӳ�������ǰ��
			StringBuffer starSqlTemp = new StringBuffer();
			starSqlTemp.append("insert into " + tableName + " (");
			Iterator<Cell> fristCells = fristRow.cellIterator();
			// ���sql��� ��colum����
			ArrayList<String> sqlColum = new ArrayList<String>();
			while (fristCells.hasNext()) {
				XSSFCell cell = (XSSFCell) fristCells.next();
				String columTemp = readCellValue(cell, null, false);
				starSqlTemp.append(columTemp + ",");// ƴ��sql���ǰ��
				sqlColum.add(columTemp);
			}
			String starSql = starSqlTemp.substring(0, starSqlTemp.length() - 1)
					+ ") values (";
			while (rows.hasNext()) {
				StringBuffer sqlTemp = new StringBuffer();
				sqlTemp.append(starSql);
				XSSFRow row = (XSSFRow) rows.next();
				Iterator<Cell> cells = row.cellIterator();
				int columAt = 0;// ����е��±�
				while (cells.hasNext()) {
					String columTemp = readCellValue((XSSFCell) cells.next(),
							sqlColum.get(columAt), true);
					String columDiscrpt = p_sql.get(sqlColum.get(columAt)
							+ "_REPLACE") != null ? p_sql.get(
							sqlColum.get(columAt) + "_REPLACE").toString()
							: null;
					// ����������ļ����� ���ڴ��е���Ϣ�����滻
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
				// ���sql
				System.out.println(sql);
			}
		}
		
		try {
			String textFileName = "input_" + DateUtil.formatDate(new Date(),"yyyy-MM-dd") + ".txt";
			FileUtil.toFile(fileList, "d://temp//", textFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ȡֵ
	 * 
	 * @param cell
	 *            ��
	 * 
	 * @param columName
	 *            ���������ݿ��е�����
	 * @param bool
	 *            �Ƿ��������ƴ�� ���� �� ���ţ�true ƴ��
	 * @return
	 */
	private static String readCellValue(XSSFCell cell, String columName,
			Boolean bool) {
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_NUMERIC:
			Double d = cell.getNumericCellValue();
			// ��ȡ�����ļ��е� �Դ��е�type����
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
			// ��ʽ
		case XSSFCell.CELL_TYPE_FORMULA:
			throw new RuntimeException("�ݲ�֧�ֹ�ʽ��" + cell.getCellFormula());
			// break;
			// δ֪����
		case XSSFCell.CELL_TYPE_BLANK:
			return bool ? "''," : "";
		default:
			throw new RuntimeException("δ֪�����ͣ�" + cell.getCellType());
			// System.out.println("unsuported sell type");
			// break;
		}
	}
	
	/**
	 * ȡֵ
	 * 
	 * @param cell
	 *            ��
	 * 
	 * @param columName
	 *            ���������ݿ��е�����
	 * @param bool
	 *            �Ƿ��������ƴ�� ���� �� ���ţ�true ƴ��
	 * @return
	 */
	private static String readCellValue2(XSSFCell cell,
			Boolean bool) {
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_NUMERIC:
			Double d = cell.getNumericCellValue();
			
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
			// ��ʽ
		case XSSFCell.CELL_TYPE_FORMULA:
			throw new RuntimeException("�ݲ�֧�ֹ�ʽ��" + cell.getCellFormula());
			// break;
			// δ֪����
		case XSSFCell.CELL_TYPE_BLANK:
			return bool ? "''," : "";
		default:
			throw new RuntimeException("δ֪�����ͣ�" + cell.getCellType());
			// System.out.println("unsuported sell type");
			// break;
		}
	}

	public static void main(String[] args) {
		//readExcel("D:\\temp\\2.xlsx", "employee_alipay");
		new ReadExcelToSQL(true);
		ReadExcelToSQL test = new ReadExcelToSQL();
		test.readExcelAccount2("D:\\temp\\6.xlsx");
		//new "D:\\temp\\2.xlsx";
	}
}
