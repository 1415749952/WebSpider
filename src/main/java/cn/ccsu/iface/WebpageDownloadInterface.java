package cn.ccsu.iface;

/**
 * Created with IntelliJ IDEA.
 * Description: 下载网页接口，有HttpClient下载方式，UrlConnection下载方式
 *
 * @author: TheFei
 * @Date: 2019-09-16
 * @Time: 18:28
 */
public interface WebpageDownloadInterface
{
    public String downloadHtml(String url);
}
