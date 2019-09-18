package cn.ccsu.parser;

import cn.ccsu.iface.HtmlParserInterface;
import cn.ccsu.iface.WebpageDownloadInterface;
import cn.ccsu.pojos.entity.NewsItemEntity;
import cn.ccsu.utils.JsoupUtil;
import cn.ccsu.utils.WebpageDownloadUtilForHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 通过jsoup解析网页
 *
 * @author: TheFei
 * @Date: 2019-09-18
 * @Time: 12:31
 */
public class NewsItemParserForJsoup implements HtmlParserInterface
{

    @Override
    public List<NewsItemEntity> parserHtmlSource(String htmlSource)
    {
        List<NewsItemEntity> newsItemEntityList =new ArrayList<>();
        String selector = "ul.tj3_1>li";
        List<String> elementsBySelector = JsoupUtil.getElementsBySelector(htmlSource, selector, JsoupUtil.ContentSelectType.TEXT);
        for (String s : elementsBySelector) {
            System.out.println(s);
        }
        return null;
    }

    public static void main(String[] args)
    {
        WebpageDownloadInterface downloadInterface = new WebpageDownloadUtilForHttpClient();
        String downloadHtml = downloadInterface.downloadHtml("http://news.youth.cn/gn/");
        List<NewsItemEntity> itemEntities = new NewsItemParserForJsoup().parserHtmlSource(downloadHtml);
    }
}
