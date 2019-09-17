package cn.ccsu.pojos;

/**
 * Created with IntelliJ IDEA.
 * Description:对url任务的封装类，UrlTaskPojo实体类
 *
 * @author: TheFei
 * @Date: 2019-09-14
 * @Time: 22:17
 */
public class UrlTaskPojo
{
    private String title;
    private String url;

    public UrlTaskPojo() {
    }

    public UrlTaskPojo(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UrlTaskPojo{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
