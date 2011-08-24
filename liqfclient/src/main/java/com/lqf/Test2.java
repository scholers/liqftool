package com.lqf;

public class Test2 {

	private long csId; // ID
	private int chargePercentage; // ¹Ì¶¨·ÑÂÊ

	public long getCsId() {
		return csId;
	}

	public void setCsId(long csId) {
		this.csId = csId;
	}

	public int getChargePercentage() {
		return chargePercentage;
	}

	public void setChargePercentage(int chargePercentage) {
		this.chargePercentage = chargePercentage;
	}

	public Object clone() throws CloneNotSupportedException {
		Test2 test2 =   (Test2) super.clone();  
		
		test2.chargePercentage = this.getChargePercentage() ;
        return test2;  
	}

	public void testExt() {
		String point = "bb";
		int pointNum = 0;
		try {
			pointNum = Integer.parseInt(point);
		} catch (Exception e) {
			System.out.println(e.fillInStackTrace());
		}
		System.out.println(pointNum);
	}

	public static void main(String[] arges) {
		Test2 test2 = new Test2();
		try {
			test2.setChargePercentage(5);
			test2.setCsId(2L);
			Test2 test3 = (Test2) test2.clone();
			System.out.println(test3.getChargePercentage());
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}

		// test2.testExt();
	}

}
