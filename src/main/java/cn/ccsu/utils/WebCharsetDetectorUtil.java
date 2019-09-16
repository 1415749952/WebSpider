package cn.ccsu.utils;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description: 给一个网址url，返回网页编码
 *
 * @author: TheFei
 * @Date: 2019-09-15
 * @Time: 11:44
 */
public class WebCharsetDetectorUtil
{
    public static String meta_charset_regex = "charset=[\"]?([\\s\\S]*?)[\">]";


    /**
     * 获取网页编码
     * @param url 网页的url
     * @return 网页编码
     * @throws IOException
     */
    public static String Getcharset(String url) throws IOException
    {
        String findCharset = null;
        URL urlObj = new URL(url);
        URLConnection urlConnection = urlObj.openConnection();
        Map<String, List<String>> headerMap = urlConnection.getHeaderFields();
        Set<String> keySet = headerMap.keySet();
        List<String> valueList = null;
        for (String key : keySet)
        {
            if (key!=null && key.equalsIgnoreCase("Content-Type"))//Content-Type
            {
                valueList = headerMap.get(key);
                break;
            }
        }
        String line = valueList.get(0);
        String[] valueArray = line.trim().split(StaticValue.sep_semicolon);
        if (valueArray.length == 2)
        {
            String charsetString = valueArray[1];
            valueArray = charsetString.trim().split(StaticValue.sep_equal);
            if (valueArray.length == 2)
            {
                findCharset = valueArray[1];
            }
        }
        if (findCharset == null)//如果在header中找不到，就去页面上面找
        {
            BufferedReader br = IOUtil.getBufferedReader(urlConnection,StaticValue.defaultEncoding);
            String linee = null;
            while ((linee = br.readLine()) != null)
            {
                linee = linee.trim().toLowerCase();
                if (linee.contains("<meta"))
                {
                    findCharset = RegexUtil.getMatchText(linee,meta_charset_regex,1);
                    if (findCharset != null)
                    {
                        break;
                    }
                }
                else if (linee.contains("</head>"))
                {//到了</head>还没有找到就直接跳出循环，不在找了。
                    break;
                }
            }
            br.close();
        }
        if (findCharset==null)//header中和网页页面上都找不到就使用默认编码UTF-8
        {
            findCharset = StaticValue.defaultEncoding;
        }
        return findCharset;
    }
    @Test
    public void test()
    {
        //String url = "https://www.baidu.com";
        String url = "http://www.xbiquge.la/";
        //String url = "https://www.qq.com";
        //String url = "http://news.youth.cn/gn/";
        //String url = "http://news.youth.cn/";
        //String url = "https://bbs.csdn.net/topics/370024556";
        //String url ="https://www.52pojie.cn/";
        //String url ="https://www.xl720.com/";
        //String url ="https://www.bilibili.com/";
        //String url ="https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E7%AC%94%E8%B6%A3%E9%98%81&rsv_pq=be5cb5e600070dbf&rsv_t=8222E4EihFB1uz8ApjHNRcoZBjb8vwvz3yZJnDQ1tHUEs%2FgDCoTDU2owzfc&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_sug3=10&rsv_sug1=9&rsv_sug7=101&rsv_sug2=0&inputT=4577&rsv_sug4=5234";
        String charset = null;
        try {
            charset = WebCharsetDetectorUtil.Getcharset(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(charset);
    }

    /**
     *
     * @param httpEntity httpEntity对象
     * @return 网页编码
     * @throws IOException
     */
    public static String Getcharset(HttpEntity httpEntity) throws IOException
    {
        String findCharset = null;
        findCharset = EntityUtils.getContentCharSet(httpEntity);
        System.out.println("header********"+findCharset);
        if (findCharset == null)
        {
            byte[] contentByteArray = IOUtil.convertInputStreamToByteArray(httpEntity.getContent());
            String htmlSource = new String(contentByteArray,StaticValue.defaultEncoding);
            StringReader sr = new StringReader(htmlSource);
            BufferedReader br = new BufferedReader(sr);
            String linee = null;
            while ((linee = br.readLine()) != null)
            {
                linee = linee.trim().toLowerCase();
                if (linee.contains("<meta"))
                {
                    findCharset = RegexUtil.getMatchText(linee,meta_charset_regex,1);
                    if (findCharset != null)
                    {
                        break;
                    }
                }
                else if (linee.contains("</head>"))
                {//到了</head>还没有找到就直接跳出循环，不在找了。
                    break;
                }
            }
            System.out.println("body********"+findCharset);
        }
        if (findCharset==null)//header中和网页页面上都找不到就使用默认编码UTF-8
        {
            findCharset = StaticValue.defaultEncoding;
            System.out.println("默认********"+findCharset);
        }
        return findCharset;
    }


}
