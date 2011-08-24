package com.lqf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntrySetTest {

	
	public void testAll() {
		Map models = new HashMap();
		models.put("1", "erere1");
		models.put("2", "erere2");
		models.put("3", "erere3");
		models.put("4", "erere4");
		List resultList = new ArrayList();
		for (Object tempObj : models.entrySet()) {
			if(tempObj != null) {
				Map.Entry<Object, Object> tempEntry = (Map.Entry<Object, Object>) tempObj;
				resultList.add(tempEntry.getValue());
				System.out.println(tempEntry.getValue());
			}
		}
	}
	
	public static void main(String[] args) {
		EntrySetTest setTest = new EntrySetTest();
		setTest.testAll();
	}
	
}
