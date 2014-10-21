package com.net.pic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.net.pic.ui.HttpClientUrl;

public class DataFetcherImpl implements DataFetcher {

    public File fecthFile(String httpUrl, String fileSavePath) throws MalformedURLException,
                                                              IOException {

        File file = new File(fileSavePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        // 打开输入流
        BufferedInputStream in = new BufferedInputStream(getInputStream(httpUrl));

        // 打开输出流
        FileOutputStream out = new FileOutputStream(file);

        byte[] buff = new byte[1];
        // 读取数据
        while (in.read(buff) > 0) {
            out.write(buff);
        }

        out.flush();
        out.close();
        in.close();
        return file;
    }

    public StringBuffer fetchHtml(String httpUrl, HttpClientUrl clintUrl)
                                                                         throws MalformedURLException,
                                                                         IOException {
        StringBuffer data = new StringBuffer();
        clintUrl.setUrl(httpUrl);
        data.append(clintUrl.parseHtml());
        return data;
    }

    /**
     * 获取数据流
     * @param httpUrl
     * @return
     * @throws IOException
     */
    private InputStream getInputStream(String httpUrl) throws IOException {
        // 网页Url
        URL url = new URL(httpUrl);
        URLConnection uc = url.openConnection();
        uc.setRequestProperty("User-Agent",
            "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        uc.setRequestProperty("Content-type", "application/x-java-serialized-object");
        return uc.getInputStream();
    }
}