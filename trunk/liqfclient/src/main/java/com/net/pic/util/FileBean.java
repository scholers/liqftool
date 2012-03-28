package com.net.pic.util;

/**
 * 
 * @author jill
 *
 */
public class FileBean {

	private String fileName = null;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
    public boolean equals(Object anObject) {
	if (this == anObject) {
	    return false;
	}
	if (anObject instanceof FileBean) {
		FileBean fileBean = (FileBean)anObject;
	    if(fileBean.getFileName().equals(fileName)) {
	    	return true;
	    }
	}
	return false;
    }
    
    public int hashCode() {
    	return 1;
	}
}
