package cn.ccsu.parser;

import cn.ccsu.iface.HtmlParserInterface;
import cn.ccsu.iface.WebpageDownloadInterface;
import cn.ccsu.pojos.entity.NewsItemEntity;
import cn.ccsu.utils.JsoupUtil;
import cn.ccsu.utils.WebpageDownloadUtilForHttpClient;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
public class NewsItemParserForJsoup implements HtmlParserInterface<NewsItemEntity>
{

    @Override
    public List<NewsItemEntity> parserHtmlSource(String htmlSource)
    {
        List<NewsItemEntity> newsItemEntityList =new ArrayList<>();
        String selector = "ul.tj3_1>li";
        Elements elementsBySelector = JsoupUtil.getElementsBySelector(htmlSource, selector);
        for (Element element : elementsBySelector) {

            String time = element.getElementsByTag("font").text();
            //System.out.println(time);
            String title = element.getElementsByTag("a").text();

            String url = element.getElementsByTag("a").attr("href");
            url ="http:"+url;
            //System.out.println(time+"****"+title+"---------"+url);
            newsItemEntityList.add(new NewsItemEntity(title,url,time));
        }
        return newsItemEntityList;
    }

    public static void main(String[] args)
    {
        WebpageDownloadInterface downloadInterface = new WebpageDownloadUtilForHttpClient();
        String downloadHtml = downloadInterface.downloadHtml("http://news.youth.cn/gn/");
        List<NewsItemEntity> itemEntities = new NewsItemParserForJsoup().parserHtmlSource(downloadHtml);
        for (NewsItemEntity itemEntity : itemEntities) {
            System.out.println(itemEntity);
        }
    }
}
