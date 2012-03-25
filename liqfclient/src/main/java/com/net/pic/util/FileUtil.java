package com.net.pic.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

public class FileUtil {

	private static final String saveFileName = "";

	private static final String FILE_PATH = "d://pic//";
	
	public static void toFile(InputStream in, String filePath, String fileName) {
		if(filePath == null) {
			filePath = FILE_PATH;
		}
		if(fileName == null) {
			filePath = saveFileName;
		}
		try {
			DataInputStream dis = new DataInputStream(in);
			OutputStream fos = new FileOutputStream(new File(filePath
					+ fileName));
			byte[] buff = new byte[1024];
			int len = -1;
			while ((len = dis.read(buff)) != -1) {
				fos.write(buff, 0, len);
			}
			buff = null;
			fos.close();
			dis.close();
			System.out.println("下载文件" + fileName + "完成");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
