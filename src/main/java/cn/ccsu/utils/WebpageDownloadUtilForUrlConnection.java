package cn.ccsu.utils;

import cn.ccsu.iface.download.DownloadInterface;
import jdk.nashorn.internal.ir.IfNode;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:通过UrlConnection用于下载给定任意网址对应的html代码
 *
 * @author: TheFei
 * @Date: 2019-09-15
 * @Time: 10:42
 */
public class WebpageDownloadUtilForUrlConnection implements DownloadInterface
{
    @Override
    public String downloadHtml(String url)
    {
        String downloadHtml = null;
        try {
            downloadHtml = download(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return downloadHtml;
    }

    /**
     * 根据url下载该url网页数据
     * @param url 要下载的网址
     * @return 该网址的内容
     * @throws Exception
     */
    public static String download(String url) throws Exception
    {
        //1 最终编码html源码变量
        String htmlSource = null;
        String findCharset = null;
        //2 获取URLConnection对象
        URLConnection urlConnection = IOUtil.getUrlConnection(url);
        //3 将流转换成字节数组
        byte[] contentByteArray = IOUtil.convertInputStreamToByteArray(urlConnection.getInputStream());
        //4 从http header 获取编码 如果拿到则为最终网页编码
        findCharset =WebCharsetDetectorUtil.GetcharsetByHttpHeader(urlConnection);
        //5 编码逻辑
        if(findCharset == null)//如果header没有找到，就去网页中找网页编码
        {
            htmlSource = new String(contentByteArray,StaticValue.defaultEncoding);//先用默认UTF-8读取网页，不管他乱不乱码

            findCharset = WebCharsetDetectorUtil.getCharsetByHttpSource(htmlSource);//找到网站编码

            if (findCharset.equalsIgnoreCase(StaticValue.defaultEncoding) || findCharset == null)//如果网站的编码与默认的编码UTF-8相同或者没有找到网站编码    就不用转换
            {

            }
            else//如果网站的编码与默认的编码UTF-8不相同,就要用找出来的网站编码重新转换一次
            {
                htmlSource = new String(contentByteArray,findCharset);
            }
        }
        else//如果header找到了网页编码
        {
            htmlSource = new String(contentByteArray,findCharset);
        }
        return htmlSource;
    }

    @Test
    public void testDownloadHtml() throws Exception
    {
        String url = "https://www.baidu.com";
        //String url = "http://www.xbiquge.la/";
        //String url = "https://www.qq.com";
        //String url = "http://news.youth.cn/gn/";
        //String url = "http://news.youth.cn/";
        //String url = "https://bbs.csdn.net/topics/370024556";
        //String url ="https://www.52pojie.cn/";
        //String url ="https://www.xl720.com/";
        //String url ="https://www.bilibili.com/";
        //String url ="https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E7%AC%94%E8%B6%A3%E9%98%81&rsv_pq=be5cb5e600070dbf&rsv_t=8222E4EihFB1uz8ApjHNRcoZBjb8vwvz3yZJnDQ1tHUEs%2FgDCoTDU2owzfc&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_sug3=10&rsv_sug1=9&rsv_sug7=101&rsv_sug2=0&inputT=4577&rsv_sug4=5234";
        String downloadHtml = WebpageDownloadUtilForUrlConnection.download(url);
        System.out.println(downloadHtml);
    }


}
