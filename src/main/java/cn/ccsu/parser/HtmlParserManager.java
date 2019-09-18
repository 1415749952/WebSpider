package cn.ccsu.parser;

import cn.ccsu.iface.WebpageDownloadInterface;
import cn.ccsu.utils.RegexUtil;
import cn.ccsu.utils.WebpageDownloadUtilForHttpClient;

/**
 * Created with IntelliJ IDEA.
 * Description:正则表达式解析网页
 *
 * @author: TheFei
 * @Date: 2019-09-17
 * @Time: 21:50
 */
public class HtmlParserManager
{
    public static void main(String[] args)
    {
        WebpageDownloadInterface download = new WebpageDownloadUtilForHttpClient();
        String downloadHtml = download.downloadHtml("http://news.youth.cn/gn/");
        //System.out.println(downloadHtml);

        String itemRegex = "<ul class=\"tj3_1\">([\\s\\S]*?)</ul>";

        String matchText = RegexUtil.getMatchText(downloadHtml, itemRegex, 0);
        System.out.print(matchText);
        System.out.println("---------------------------------------------------------------");
        itemRegex = "<li>[\\s\\S]</li>";
        matchText = RegexUtil.getMatchText(downloadHtml, itemRegex, 0);
        System.out.println(matchText);

    }
}
