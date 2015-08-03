package com.finance.vo;

import com.finance.enums.FinanceTypeEnum;
import com.finance.enums.SubTypeEnum;

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
	private FinanceTypeEnum financeTypeEnum;
	/**
	 * 
	 */
	private SubTypeEnum subTypeEnum;
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
	
	public FinanceVo (FinanceTypeEnum type, SubTypeEnum subType, String name, double print, String url) {
		this.financeTypeEnum = type;
		this.subTypeEnum = subType;
		this.name = name;
		this.print = print;
		this.url = url;
	}
	
	public FinanceVo (FinanceTypeEnum type, SubTypeEnum subType, String name, double print) {
		this.financeTypeEnum = type;
		this.subTypeEnum = subType;
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

	public FinanceTypeEnum getFinanceTypeEnum() {
		return financeTypeEnum;
	}

	public void setFinanceTypeEnum(FinanceTypeEnum financeTypeEnum) {
		this.financeTypeEnum = financeTypeEnum;
	}

	public SubTypeEnum getSubTypeEnum() {
		return subTypeEnum;
	}

	public void setSubTypeEnum(SubTypeEnum subTypeEnum) {
		this.subTypeEnum = subTypeEnum;
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
