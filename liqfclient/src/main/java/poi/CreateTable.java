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
	 * ��ȡ���
	 */
	public static void readWord() {

		try {
			FileInputStream in = new FileInputStream("E:\\test.doc");//�����ĵ�   
			POIFSFileSystem pfs = new POIFSFileSystem(in);
			HWPFDocument hwpf = new HWPFDocument(pfs);
			Range range = hwpf.getRange();//�õ��ĵ��Ķ�ȡ��Χ   
			TableIterator it = new TableIterator(range);
			//�����ĵ��еı��   
			while (it.hasNext()) {
				Table tb = (Table) it.next();
				StringBuffer strBuf = null;
				//�����У�Ĭ�ϴ�0��ʼ   
				for (int i = 0; i < tb.numRows(); i++) {
					strBuf = new StringBuffer();
					TableRow tr = tb.getRow(i);
					//�����У�Ĭ�ϴ�0��ʼ   
					for (int j = 0; j < tr.numCells(); j++) {
						
						TableCell td = tr.getCell(j);//ȡ�õ�Ԫ��   
						//ȡ�õ�Ԫ�������   
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
