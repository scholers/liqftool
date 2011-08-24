package poi;

import java.io.FileInputStream;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;



import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class CreateTable {

	/**
	 * 读取表格
	 */
	public static void readWord() {

		try {
			FileInputStream in = new FileInputStream("E:\\test.doc");//载入文档   
			POIFSFileSystem pfs = new POIFSFileSystem(in);
			HWPFDocument hwpf = new HWPFDocument(pfs);
			Range range = hwpf.getRange();//得到文档的读取范围   
			TableIterator it = new TableIterator(range);
			//迭代文档中的表格   
			while (it.hasNext()) {
				Table tb = (Table) it.next();
				StringBuffer strBuf = null;
				//迭代行，默认从0开始   
				for (int i = 0; i < tb.numRows(); i++) {
					strBuf = new StringBuffer();
					TableRow tr = tb.getRow(i);
					//迭代列，默认从0开始   
					for (int j = 0; j < tr.numCells(); j++) {
						
						TableCell td = tr.getCell(j);//取得单元格   
						//取得单元格的内容   
						for (int k = 0; k < td.numParagraphs(); k++) {
							Paragraph para = td.getParagraph(k);
							//String s = para.text();
							strBuf.append(para.text().trim() + " ");
							//System.out.println(strBuf.toString());
							
						} //end for 
						
					} //end for   
					System.out.println(strBuf.toString());
				} //end for   
			} //end while   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end method        

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		readWord();
	}
}
