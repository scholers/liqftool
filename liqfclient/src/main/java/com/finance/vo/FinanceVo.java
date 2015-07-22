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
	 * 理财大类
	 */
	private String type;
	/**
	 * 理财小类
	 */
	private String subType;
	/**
	 * 理财产品名称
	 */
	private String name;
	/**
	 * 本金
	 */
	private double print;
	/**
	 * 份额
	 */
	private double num;
	
	public FinanceVo (String type, String subType, String name, double print) {
		this.type = type;
		this.subType = subType;
		this.name = name;
		this.print = print;
	}
	/**
	 * 当前净值
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
	
	
}
