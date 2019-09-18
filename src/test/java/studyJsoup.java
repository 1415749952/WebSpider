import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.nio.cs.UTF_32;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: TheFei
 * @Date: 2019-09-18
 * @Time: 13:53
 */
public class studyJsoup
{
    public static void main(String[] args)
    {
        File file = new File("D:\\oo.html");
        String readFileToString = null;
        try {
            readFileToString = FileUtils.readFileToString(file,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(readFileToString);

        Elements meta = doc.getElementsByTag("meta");
        for (Element element : meta) {
            String content = element.attr("content");
            System.out.println(content);
        }



        //获取title 内容
       /* Elements titleList = doc.getElementsByTag("title");
        Element title = titleList.first();
        System.out.println(title.text());*/
        /////////////////////////////////////////////////////////
       /* Element wypl = doc.getElementById("wypl");
        System.out.println();
        wypl = wypl.getElementById("SOHUCS");

        System.out.println(wypl.outerHtml());*/
////////////////////////////////////////////////////
       /* Elements tj3_1 = doc.getElementsByClass("tj3_1");
        Element first = tj3_1.first();

        //System.out.println(first);
        Elements elementsByTag = first.getElementsByTag("li");*/

/*
        Elements elementsByTag = doc.select("#sd>#q>font");


        System.out.println(elementsByTag);*/
       /* for (Element element : elementsByTag) {
            String time = element.getElementsByTag("font").text();
            //System.out.println(time);
            String title = element.getElementsByTag("a").text();

            String url = element.getElementsByTag("a").attr("href");
            System.out.println(time+"****"+title+"---------"+url);
        }*/

    }
}
