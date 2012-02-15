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
 * ����WORD
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
	 * ��ȡ���
	 */
	public  Map<String, String> parseDate() {
		Map<String, String> keyMap = new HashMap<String, String>();
		try {
			FileInputStream in = new FileInputStream(filePath);// �����ĵ�
			POIFSFileSystem pfs = new POIFSFileSystem(in);
			HWPFDocument hwpf = new HWPFDocument(pfs);
			Range range = hwpf.getRange();// �õ��ĵ��Ķ�ȡ��Χ
			TableIterator it = new TableIterator(range);
			// �����ĵ��еı��
			while (it.hasNext()) {
				Table tb = (Table) it.next();

				// �����У�Ĭ�ϴ�0��ʼ
				for (int i = 0; i < tb.numRows(); i++) {

					TableRow tr = tb.getRow(i);
					// �����У�Ĭ�ϴ�0��ʼ
					for (int j = 0; j < tr.numCells(); j++) {

						TableCell td = tr.getCell(j);// ȡ�õ�Ԫ��
						String bugKeyStr = "";
						StringBuilder strBuf = new StringBuilder();
						// ȡ�õ�Ԫ�������
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
	 * �жϵ�һ���ַ��Ƿ�Ϊ����
	 * 
	 * @param content
	 *            ���������
	 * @return
	 */
	public  int judgeChina(String content) {
		// Integer index = 0;

		// StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < content.length(); i++) {
			String retContent = content.substring(i, i + 1);
			// ����һ��Pattern,ͬʱ����һ��������ʽ
			boolean isChina = retContent.matches("[\u4E00-\u9FA5]");
			if (isChina) {
				return i + 1;
			}

		}
		// û������
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
		String filePath = "D:\\myword\\KPI\\FindBugs��������_���İ�.doc";
		// System.out.println(judgeChina(filePath));
		//readWord(filePath);
		//printAllValues();
	}
}