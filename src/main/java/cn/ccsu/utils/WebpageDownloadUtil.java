package cn.ccsu.utils;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created with IntelliJ IDEA.
 * Description:用于下载给定任意网址对应的html代码
 *
 * @author: TheFei
 * @Date: 2019-09-15
 * @Time: 10:42
 */
public class WebpageDownloadUtil
{
    @Test
    public void testDownloadHtml() throws IOException
    {
        //String url = "http://news.youth.cn/gn/";
        String url = "https://www.baidu.com";
        URL urlObj = new URL(url);
        URLConnection urlConnection = urlObj.openConnection();

        InputStream is = urlConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is,"gb2312");

        BufferedReader br = new BufferedReader(isr);
        String line = null;
        int lineCounter = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = br.readLine())!=null)
        {
            if (lineCounter > 0)
            {
                stringBuilder.append("\n");
            }
            stringBuilder.append(line);
        }
        br.close();
        System.out.println(stringBuilder.toString());

    }
}
