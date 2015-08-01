package com.finance.vo;

import java.math.BigDecimal;

/**
 * 
 * @author weique
 *
 */
public class FinanceVo {

	private String id;
	/**
	 * 
	 */
	private String type;
	/**
	 * 
	 */
	private String subType;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private double print;
	/**
	 * 浠介
	 */
	private double num;
	
	private String url;
	
	public FinanceVo (String type, String subType, String name, double print, String url) {
		this.type = type;
		this.subType = subType;
		this.name = name;
		this.print = print;
		this.url = url;
	}
	
	public FinanceVo (String type, String subType, String name, double print) {
		this.type = type;
		this.subType = subType;
		this.name = name;
		this.print = print;
	}
	/**
	 *
	 */
	private double curValue;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrint() {
		return print;
	}
	public void setPrint(double print) {
		this.print = print;
	}
	public double getNum() {
		return num;
	}
	public void setNum(double num) {
		this.num = num;
	}
	public double getCurValue() {
		return curValue;
	}
	public void setCurValue(double curValue) {
		this.curValue = curValue;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
