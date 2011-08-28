package com.liqf.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件COPY,移动操作
 * @author jill
 *
 */
public class FileAccess {
	public static boolean move(File srcFile, String destPath) {
		// Destination directory
		File dir = new File(destPath);

		// Move file to new directory
		boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));

		return success;
	}

	/**
	 * 移动整个目录
	 * @param srcFile 源目录
	 * @param destPath 目标目录
	 * @return
	 */
	public static int Move(String srcFile, String destPath) {
		// File (or directory) to be moved
		File file = new File(srcFile);
		File dir = new File(destPath);
		//如果目录不存在,就创建目录
		if(destPath != null) {
	         if (!dir.exists()){
	        	 dir.mkdir();
	         }
		}
		int count = 0;
		if(file.isDirectory()) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				if (list[i].isFile()) {
					if(move(list[i], destPath)){
						count ++;
					}
					
				}
			}
		}
		return count;
	}

	public static void Copy(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("error  ");
			e.printStackTrace();
		}
	}

	public static void Copy(File oldfile, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			// File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldfile);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("error  ");
			e.printStackTrace();
		}
	}
}
