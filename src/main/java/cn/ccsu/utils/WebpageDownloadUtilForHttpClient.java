package cn.ccsu.utils;

import cn.ccsu.iface.WebpageDownloadInterface;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:通过HttpClient用于下载给定任意网址对应的html代码
 *
 * @author: TheFei
 * @Date: 2019-09-16
 * @Time: 12:23
 */
public class WebpageDownloadUtilForHttpClient implements WebpageDownloadInterface
{
    @Override
    public String downloadHtml(String url)
    {
        String downloadHtml = null;
        try {
            downloadHtml = download(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadHtml;
    }

    public static String download(String url) throws IOException
    {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String htmlSource = null;
        try
        {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200)//http状态码为200
            {
                try
                {
                    HttpEntity entity = response.getEntity();
                    htmlSource = parseEntity(entity);
                    EntityUtils.consume(entity);
                }finally
                {
                    response.close();
                }
            }
        }finally
        {
            httpclient.close();
        }

        return htmlSource;
    }
    @Test
    public void testdownloadHtml()
    {
        //String url = "https://www.baidu.com";
        //String url = "http://www.xbiquge.la/";
        //String url = "https://www.qq.com";
        String url = "http://news.youth.cn/gn/";
        //String url = "http://news.youth.cn/";
        //String url = "https://bbs.csdn.net/topics/370024556";
        //String url ="https://www.52pojie.cn/";
        //String url ="https://www.xl720.com/";
        //String url ="https://www.hanfan.cc/tag/jungle/";
        // String url ="https://www.hanfan.cc/35590.html";

        String html = null;
        //String url ="https://www.bilibili.com/";
        try {
            html = WebpageDownloadUtilForHttpClient.download(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(html);
    }

    public static String parseEntity(HttpEntity httpEntity) throws IOException
    {
        //1 最终编码html源码变量
        String findCharset  = null;
        String htmlSource = null;
        //2 因为不管哪个编码，这个字节数组均要形成，故直接先转成字节数组
        byte[] contentByteArray = IOUtil.convertInputStreamToByteArray(httpEntity.getContent());
        //3 从http header 获取编码 如果拿到则为最终网页编码
        findCharset = EntityUtils.getContentCharSet(httpEntity);
        //4 编码逻辑
        if (findCharset == null)//在header中没有找到
        {
            htmlSource = new String(contentByteArray,StaticValue.defaultEncoding);//先用默认UTF-8读取网页，不管他乱不乱码

            findCharset = WebCharsetDetectorUtil.getCharsetByHttpSource(htmlSource);//找到网站编码

            if (findCharset.equalsIgnoreCase(StaticValue.defaultEncoding) || findCharset == null)//如果网站的编码与默认的编码UTF-8相同或者没有找到网站编码    就不用转换
            {

            }
            else//如果网站的编码与默认的编码UTF-8不相同相同就要用找出来的网站编码重新转换一次
            {
                htmlSource = new String(contentByteArray,findCharset);
            }
        }
        else//在responseheader中找到了
        {
            htmlSource = new String(contentByteArray,findCharset);
        }
        return htmlSource;
    }

}
