package com.poi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * 解析XML
 * @author weique.lqf
 *
 */
public class XmlParse implements CommParseInerface {
	
	private static SAXBuilder saxBuilder = new SAXBuilder();
	private String filePath = null;
	
	public XmlParse(String filePath) {
		this.filePath = filePath;
	}
	
	public  Map<String, String> parseDate() {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			resultMap = this.buildRichPage(this.buildDocument(filePath));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultMap;
	}
	/**
	 * build jdom document
	 * 
	 * @param xmlText
	 *            xml text
	 * @return JDom document
	 * @throws java.io.IOException
	 *             IO Exception
	 * @throws org.jdom.JDOMException
	 *             JDom exception
	 */
	private Document buildDocument(String filePath) throws JDOMException, IOException {
		FileInputStream in = new FileInputStream(filePath);// 载入文档
		return saxBuilder.build(in);
	}
	
	
	/**
	 * 从document构建RichPage对象，如果xml包含prototype信息，会覆盖页面对象的prototypeId
	 * 
	 * @param document
	 *            jdom document对象
	 * @param userPageDO
	 *            用户页面对象
	 * @return rich page对象
	 */
	private Map<String, String> buildRichPage(Document document) {
		Element pageElement = document.getRootElement();
		Map<String, String> xmlMap = new HashMap<String, String>();
		//String key = pageElement.getAttributeValue("category");
		List<Element> insertModuleElements = pageElement.getChildren("BugPattern");
		if (insertModuleElements != null) {
			for (Element insertModuleElement : insertModuleElements) {
				String key = insertModuleElement.getAttributeValue("type");
				Element elments = insertModuleElement.getChild("Details");
				String details = elments.getText();
				if(details != null) {
					xmlMap.put(key.trim(), details.trim());
				}
			}
		}
		return xmlMap;
	}
	
	private  void printAllValues(Map<String, String> keyMap) {
		for (Map.Entry<String, String> temp : keyMap.entrySet()) {
			System.out.println(temp.getKey() + "===" + temp.getValue());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = "D:\\myword\\KPI\\messages.xml";
		// System.out.println(judgeChina(filePath));
		//buildDocument(filePath);
		XmlParse xmlParse = new XmlParse(filePath);
		try {
			xmlParse.printAllValues(xmlParse.buildRichPage(xmlParse.buildDocument(filePath)));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
