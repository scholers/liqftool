package com.taobao.tools;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DefaultPrcXmlDoc implements XmlDocument {
	private Document document;
	private String fileName;
	private Vector<String> teamUIXmlFiles;
	static final int TEAM_UI = 0;
	
	private final static String firestStr = "<project";
	private final static String firestStrs = "<projects";
	private final static String startStr = "id=\"";
	private final static String endStr = "\"/>";
	private final static String dir = "dir=";
	private final static String version = "version";
	//判断是否是目录总控文件
	private final static String DEFAULT_SCOPE = "ALL";

	public static void ElementToStream(Element element, OutputStream out) {
		try {
			DOMSource source = new DOMSource(element);
			StreamResult result = new StreamResult(out);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);
		} catch (Exception ex) {
		}
	}

	/**
	 * 将dom中的document树生成一个string, 便于直接写入文件.
	 */
	public static String DocumentToString(Document doc) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ElementToStream(doc.getDocumentElement(), baos);
		return new String(baos.toByteArray());
	}

	public void init() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			this.document = builder.newDocument();
			teamUIXmlFiles = new Vector<String>();
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		}
	}

	public void parseUIS(int uiType) {
		String fileName = null;
		switch (uiType) {
		case TEAM_UI: {
			fileName = "X://uis.xml";
		}
			break;
		}

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(fileName);
			NodeList uis = document.getChildNodes();
			Node ui = uis.item(0);
			NodeList groups = ui.getChildNodes();
			int lenght = groups.getLength();
			for (int i = 0; i < lenght; i++) {
				Node group = groups.item(i);
				if (group.getNodeName() == "Group") {
					// System.out.println(i + " " + group.getNodeName());
					// System.out.println(i + " " +
					// group.getAttributes().getNamedItem("name").getNodeValue());
					if (group.getAttributes().getNamedItem("name").getNodeValue().equalsIgnoreCase("team")) {
						NodeList groupInfo = group.getChildNodes();
						for (int j = 0; j < groupInfo.getLength(); j++) {
							Node res = groupInfo.item(j);
							System.out.println(res.getNodeName());
							if (res.getNodeName() == "res") {
								// System.out.println(res.getNodeName());
								String xmlPath = res.getAttributes().getNamedItem("path").getNodeValue();
								// System.out.println(xmlPath);
								teamUIXmlFiles.add(xmlPath);
								replaceRichTextColor(xmlPath, 0x5299b7, 0xba3339);
							}
						}
					}
				}
			}
			System.out.println("解析完毕");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void replaceRichTextColor(String xmlPath, int newUnselectedBgRgb, int newSelectedBgRgb) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(xmlPath);
			NodeList screens = document.getChildNodes();
			Node screen = screens.item(0);
			NodeList uiElements = screen.getChildNodes();
			int lenght = uiElements.getLength();
			System.out.println("......" + xmlPath + "......");
			for (int i = 0; i < lenght; i++) {
				Node element = uiElements.item(i);
				System.out.println(i + " " + element.getNodeName());
				if (element.getNodeName() == "RichText") {
					System.out.println(i + " " + element.getAttributes().getNamedItem("usbgc").getNodeName());
					System.out.println(i + " " + element.getAttributes().getNamedItem("sbgc").getNodeName());
					element.getAttributes().getNamedItem("usbgc").setNodeValue(String.valueOf(newUnselectedBgRgb));
					element.getAttributes().getNamedItem("sbgc").setNodeValue(String.valueOf(newSelectedBgRgb));
					System.out.println(i + " " + element.getAttributes().getNamedItem("usbgc").getNodeValue());
					System.out.println(i + " " + element.getAttributes().getNamedItem("sbgc").getNodeValue());
				}
			}
			String whatisit = DocumentToString(document);
			System.out.println(whatisit);
			// System.out.println("解析完毕");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void createXml(String fileName) {
		
		Element root = this.document.createElement("dependencyManagement");
		this.document.appendChild(root);

		Element employee = this.document.createElement("dependencies");
		Element name = this.document.createElement("name");
		name.appendChild(this.document.createTextNode("丁宏亮"));
		employee.appendChild(name);

		Element sex = this.document.createElement("sex");
		sex.appendChild(this.document.createTextNode("m"));
		employee.appendChild(sex);

		Element age = this.document.createElement("age");
		age.appendChild(this.document.createTextNode("30"));
		employee.appendChild(age);
		root.appendChild(employee);

		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
			System.out.println("生成XML文件成功!");
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		}
		 
	}
	
	public void createXml(String fileName, List<PomBean> tempList) {
		
		Element root = this.document.createElement("dependencyManagement");
		this.document.appendChild(root);

		
		
		//循环创建xml的元素
		for(PomBean pomBean : tempList) {
			Element employee = this.document.createElement("dependencies");
			
			Element name = this.document.createElement("groupId");
			name.appendChild(this.document.createTextNode(pomBean.getGroupId()));
			employee.appendChild(name);
			
			Element artifactId = this.document.createElement("artifactId");
			artifactId.appendChild(this.document.createTextNode(pomBean.getArtifactId()));
			employee.appendChild(artifactId);
			
			if(pomBean.getVersion() != null && pomBean.getVersion().length() >  0) {
				Element version = this.document.createElement("version");
				version.appendChild(this.document.createTextNode(pomBean.getVersion()));
				employee.appendChild(version);
			}
			root.appendChild(employee);
		}

		

		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
			System.out.println("生成XML文件成功!");
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		}
		 
	}


	/**
	 * 生成XML文件
	 */
	public void parserXml(String fileName) {
		List<PomBean> tempList = new ArrayList<PomBean>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(fileName);
			NodeList employees = document.getChildNodes();
			
			for (int i = 0; i < employees.getLength(); i++) {
				Node employee = employees.item(i);
				NodeList employeeInfo = employee.getChildNodes();
				for (int j = 0; j < employeeInfo.getLength(); j++) {
					Node node = employeeInfo.item(j);
					NodeList employeeMeta = node.getChildNodes();
					tempList.addAll(parJars(employeeMeta));
				}
			}
			System.out.println("OK!");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		//生成xml
		createXml("D://TEST.XML", tempList);
	}

	
	/**
	 * 
	 * parJars(这里用一句话描述这个方法的作用)
	 * 解析PROJECT
	 * @param employeeMeta
	 * @return
	 * StringBuilder
	 * @since 1.0.0
	 */
	private List<PomBean> parJars(NodeList employeeMeta) {
		StringBuilder strBuilder = new StringBuilder();
		List<PomBean> tempList = new ArrayList<PomBean>();
		for (int k = 0; k < employeeMeta.getLength(); k++) {
			String strGroupId = null;
			String version = null;
			PomBean pomBean = null;
			if (employeeMeta.item(k).getAttributes() != null 
					&& employeeMeta.item(k).getAttributes().getNamedItem("id") != null
					&& employeeMeta.item(k).getAttributes().getNamedItem("dir") == null ) {
				strGroupId = employeeMeta.item(k).getAttributes().getNamedItem("id").getNodeValue();
				//跳过
				if(strGroupId.indexOf("binary-release") >= 0){
					continue;
				}
				//替换\为.
				strGroupId = strGroupId.replaceAll("/", ".");
				//version信息
				if(employeeMeta.item(k).getAttributes().getNamedItem("version") != null)
					version = employeeMeta.item(k).getAttributes().getNamedItem("version").getNodeValue();
				//生成pom.xml
				strBuilder.append("<dependency>\n");
				strBuilder.append("\t<groupId>");
				// 判断是否是toolkit
				if (strGroupId.indexOf("toolkit") >= 0) {
					strGroupId = "com.alibaba." + strGroupId;
				}
				// 判断是否是taobao
				else if (strGroupId.indexOf("taobao") >= 0) {
					strGroupId = "com." + strGroupId;
				}
				String artifactId = strGroupId.substring(strGroupId.lastIndexOf(".") + 1, strGroupId.length());

				
				pomBean = new PomBean(strGroupId, artifactId, version);
				tempList.add(pomBean);
			}
		}
		return tempList;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DefaultPrcXmlDoc cc = new DefaultPrcXmlDoc();
		 cc.init();
		cc.parserXml("D://project//adminV22427_20110113//all//project.xml");
		// cc.parserXml("D:\\fuck.xml");
		// cc.parseUIS(TEAM_UI);
	}
}