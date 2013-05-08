package com.lqf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;





public class SystemProp {

	
	 public static void initSystemPropertie(Map<String, String> prop) {
	        try {
	            Process p = null;
	           // if (SofaTestConstants.IS_WINDOWS_OS) {
	                p = Runtime.getRuntime().exec("cmd /c set"); // windows
	            //} else {
	            //    p = Runtime.getRuntime().exec("/bin/sh -c set"); // unix/linux/mac/aix
	            //}
	            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;
	            while ((line = br.readLine()) != null) {
	                int i = line.indexOf("=");
	                if (i > -1) {
	                    prop.put(line.substring(0, i), line.substring(i + 1));
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        System.out.println(prop.toString());
	        System.out.println(prop.get("CLOUDENGINE_HOME"));
	    }
	 
	 
	 /**
	     * 
	     * @param args
	     */
	    public static void main(String[] args) {
	    	initSystemPropertie(new HashMap<String, String>());
	    }
}
