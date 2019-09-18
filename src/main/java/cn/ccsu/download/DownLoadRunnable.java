package cn.ccsu.download;


import cn.ccsu.iface.HtmlParserInterface;
import cn.ccsu.iface.WebpageDownloadInterface;
import cn.ccsu.parser.NewsItemParserForJsoup;
import cn.ccsu.persistence.DB;
import cn.ccsu.persistence.NewsItemEntityDao;
import cn.ccsu.pojos.UrlTaskPojo;
import cn.ccsu.pojos.entity.NewsItemEntity;
import cn.ccsu.schedule.TaskScheduleManager;
import cn.ccsu.utils.SystemConfigParas;
import cn.ccsu.utils.WebpageDownloadUtilForHttpClient;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 多线程下载url中的内容（生产者消费者模式的消费者）
 *
 * @author: TheFei
 * @Date: 2019-09-16
 * @Time: 21:34
 */
public class DownLoadRunnable implements Runnable
{
    static Logger logger = Logger.getLogger(DownLoadRunnable.class);//给DownLoadRunnable类加日志

    private boolean enableRunningFllag = true;//线程是可以运行的标志位true在跑； false为停止
    private String name;

    public DownLoadRunnable() {
    }

    public DownLoadRunnable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnableRunningFllag() {
        return enableRunningFllag;
    }

    public void setEnableRunningFllag(boolean enableRunningFllag) {
        this.enableRunningFllag = enableRunningFllag;
    }

    @Override
    public void run()
    {
        while (enableRunningFllag)
        {
            //WebpageDownloadInterface download = new WebpageDownloadUtilForUrlConnection();//使用UrlConnection下载网页内容
            WebpageDownloadInterface download = new WebpageDownloadUtilForHttpClient();//使用HttpClient下载网页内容

            LinkedList<UrlTaskPojo> todoTaskPojoList = TaskScheduleManager.getTaskScheduleManager().getTodoTaskPojoList();
            //打印任务调度器中所有要下载的任务
            logger.info(this.name+"读取任务调度器中所有要下载的任务   "+todoTaskPojoList.size());


            //从任务调度器中获取一个任务对象 UrlTaskPojo（title,url）标题和统一资源定位器url
            UrlTaskPojo tackPojo = TaskScheduleManager.getTaskScheduleManager().tack();
            if (tackPojo!=null)
            {
                String downloadHtmlSource = download.downloadHtml(tackPojo.getUrl());
                logger.info(this.name+"进入解析环节****"+tackPojo.getUrl());//进入解析环节
                if (downloadHtmlSource != null)
                {
                    //进入解析环节
                    HtmlParserInterface<NewsItemEntity> htmlParser = new NewsItemParserForJsoup();//使用Jsoup解析
                    List<NewsItemEntity> itemEntities = htmlParser.parserHtmlSource(downloadHtmlSource);

                    for (NewsItemEntity itemEntity : itemEntities) {
                        //System.out.println(this.name+"////////解析结果"+itemEntity);
                        DB.inserNewsItemEntitytoDB(itemEntity);//插入数据库
                    }
                    logger.info(this.name+"-------一个网页下载完成即将进入下一页");

                }
                else
                {
                    logger.error(this.name+"下载出错-----"+tackPojo.getUrl());
                    TaskScheduleManager.getTaskScheduleManager().addOneUrlTaskPojo(tackPojo);//将任务重新加入到任务调度器中
                    logger.info(this.name+"重新加入到任务调度器中"+tackPojo.getUrl());
                }
            }
            else
            {
                logger.info(this.name+"没有带采集的任务，线程将睡眠"+SystemConfigParas.once_sleep_time_for_empty_task/1000+"秒钟！");
                try {
                    Thread.sleep(SystemConfigParas.once_sleep_time_for_empty_task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info(this.name+"本次睡眠结束！");
            }
        }
        this.enableRunningFllag = false;
        logger.info(this.name+"线程run方法即将结束");
    }
    
    
   /* public static void main(String[] args)
    {
        new UIManager().addSeedUrlsToTaskSchedule();//将任务添加到生产者消费者模式的池子（TaskScheduleManager）中
        DownLoadRunnable downLoadRunnable1 = new DownLoadRunnable("Runnable--1");
        DownLoadRunnable downLoadRunnable2 = new DownLoadRunnable("Runnable--2");
        Thread thread = new Thread(downLoadRunnable1);
        Thread thread1 = new Thread(downLoadRunnable2);
        thread.start();
        thread1.start();
    }
    */
    
    
    
}
