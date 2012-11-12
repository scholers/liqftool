package com.hadoop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;


public class Comper {
	
	private static FileSystem fs = null;
	private char SPLIT_CHAR =  '\u0001';// 0x09;
	//private LoadHandler loadHandler = null;
	private static String encoding = "UTF-8";
	private static int bufferSize = 4*1024;
	private int emptyfile = 0;
	private String fileType = "SEQ";
	private Path path;
	
	
	
	public static int open(Path p) throws IOException {
		Configuration conf = new Configuration();
		//conf.setInt("io.file.buffer.size", bufferSize);
		//reader = new SequenceFile.Reader(fs, p, conf);
		 FSDataInputStream in = null;
		 CompressionInputStream cin = null;
		 BufferedReader br  = null;
		 String s = null;
		 boolean compressed = true;
		  BufferedWriter bw = null;  
		 
		 try {
				fs = FileSystem.get(conf);
			} catch (IOException e) {
				throw new RuntimeException(String.format(
						"Can't create file system:%s,%s", e.getMessage(),
						e.getCause()));
			}

		try {
			if(compressed){
				CompressionCodecFactory factory = new CompressionCodecFactory(conf);
				CompressionCodec codec = factory.getCodec(p);
				if (codec == null) {
					throw new IOException(String.format(
							"Can't find any suitable CompressionCodec to this file:%s",
							p.toString()));
				}
				in = fs.open(p);
				cin = codec.createInputStream(in);
				br = new BufferedReader(
						new InputStreamReader(cin, encoding), bufferSize);
				System.out.println("br===" + br.toString() + "\n");
			}else{
				in = fs.open(p);
				br = new BufferedReader(new InputStreamReader(in, encoding), bufferSize);
			}
			String b = null;  
	        File file = new File("D:\\s_dw_log_cps_click.txt");  
	        if (!file.exists() != false) {  
	            try {  
	                file.createNewFile();  
	                  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	          }  
	        bw = new BufferedWriter(new FileWriter(file));  
			 while ((b = br.readLine()) != null) {  
	                System.out.println(b);  
	                bw.write(b);//Êä³ö×Ö·û´®  
	                bw.newLine();//»»ÐÐ  
	                bw.flush();  
	            }  
			if(in.available()==0)
				return -1;
			else
				return 0;
		} catch (IOException e) {
			throw e;
		}
	}
	
	public static void main(String[] args) {
		Path path = new Path("d:\\attempt_local_0001_m_000000_0.deflate");
		
		try {
			open(path);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
