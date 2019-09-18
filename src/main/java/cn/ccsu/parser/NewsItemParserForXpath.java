package cn.ccsu.parser;

import cn.ccsu.iface.HtmlParserInterface;
import cn.ccsu.pojos.entity.NewsItemEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: TheFei
 * @Date: 2019-09-18
 * @Time: 12:32
 */
public class NewsItemParserForXpath implements HtmlParserInterface
{

    @Override
    public List<NewsItemEntity> parserHtmlSource(String htmlSource) {
        return null;
    }
}
