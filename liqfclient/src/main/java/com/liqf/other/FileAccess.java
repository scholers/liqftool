package com.liqf.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件COPY,移动操作
 * @author jill
 *
 */
public class FileAccess {

    /**
     * 文件到目标文件夹
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
     * 计算文件数量,超多一定数量就移动到目录目录
     * @param srcFile 源目录
     * @param destPath 默认目录
     * @return
     */
    public static int move(String srcFile, String destPath, int maxCount) {
        // File (or directory) to be moved
        File file = new File(srcFile);
        //
        File dir = new File(destPath);
        //如果目录不存在,就创建目录
        if (destPath != null) {
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
        int count = 0;
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            //如果该文件夹文件数目小于最大值
            System.out.print("dir [" + file.getAbsolutePath() + "] file size :" + list.length);
            //如果该目录文件夹下的文件小于maxcount,那么反向移动
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
                System.out.println("\n目录" + dir + "移动到目录[" + file.getAbsolutePath() + "]的文件数为:"
                                   + count);
            } else {
                for (int i = 0; i < list.length; i++) {
                    //超过最大数目,那么开始移动文件
                    if (list[i].isFile() && count >= maxCount) {
                        if (move(list[i], destPath)) {

                        }

                    }
                    count++;
                }
                System.out.println("目录" + file.getAbsolutePath() + "移动到目录[" + destPath + "]的文件数为:"
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
                //判断是否是照片目录
                if (list[i].getAbsolutePath().indexOf("pic") >= 0
                    || list[i].getAbsolutePath().indexOf("other") >= 0) {
                    //超过最大数目,那么停止移动文件
                    if (list[i].isDirectory()) {
                        int iCount = move(list[i].getAbsolutePath(), destPath, maxCount);

                    }
                }

            }
        }
    }

    /**
     * 
     * 递归文件家下面的JPG文件，然后转移到新的目录
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
     * 判断是否想要的文件
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
