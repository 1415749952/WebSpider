package cn.ccsu.utils;

import cn.ccsu.iface.WebpageDownloadInterface;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
    Logger logger = Logger.getLogger(WebpageDownloadUtilForHttpClient.class);

    @Override
    public String downloadHtml(String url)
    {
        return this.download(url);
    }

    private String download(String url)
    {
        //CloseableHttpClient httpClient = HttpClients.createDefault();
        //使用HttpClient连接池，从连接池中拿取HttpClient对象
        PoolingHttpClientConnectionManager httpClientPooling = new PoolingHttpClientConnectionManager();
        httpClientPooling.setMaxTotal(100);//设置最大连接数 连接池中有100个HttpClient对象
        httpClientPooling.setDefaultMaxPerRoute(20);//设置主机最大连接数（爬取时候有可能爬去百度新闻，新浪新闻不同的服务器）
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(httpClientPooling).build();
        String htmlSource = null;
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200)//http状态码为200
            {
                HttpEntity entity = response.getEntity();
                htmlSource = this.parseEntity(entity);
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        String html  = new  WebpageDownloadUtilForHttpClient().downloadHtml(url);
        System.out.println(html);

    }

    private String parseEntity(HttpEntity httpEntity)
    {
        //1 最终编码html源码变量
        String findCharset  = null;
        String htmlSource = null;
        //2 因为不管哪个编码，这个字节数组均要形成，故直接先转成字节数组
        byte[] contentByteArray = new byte[0];
        try {
            contentByteArray = IOUtil.convertInputStreamToByteArray(httpEntity.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //3 从http header 获取编码 如果拿到则为最终网页编码
        findCharset = EntityUtils.getContentCharSet(httpEntity);
        //4 编码逻辑
        if (findCharset == null)//在header中没有找到
        {
            try {
                htmlSource = new String(contentByteArray,StaticValue.defaultEncoding);//先用默认UTF-8读取网页，不管他乱不乱码
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                findCharset = WebCharsetDetectorUtil.getCharsetByHttpSource(htmlSource);//找到网站编码
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (findCharset.equalsIgnoreCase(StaticValue.defaultEncoding) || findCharset == null)//如果网站的编码与默认的编码UTF-8相同或者没有找到网站编码    就不用转换
            {

            }
            else//如果网站的编码与默认的编码UTF-8不相同相同就要用找出来的网站编码重新转换一次
            {
                try {
                    htmlSource = new String(contentByteArray,findCharset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        else//在responseheader中找到了
        {
            try {
                htmlSource = new String(contentByteArray,findCharset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return htmlSource;
    }


}
