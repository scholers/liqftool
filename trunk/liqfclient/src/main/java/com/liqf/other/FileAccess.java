package com.liqf.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * �ļ�COPY,�ƶ�����
 * @author jill
 *
 */
public class FileAccess {

    /**
     * �ļ���Ŀ���ļ���
     * 
     * @param srcFile
     * @param destPath
     * @return
     */
    public static boolean move(File srcFile, String destPath) {
        // Destination directory
        File dir = new File(destPath);

        // Move file to new directory
        boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));

        return success;
    }

    /**
     * �ƶ�����Ŀ¼
     * @param srcFile ԴĿ¼
     * @param destPath Ŀ��Ŀ¼
     * @return
     */
    public static int Move(String srcFile, String destPath) {
        // File (or directory) to be moved
        File file = new File(srcFile);
        File dir = new File(destPath);
        //���Ŀ¼������,�ʹ���Ŀ¼
        if (destPath != null) {
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
        int count = 0;
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (int i = 0; i < list.length; i++) {
                if (list[i].isFile()) {
                    if (move(list[i], destPath)) {
                        count++;
                    }

                }
            }
        }
        return count;
    }

    /**
     * �����ļ�����,����һ���������ƶ���Ŀ¼Ŀ¼
     * @param srcFile ԴĿ¼
     * @param destPath Ĭ��Ŀ¼
     * @return
     */
    public static int move(String srcFile, String destPath, int maxCount) {
        // File (or directory) to be moved
        File file = new File(srcFile);
        //
        File dir = new File(destPath);
        //���Ŀ¼������,�ʹ���Ŀ¼
        if (destPath != null) {
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
        int count = 0;
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            //������ļ����ļ���ĿС�����ֵ
            System.out.print("dir [" + file.getAbsolutePath() + "] file size :" + list.length);
            //�����Ŀ¼�ļ����µ��ļ�С��maxcount,��ô�����ƶ�
            if (list.length <= maxCount) {
                File[] listDest = dir.listFiles();
                int desCount = maxCount - list.length;
                for (int i = 0; i < listDest.length; i++) {
                    if (listDest[i].isFile() && count < desCount) {
                        if (move(listDest[i], file.getAbsolutePath())) {
                            count++;
                        }

                    }
                }
                System.out.println("\nĿ¼" + dir + "�ƶ���Ŀ¼[" + file.getAbsolutePath() + "]���ļ���Ϊ:"
                                   + count);
            } else {
                for (int i = 0; i < list.length; i++) {
                    //���������Ŀ,��ô��ʼ�ƶ��ļ�
                    if (list[i].isFile() && count >= maxCount) {
                        if (move(list[i], destPath)) {

                        }

                    }
                    count++;
                }
                System.out.println("Ŀ¼" + file.getAbsolutePath() + "�ƶ���Ŀ¼[" + destPath + "]���ļ���Ϊ:"
                                   + count);
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
    public static void batchMove(String srcFile, String destPath, int maxCount) {
        // File (or directory) to be moved
        //scan src dir
        File file = new File(srcFile);

        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (int i = 0; i < list.length; i++) {
                //�ж��Ƿ�����ƬĿ¼
                if (list[i].getAbsolutePath().indexOf("pic") >= 0
                    || list[i].getAbsolutePath().indexOf("other") >= 0) {
                    //���������Ŀ,��ôֹͣ�ƶ��ļ�
                    if (list[i].isDirectory()) {
                        int iCount = move(list[i].getAbsolutePath(), destPath, maxCount);

                    }
                }

            }
        }
    }

    /**
     * 
     * �ݹ��ļ��������JPG�ļ���Ȼ��ת�Ƶ��µ�Ŀ¼
     * @param oldPath
     * @param newPath
     */
    public static void Copy(String oldPath, String newPath) {
        try {
            File oldfile = new File(oldPath);
            if (oldfile.isDirectory()) {
                File[] list = oldfile.listFiles();
                for (int i = 0; i < list.length; i++) {
                    File tempFile = list[i];
                    if (tempFile.exists()
                        && tempFile.isFile()
                        && (tempFile.getAbsolutePath().indexOf("jpg") >= 0 || tempFile
                            .getAbsolutePath().indexOf("bmp") >= 0)
                        && isMyPic(tempFile.getAbsolutePath())) {
                        move(tempFile.getAbsoluteFile(), newPath);
                    } else if (tempFile.isDirectory()) {
                        Copy(tempFile.getAbsolutePath(), newPath);
                    }
                }
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

    /**
     * �ж��Ƿ���Ҫ���ļ�
     * 
     * @param fileName
     * @return
     */
    public static boolean isMyPic(String fileName) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(fileName);
            int len = stream.available();
            if (len / 1024 > 80) {
                System.out.println(len);
                return true;
            }
        } catch (IOException e) {
            return false;
        } finally {
            try {
                stream.close();
            } catch (IOException e) {

            }
        }
        return false;
    }
}
