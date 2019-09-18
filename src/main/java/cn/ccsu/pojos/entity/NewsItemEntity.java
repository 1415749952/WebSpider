package cn.ccsu.pojos.entity;

import cn.ccsu.utils.DateUtil;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:存储解析下来的新闻条目对象
 *
 * @author: TheFei
 * @Date: 2019-09-18
 * @Time: 11:56
 */
public class NewsItemEntity
{
    private int id;
    private String title;
    private String postTimeString;
    private String sourceURL;
    private Date insertDate;
    private Date postDateObj;


    public NewsItemEntity() {
    }

    public NewsItemEntity(String title, String sourceURL, String postTimeString) {
        this.title = title;
        this.sourceURL = sourceURL;
        this.postTimeString = postTimeString;

        this.insertDate = DateUtil.getDate();
        this.postDateObj = DateUtil.paeserStringToDate(postTimeString);
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostTimeString() {
        return postTimeString;
    }

    public void setPostTimeString(String postTimeString) {
        this.postTimeString = postTimeString;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    @Override
    public String toString() {
        return "NewsItemEntity{" +
                "title='" + title + '\'' +
                ", postTimeString='" + postTimeString + '\'' +
                ", sourceURL='" + sourceURL + '\'' +
                ", insertDate=" + insertDate +
                ", postDateObj=" + postDateObj +
                '}';
    }
}
