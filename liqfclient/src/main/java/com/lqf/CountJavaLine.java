package com.lqf;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


/**
 * 
*
* @�����ƣ�CountJavaLine
* @��������ͳ��JAVA��������
* @�����ˣ���ȱ
* @�޸��ˣ���ȱ
* @�޸�ʱ�䣺2011-1-25 ����10:42:00
* @�޸ı�ע��
* @version 1.0.0
*
 */

public class CountJavaLine {  
    	/**JAVA�ļ�*/  
    	private long javaFiles = 0;  
	    /**��ͨ����*/  
	    private long normalLines = 0;  
	    /**ע������*/  
	    private long commentLines = 0;  
	    /**�հ�����*/  
	    private long spaceLines = 0;  
	    /**������*/  
	    private long totalLines = 0;  
	    /*** 
	     * ͨ��java�ļ�·������ö��� 
	     * @param filePath java�ļ�·�� 
	     */  
	    public CountJavaLine(String filePath){  
	        tree(filePath);  
	    }  
	    /** 
	     * �����ļ��ķ��� 
	     * @param filePath �ļ�·�� 
	     */  
	    private void tree(String filePath){  
	        File file = new File(filePath);  
	        File[] childs = file.listFiles();  
	        if(childs == null){  
	            parse(file);  
	        }else{  
		        for(int i = 0; i< childs.length; i++){  
		            //System.out.println("path:"+childs[i].getPath());  
		            if(childs[i].isDirectory()){  
		                tree(childs[i].getPath());  
		            }else{  
		                if(childs[i].getName().matches(".*\\.java$")) {;   
			               // System.out.println("��ǰ"+childs[i].getName()+"��������:");  
			                parse(childs[i]);  
			                getCodeCounter();  
			                javaFiles ++;
		                }
		            }  
		        }  
	        }  
	    }  
	    /** 
	     * �����ļ� 
	     * @param file �ļ����� 
	     */  
	    private void parse(File file){  
	        BufferedReader br=null;  
	        boolean comment=false;  
	        try {  
	            br=new BufferedReader(new FileReader(file));  
	            String line="";  
	            while((line=br.readLine())!=null){  
	                line=line.trim();//ȥ���ո�  
	                if(line.matches("^[\\s&&[^\\n]]*$")) {     
	                       spaceLines ++;     
	                }else if((line.startsWith("/*"))&& !line.endsWith("*/")) {     
	                       commentLines ++;     
	                       comment = true;     
	                }else if(true == comment) {     
	                       commentLines ++;     
	                if(line.endsWith("*/")) {     
	                       comment = false;     
	                       }     
	                }else if(line.startsWith("//")) {     
	                        commentLines ++;     
	                }else {     
	                       normalLines ++;     
	                      }   
	              }  
	              
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    /** 
	     * �õ�Java�ļ��Ĵ������� 
	     */  
	    private void getCodeCounter(){  
	        totalLines=normalLines+spaceLines+commentLines;  
	        /*System.out.println("��ͨ��������:"+normalLines);  
	        System.out.println("�հ״�������:"+spaceLines);  
	        System.out.println("ע�ʹ�������:"+commentLines);  
	        System.out.println("����������:"+totalLines);  
	        normalLines=0;  
	        spaceLines=0;  
	        commentLines=0;  
	        totalLines=0;  */
	    }  
	    //�ܳ���
	    public long getCodeLineLength() {
	    	return totalLines;
	    }
	    
	    //�ܳ���
	    public long getJavaFiles() {
	    	return javaFiles;
	    }
	    
	    
	    public long getTotalLines() {
			return totalLines;
		}
		public void setTotalLines(long totalLines) {
			this.totalLines = totalLines;
		}
		public void setJavaFiles(long javaFiles) {
			this.javaFiles = javaFiles;
		}
		public static void main(String args[]){  
			String[] codeDir = new String[]{"D:\\project\\detail"
					//"D:\\project\\sellercenter\\trunk\\sellercenter",
					//"D:\\project\\sellermanager\\trunk\\sellermanager",
					//"D:\\project\\malllist",
					//"D:\\project\\adminV22427_20110113"
					};
			for(String tempDir : codeDir) {
				CountJavaLine counter = new CountJavaLine(tempDir); 
		    	System.out.println("��JAVA�����ļ�����" + counter.getJavaFiles());
		    	System.out.println("�ܴ���������" + counter.getCodeLineLength());
			}
	    	
	    	//counter.getCodeCounter();
	    }  
}
