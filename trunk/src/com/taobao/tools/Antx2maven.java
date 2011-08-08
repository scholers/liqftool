package com.taobao.tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

import com.taobao.tools.impl.DefaultStringDeal;

/**
 * 
*
* @类名称：antx2maven
* @类描述：
* @创建人：卫缺
* @修改人：卫缺
* @修改时间：2011-3-8 下午01:22:30
* @修改备注：
* @version 1.0.0
*
 */
public class Antx2maven {
	
	private StringDealIntf stringDealIntf = new DefaultStringDeal();

	 /**
     * 1. 演示将流中的文本读入一个 StringBuffer 中
     * @throws IOException
     */
    public void readToBuffer(StringBuffer buffer, InputStream is)
        throws IOException {
    	StringBuilder strBuilder = new StringBuilder();
        String line;        // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine();       // 读取第一行
        while (line != null) {          // 如果 line 为空说明读完了
        	stringDealIntf.dealText(line, strBuilder);
            line = reader.readLine();   // 读取下一行
        }
        System.out.println(strBuilder.toString());
    }
    /**
     * 2. 演示将 StringBuffer 中的内容读出到流中
     */
    public void writeFromBuffer(StringBuffer buffer, OutputStream os) {
        // 用 PrintStream 可以方便的把内容输出到输出流中
        // 其对象的用法和 System.out 一样
        // （System.out 本身就是 PrintStream 对象）
        PrintStream ps = new PrintStream(os);   
        ps.print(buffer.toString());
    }

    /**
     * 3*. 从输入流中拷贝内容到输入流中
     * @throws IOException
     */
    public void copyStream(InputStream is, OutputStream os) throws IOException {
        // 这个读过过程可以参阅 readToBuffer 中的注释
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(os));
        line = reader.readLine();
        while (line != null) {
            writer.println(line);
            line = reader.readLine();
        }
        writer.flush();     // 最后确定要把输出流中的东西都写出去了
                            // 这里不关闭 writer 是因为 os 是从外面传进来的
                            // 既然不是从这里打开的，也就不从这里关闭
                            // 如果关闭的 writer，封装在里面的 os 也就被关了
    }

    /**
     * 3. 调用 copyStream(InputStream, OutputStream) 方法拷贝文本文件
     */
    public void copyTextFile(String inFilename, String outFilename)
        throws IOException {
        // 先根据输入/输出文件生成相应的输入/输出流
        InputStream is = new FileInputStream(inFilename);
        OutputStream os = new FileOutputStream(outFilename);
        copyStream(is, os);     // 用 copyStream 拷贝内容
        is.close(); // is 是在这里打开的，所以需要关闭
        os.close(); // os 是在这里打开的，所以需要关闭
    }

    public static void main(String[] args) throws IOException {
        int sw = 1;     // 三种测试的选择开关
        Antx2maven test = new Antx2maven();
        
        switch (sw) {
        case 1: // 测试读
        {
            InputStream is = new FileInputStream("D:\\project\\adminV22427_20110113\\all\\project.xml");
            StringBuffer buffer = new StringBuffer();
            test.readToBuffer(buffer, is);
            System.out.println(buffer);     // 将读到 buffer 中的内容写出来
            is.close();
            break;
        }
        case 2: // 测试写
        {
            StringBuffer buffer = new StringBuffer("Only a test\n");
            test.writeFromBuffer(buffer, System.out);
            break;
        }
        case 3: // 测试拷贝
        {
            test.copyTextFile("E:\\test.txt", "E:\\r.txt");
        }
            break;
        }
    }
}