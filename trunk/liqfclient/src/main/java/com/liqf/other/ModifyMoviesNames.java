package com.liqf.other;

import java.io.File;

public class ModifyMoviesNames {

	/**
	 * 修改文件名称，去掉‘[]’
	 * 
	 * @param filePath
	 */
	public static void readFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file != null && file.isDirectory()) {
				File list[] = file.listFiles();
				// 文件不能为空
				if (list != null) {
					for (int i = 0; i < list.length; i++) {
						if (list[i].isFile()) {
							String fileName = list[i].getName().trim();
							System.out.println("fileName==" + fileName);
							// 获取文件后缀名
							String ffHouZhui = fileName.substring(
									fileName.lastIndexOf(".") + 1,
									fileName.length());
							System.out.println("ffHouZhui==" + ffHouZhui);
							// 判断文件是或否是电影文件
							if (("rmvb").equals(ffHouZhui)
									|| "RMVB".equals(ffHouZhui)
									|| "RM".equals(ffHouZhui)
									|| "rm".equals(ffHouZhui)) {
								int end = fileName.indexOf("]");
								if (end > 0 && ((end + 1) < fileName.length())) {
									// 替换文件名称
									fileName = fileName.substring(end + 1,
											fileName.length());
									if (fileName.indexOf(".") > 0) {
										System.out.println("fileName==="
												+ fileName);
										// 修改文件名称
										File filedst = new File(filePath,
												fileName);
										list[i].renameTo(filedst);
									}
								}
								// break;
							}
						} else if (list[i].isDirectory()) {// 递归
							// System.out.println(list[i].getName());
							readFile(filePath + "\\" + list[i].getName());
						}
					}

				}

			}
		} catch (Exception e) {// 错误处理
			e.printStackTrace();
			System.out.println("读取数据错误.");
		}
	}

	/**
	 * 
	 * @param strFilePath
	 * @param targetPath
	 */
	public static void movePics(String strFilePath, String targetPath) {
		int count = FileAccess.Move(strFilePath, targetPath);
		System.out.println("成功转移文件数:" + count);
	}

	public static void main(String args[]) {
		//readFile("F:\\movies");
		//movePics("C:\\TDDownload\\other\\others","E:\\My Documents\\My Webs\\sex\\pic18");
		movePics("E:\\My Documents\\My Webs\\sex\\pic18","E:\\My Documents\\My Webs\\sex\\pic17");
	}

}
