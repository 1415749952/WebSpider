package cn.ccsu.utils;

import cn.ccsu.iface.WebpageDownloadInterface;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: jsoup操作工具类封装
 *
 * @author: TheFei
 * @Date: 2019-09-18
 * @Time: 11:03
 */
public class JsoupUtil
{
    public static Document getDocument(String htmlSource)
    {
        return Jsoup.parse(htmlSource);
    }
    public static Elements getElementsBySelector(String htmlSource,String selector)
    {
        Document document = getDocument(htmlSource);
        return document.select(selector);
    }

    public static void main(String[] args)
    {
        WebpageDownloadInterface downloadInterface = new WebpageDownloadUtilForHttpClient();
        String downloadHtml = downloadInterface.downloadHtml("http://news.youth.cn/gn/");

        //形成htmlSource对应的dom对象
        Document doc = Jsoup.parse(downloadHtml);

        //解析dom对象
        //Elements tj3_1 = doc.getElementsByClass("tj3_1>li");
        Elements select = doc.select("ul.tj3_1>li");

        //System.out.println(tj3_1);
       for (Element element : select) {
            //System.out.println(element);
           //element.
            System.out.println("--------------------------------------");
        }


    }
}
