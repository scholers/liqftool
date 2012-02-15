package com.poi;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 解析WORD
 * @author weique.lqf
 *
 */
public class WordParse implements CommParseInerface {

	//private static Map<String, String> keyMap = new HashMap<String, String>();
	private String filePath = null;
	
	public WordParse(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * 读取表格
	 */
	public  Map<String, String> parseDate() {
		Map<String, String> keyMap = new HashMap<String, String>();
		try {
			FileInputStream in = new FileInputStream(filePath);// 载入文档
			POIFSFileSystem pfs = new POIFSFileSystem(in);
			HWPFDocument hwpf = new HWPFDocument(pfs);
			Range range = hwpf.getRange();// 得到文档的读取范围
			TableIterator it = new TableIterator(range);
			// 迭代文档中的表格
			while (it.hasNext()) {
				Table tb = (Table) it.next();

				// 迭代行，默认从0开始
				for (int i = 0; i < tb.numRows(); i++) {

					TableRow tr = tb.getRow(i);
					// 迭代列，默认从0开始
					for (int j = 0; j < tr.numCells(); j++) {

						TableCell td = tr.getCell(j);// 取得单元格
						String bugKeyStr = "";
						StringBuilder strBuf = new StringBuilder();
						// 取得单元格的内容
						for (int k = 0; k < td.numParagraphs(); k++) {
							Paragraph para = td.getParagraph(k);

							String text = para.text().trim();
							if (k == 0) {
								bugKeyStr = text;
							} else {
								strBuf.append(text);
							}
							// System.out.println("text==" + k + "...." + text);

						} // end for
						keyMap.put(bugKeyStr, strBuf.toString());
					} // end for
						// System.out.println(strBuf.toString());
				} // end for
			} // end while
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keyMap;
	}// end method

	/**
	 * 判断第一个字符是否为中文
	 * 
	 * @param content
	 *            输入的内容
	 * @return
	 */
	public  int judgeChina(String content) {
		// Integer index = 0;

		// StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < content.length(); i++) {
			String retContent = content.substring(i, i + 1);
			// 生成一个Pattern,同时编译一个正则表达式
			boolean isChina = retContent.matches("[\u4E00-\u9FA5]");
			if (isChina) {
				return i + 1;
			}

		}
		// 没有中文
		return -1;
	}

/*	private static void printAllValues() {
		for (Map.Entry<String, String> temp : keyMap.entrySet()) {
			System.out.println(temp.getKey() + "===" + temp.getValue());
		}
	}
	
	
	public static Map<String, String> getKeyMap() {
		return keyMap;
	}*/

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = "D:\\myword\\KPI\\FindBugs规则整理_中文版.doc";
		// System.out.println(judgeChina(filePath));
		//readWord(filePath);
		//printAllValues();
	}
}