package com.taobao.tools.impl;

import com.taobao.tools.StringDealIntf;

/**
 * 
 * 
 * @类名称：DefaultStringDeal
 * @类描述：
 * @创建人：卫缺
 * @修改人：卫缺
 * @修改时间：2011-3-9 下午06:34:46
 * @修改备注：
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
	//判断是否是目录总控文件
	private final static String DEFAULT_SCOPE = "ALL";

	/**
	 * 解析 <project id="toolkit/common/lang"/> <project
	 * id="taobao/sellercenter/common-client" version="1.1.0"/>
	 */
	public StringBuilder dealText(String line, StringBuilder strBuilder) {
		// 数据解析
		if (line.indexOf(firestStr) > 0 && line.indexOf(firestStrs) < 0 && line.indexOf(dir) < 0) {
			dealAll(strBuilder, line);
		}
		return strBuilder;
	}

	/**
	 * 
	 * dealAll(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
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
			// 包含版本信息
			if (line.indexOf(version) < 0) {
				line = line.substring(line.indexOf(startStr) + startStr.length(), end);
			} else {
				
				strVersion = line.substring(line.indexOf(version) + version.length(), end);
				// 二次解析
				if (strVersion != null && strVersion.length() > 0) {
					strVersion = strVersion.substring(strVersion.indexOf("\"") + 1, strVersion.length());
				}
				
				line = line.substring(line.indexOf(startStr) + startStr.length(), line.indexOf(version));
				//二次解析
				line = line.substring(0, line.indexOf("\""));
			}
			line = line.replaceAll("/", ".");
			strBuilder.append("<dependency>\n");
			strBuilder.append("<groupId>");
			// 判断是否是toolkit
			if (line.indexOf("toolkit") >= 0) {
				line = "com.alibaba." + line;
			}
			// 判断是否是taobao
			else if (line.indexOf("taobao") >= 0) {
				line = "com." + line;
			}
			strBuilder.append(line); // 将读到的内容添加到 buffer 中
			strBuilder.append("</groupId>");
			strBuilder.append("\n"); // 添加换行符
			strBuilder.append("<artifactId>");
			strBuilder.append(line.substring(line.lastIndexOf(".") + 1, line.length())); // artifactId
			strBuilder.append("</artifactId>");
			strBuilder.append("\n"); // 添加换行符
		
			if ("ALL".equals(DEFAULT_SCOPE) && strVersion != null) {
				strBuilder.append("\t<version>");
				strBuilder.append(strVersion); // version
				strBuilder.append("</version>");
				strBuilder.append("\n"); // 添加换行符
			}
			
			strBuilder.append("</dependency>");
			strBuilder.append("\n"); // 添加换行符
		}
		return strBuilder;
	}

}
