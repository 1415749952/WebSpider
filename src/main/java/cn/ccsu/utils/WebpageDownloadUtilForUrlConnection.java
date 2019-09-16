package cn.ccsu.utils;

import cn.ccsu.iface.download.DownloadInterface;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

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
        String charset = WebCharsetDetectorUtil.Getcharset(url);
        BufferedReader br = IOUtil.getBufferedReader(url, charset);
        String line = null;
        int lineCounter = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = br.readLine())!=null)
        {
            if (lineCounter > 0)
            {
                stringBuilder.append(StaticValue.sep_next_line);
            }
            stringBuilder.append(line);
        }
        br.close();
        return  stringBuilder.toString();
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
