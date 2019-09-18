package cn.ccsu.iface;

import cn.ccsu.pojos.entity.NewsItemEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 网页解析接口，用什么解析jsoup,xpath,正则表达式
 *
 * @author: TheFei
 * @Date: 2019-09-18
 * @Time: 12:22
 */
public interface HtmlParserInterface<T>
{
    public List<T> parserHtmlSource(String htmlSource);



}
