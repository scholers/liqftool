package com.taobao.tools.impl;

import com.taobao.tools.StringDealIntf;

/**
 * 
 * 
 * @�����ƣ�DefaultStringDeal
 * @��������
 * @�����ˣ���ȱ
 * @�޸��ˣ���ȱ
 * @�޸�ʱ�䣺2011-3-9 ����06:34:46
 * @�޸ı�ע��
 * @version 1.0.0
 * 
 */
public class DefaultStringDeal implements StringDealIntf {

	private final static String firestStr = "<project";
	private final static String firestStrs = "<projects";
	private final static String startStr = "id=\"";
	private final static String endStr = "\"/>";
	private final static String dir = "dir=";
	private final static String version = "version";
	//�ж��Ƿ���Ŀ¼�ܿ��ļ�
	private final static String DEFAULT_SCOPE = "ALL";

	/**
	 * ���� <project id="toolkit/common/lang"/> <project
	 * id="taobao/sellercenter/common-client" version="1.1.0"/>
	 */
	public StringBuilder dealText(String line, StringBuilder strBuilder) {
		// ���ݽ���
		if (line.indexOf(firestStr) > 0 && line.indexOf(firestStrs) < 0 && line.indexOf(dir) < 0) {
			dealAll(strBuilder, line);
		}
		return strBuilder;
	}

	/**
	 * 
	 * dealAll(������һ�仰�����������������) (����������������������� �C ��ѡ)
	 * 
	 * @param strBuilder
	 * @param line
	 * @return StringBuilder
	 * @since 1.0.0
	 */
	private StringBuilder dealAll(StringBuilder strBuilder, String line) {
		int end = line.indexOf(endStr);
		if (end > 0) {
			String strVersion = null;
			// �����汾��Ϣ
			if (line.indexOf(version) < 0) {
				line = line.substring(line.indexOf(startStr) + startStr.length(), end);
			} else {
				
				strVersion = line.substring(line.indexOf(version) + version.length(), end);
				// ���ν���
				if (strVersion != null && strVersion.length() > 0) {
					strVersion = strVersion.substring(strVersion.indexOf("\"") + 1, strVersion.length());
				}
				
				line = line.substring(line.indexOf(startStr) + startStr.length(), line.indexOf(version));
				//���ν���
				line = line.substring(0, line.indexOf("\""));
			}
			line = line.replaceAll("/", ".");
			strBuilder.append("<dependency>\n");
			strBuilder.append("<groupId>");
			// �ж��Ƿ���toolkit
			if (line.indexOf("toolkit") >= 0) {
				line = "com.alibaba." + line;
			}
			// �ж��Ƿ���taobao
			else if (line.indexOf("taobao") >= 0) {
				line = "com." + line;
			}
			strBuilder.append(line); // ��������������ӵ� buffer ��
			strBuilder.append("</groupId>");
			strBuilder.append("\n"); // ��ӻ��з�
			strBuilder.append("<artifactId>");
			strBuilder.append(line.substring(line.lastIndexOf(".") + 1, line.length())); // artifactId
			strBuilder.append("</artifactId>");
			strBuilder.append("\n"); // ��ӻ��з�
		
			if ("ALL".equals(DEFAULT_SCOPE) && strVersion != null) {
				strBuilder.append("\t<version>");
				strBuilder.append(strVersion); // version
				strBuilder.append("</version>");
				strBuilder.append("\n"); // ��ӻ��з�
			}
			
			strBuilder.append("</dependency>");
			strBuilder.append("\n"); // ��ӻ��з�
		}
		return strBuilder;
	}

}
