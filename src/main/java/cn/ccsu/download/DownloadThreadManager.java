package cn.ccsu.download;

import cn.ccsu.iface.ThreadManager;
import cn.ccsu.pojos.UrlTaskPojo;
import cn.ccsu.schedule.TaskScheduleManager;
import cn.ccsu.ui.UIManager;
import cn.ccsu.utils.StaticValue;
import cn.ccsu.utils.SystemConfigParas;
import com.sun.org.apache.xml.internal.security.Init;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:网页下载线程管理器，起多少个线程，线程什么时候开启，叫什么名字。（单例的）
 *
 * @author: TheFei
 * @Date: 2019-09-16
 * @Time: 23:49
 */
public class DownloadThreadManager implements ThreadManager
{
    //线程组
    public ThreadGroup threadGroup = new ThreadGroup("download_threadGroup");
    public List<Runnable> runnableList = new ArrayList<>();
    public int InitConsumerNumber = 0;

    private static volatile DownloadThreadManager downloadThreadManager;

    private DownloadThreadManager()
    {
    }

    public List<Runnable> getRunnableList() {
        return runnableList;
    }

    public void setRunnableList(List<Runnable> runnableList) {
        this.runnableList = runnableList;
    }

    public static synchronized DownloadThreadManager getDownloadThreadManager()
    {
        if (downloadThreadManager == null)
        {
            synchronized (TaskScheduleManager.class)
            {
                if (downloadThreadManager == null)
                {
                    downloadThreadManager = new DownloadThreadManager();
                }
            }
        }
        return downloadThreadManager;
    }

    /**
     * 创建下载线程，从配置文件中读取要创建下载线程的个数
     */
    @Override
    public void createThread()
    {
        InitConsumerNumber= SystemConfigParas.init_consumer_number;
        for (int i = 1; i <= InitConsumerNumber; i++)
        {
            DownLoadRunnable onRunnable = new DownLoadRunnable("download_consumer_"+i);
            new Thread(threadGroup,onRunnable,"thread_"+i).start();
            runnableList.add(onRunnable);
        }
    }


    /**
     * 创建下载线程，不从配置文件中读取要创建下载线程的个数
     * @param consumerNumber 创建下载线程个数
     */
    public void createThread(int consumerNumber)
    {
        InitConsumerNumber=consumerNumber;
        for (int i = 1; i <= InitConsumerNumber; i++)
        {
            DownLoadRunnable onRunnable = new DownLoadRunnable("download_consumer_"+i);
            new Thread(threadGroup,onRunnable,"thread_"+i).start();
            runnableList.add(onRunnable);
        }
    }

    /**
     * 获取下载线程的状态信息----有多少个还活着的下载线程
     * @return 还活着的下载线程的个数
     */
    @Override
    public int getActiveThreads()
    {
        return threadGroup.activeCount();
    }

    /**
     * 获取一共初始化了多少个下载线程
     * @return 初始化线程个数
     */
    @Override
    public int getInitThreads()
    {
        return InitConsumerNumber;
    }
    /**
     * 停止所有的下载线程
     */
    @Override
    public void stopAllThreads()
    {
        for (Runnable runnable : runnableList)
        {
            DownLoadRunnable downLoadRunnable = (DownLoadRunnable)runnable;
            downLoadRunnable.setEnableRunningFllag(false);
        }
    }

    /**
     * 停掉某个runnable对象
     * @param runnable
     */
    @Override
    public void stopOneThread(Runnable runnable)
    {
        DownLoadRunnable downLoadRunnable = (DownLoadRunnable)runnable;
        downLoadRunnable.setEnableRunningFllag(false);
    }



    public static void main(String[] args)
    {
        //new UIManager().addSeedUrlsToTaskSchedule();
        //UrlTaskPojo todo = new UIManager().getRootUrlByStaticValue();

        new UIManager().addUrlsToTaskSchedule(StaticValue.rootUrlFilePath);//

        new UIManager().addUrlsToTaskSchedule(new UrlTaskPojo("csdn","https://bbs.csdn.net/topics/370024556"));
        new UIManager().addUrlsToTaskSchedule(new UrlTaskPojo("52pojie","https://www.52pojie.cn/"));


        //String url = "https://bbs.csdn.net/topics/370024556";
        //String url ="https://www.52pojie.cn/";
        //String url ="https://www.xl720.com/";

        DownloadThreadManager.getDownloadThreadManager().createThread();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new UIManager().addUrlsToTaskSchedule(new UrlTaskPojo("xl720","https://www.xl720.com/"));

    }


}
