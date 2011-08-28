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
	
	/**
	 * 计算文件数量,超多一定数量就移动到目录目录
	 * @param srcFile 源目录
	 * @param destPath 默认目录
	 * @return
	 */
	public static int move(String srcFile, String destPath,  int maxCount) {
		// File (or directory) to be moved
		File file = new File(srcFile);
		//
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
			//如果该文件夹文件数目小于最大值
			System.out.print("dir [" + file.getAbsolutePath() + "] file size :" + list.length);
			//如果该目录文件夹下的文件小于maxcount,那么反向移动
			if(list.length <= maxCount)  {
				File[] listDest = dir.listFiles();
				int desCount = maxCount - list.length;
				for (int i = 0; i < listDest.length; i++) {
					if (listDest[i].isFile() && count < desCount) {
						if(move(listDest[i], file.getAbsolutePath())){
							count ++;
						}
						
					}
				}
				System.out.println("\n目录" + dir + "移动到目录[" + file.getAbsolutePath() + "]的文件数为:" + count);
			} else {
				for (int i = 0; i < list.length; i++) {
					//超过最大数目,那么开始移动文件
					if (list[i].isFile() && count >= maxCount) {
						if(move(list[i], destPath)){

						} 
						
					}
					count ++;
				}
				System.out.println("目录" + file.getAbsolutePath() + "移动到目录[" + destPath + "]的文件数为:" + count);
			}
		}
		
		return count;
	}
	
	/**
	 * 
	 * @param srcFile
	 * @param destPath
	 * @param maxCount
	 */
	public static void batchMove(String srcFile, String destPath,  int maxCount) {
		// File (or directory) to be moved
		//scan src dir
		File file = new File(srcFile);
		
		if(file.isDirectory()) {
			File[] list = file.listFiles();
			for (int i = 0; i < list.length; i++) {
				//判断是否是照片目录
				if(list[i].getAbsolutePath().indexOf("pic") >= 0
						 || list[i].getAbsolutePath().indexOf("other") >= 0) {
					//超过最大数目,那么停止移动文件
					if (list[i].isDirectory()) {
						int iCount = move(list[i].getAbsolutePath(), destPath, maxCount);
						
					}
				}
				
			}
		}
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
