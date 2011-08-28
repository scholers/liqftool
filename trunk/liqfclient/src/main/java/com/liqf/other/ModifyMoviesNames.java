package com.liqf.other;

import java.io.File;

public class ModifyMoviesNames {

	/**
	 * �޸��ļ����ƣ�ȥ����[]��
	 * 
	 * @param filePath
	 */
	public static void readFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file != null && file.isDirectory()) {
				File list[] = file.listFiles();
				// �ļ�����Ϊ��
				if (list != null) {
					for (int i = 0; i < list.length; i++) {
						if (list[i].isFile()) {
							String fileName = list[i].getName().trim();
							System.out.println("fileName==" + fileName);
							// ��ȡ�ļ���׺��
							String ffHouZhui = fileName.substring(
									fileName.lastIndexOf(".") + 1,
									fileName.length());
							System.out.println("ffHouZhui==" + ffHouZhui);
							// �ж��ļ��ǻ���ǵ�Ӱ�ļ�
							if (("rmvb").equals(ffHouZhui)
									|| "RMVB".equals(ffHouZhui)
									|| "RM".equals(ffHouZhui)
									|| "rm".equals(ffHouZhui)) {
								int end = fileName.indexOf("]");
								if (end > 0 && ((end + 1) < fileName.length())) {
									// �滻�ļ�����
									fileName = fileName.substring(end + 1,
											fileName.length());
									if (fileName.indexOf(".") > 0) {
										System.out.println("fileName==="
												+ fileName);
										// �޸��ļ�����
										File filedst = new File(filePath,
												fileName);
										list[i].renameTo(filedst);
									}
								}
								// break;
							}
						} else if (list[i].isDirectory()) {// �ݹ�
							// System.out.println(list[i].getName());
							readFile(filePath + "\\" + list[i].getName());
						}
					}

				}

			}
		} catch (Exception e) {// ������
			e.printStackTrace();
			System.out.println("��ȡ���ݴ���.");
		}
	}

	/**
	 * 
	 * @param strFilePath
	 * @param targetPath
	 */
	public static void movePics(String strFilePath, String targetPath) {
		int count = FileAccess.Move(strFilePath, targetPath);
		System.out.println("�ɹ�ת���ļ���:" + count);
	}

	public static void main(String args[]) {
		//readFile("F:\\movies");
		//movePics("C:\\TDDownload\\other\\others","E:\\My Documents\\My Webs\\sex\\pic18");
		movePics("E:\\My Documents\\My Webs\\sex\\pic18","E:\\My Documents\\My Webs\\sex\\pic17");
	}

}
