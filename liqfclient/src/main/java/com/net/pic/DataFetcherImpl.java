package com.net.pic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.net.pic.ui.HttpClientUrl;

public class DataFetcherImpl implements DataFetcher {

    public File fecthFile(String httpUrl, String fileSavePath)
            throws MalformedURLException, IOException {

        File file = new File(fileSavePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        // ��������
        BufferedInputStream in = new BufferedInputStream(
                getInputStream(httpUrl));

        // �������
        FileOutputStream out = new FileOutputStream(file);

        byte[] buff = new byte[1];
        // ��ȡ����
        while (in.read(buff) > 0) {
            out.write(buff);
        }

        out.flush();
        out.close();
        in.close();
        return file;
    }


    public StringBuffer fetchHtml(String httpUrl) throws MalformedURLException,
            IOException {

        StringBuffer data = new StringBuffer();

        /*
        String currentLine;

        // ��������
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                getInputStream(httpUrl), "GBK"));

        // ��ȡ����
        while ((currentLine = reader.readLine()) != null) {
            data.append(currentLine);
        }
        reader.close();*/
        HttpClientUrl clintUrl = new HttpClientUrl(httpUrl);
        data.append(clintUrl.parseHtml());
        return data;
    }

    /**
     * ��ȡ������
     * @param httpUrl
     * @return
     * @throws IOException
     */
    private InputStream getInputStream(String httpUrl) throws IOException {
        // ��ҳUrl
        URL url = new URL(httpUrl);
        URLConnection uc = url.openConnection();
        uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        return uc.getInputStream();
    }
}