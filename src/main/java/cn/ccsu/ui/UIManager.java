package cn.ccsu.ui;

import cn.ccsu.pojos.UrlTaskPojo;
import cn.ccsu.schedule.TaskScheduleManager;
import cn.ccsu.utils.IOUtil;
import cn.ccsu.utils.StaticValue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:该类作用为用户接口管理类，包括种子添加接口，种子添加的不同方式和来源
 *
 * @author: TheFei
 * @Date: 2019-09-14
 * @Time: 21:14
 */
public class UIManager
{
    public UrlTaskPojo getRootUrlByDirect()
    {
        return new UrlTaskPojo("中国青年网-国内新闻","http://news.youth.cn/gn/");
    }

    public UrlTaskPojo getRootUrlByStaticValue()
    {
        return new UrlTaskPojo(StaticValue.rootTitle,StaticValue.rootUrl);
    }

    /**
     * 通过文件拿去RootUrl。。。将从seeds.txt数据文件中读取List<UrlTaskPojo> seedUrlPojoList数据
     * @param dataFilePath 数据文件的路径
     * @param isClasspath 数据文件的路径是不是类路径,是的话为true，不是的话为false
     * @return List<UrlTaskPojo>，UrlTaskPojo集合
     */
    public List<UrlTaskPojo> getRootUrlBySeedFile(String dataFilePath,boolean isClasspath)
    {
        List<String> lineList = new ArrayList<>();
        List<UrlTaskPojo> resultTaskPojo = new ArrayList<>();
        try {
            lineList = IOUtil.readFileToList(dataFilePath, isClasspath, StaticValue.defaultEncoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String line : lineList)
        {
            line = line.trim();//用来删除字符串两端的空白字符并返回，trim方法并不影响原来的字符串本身，它返回的是一个新的字符串
            if (line.length() > 0 && !line.startsWith("#"))//不以#开头的为正常列
            {
                String[] columArray = line.split("\\s+");//中国青年网-国内新闻  http://news.youth.cn/gn/按照空格或者tab分割
                if(columArray.length == 2)
                {
                    resultTaskPojo.add(new UrlTaskPojo(columArray[0].trim(),columArray[1].trim()));
                }
                else
                {
                    System.err.println("错误行："+line);
                    try {
                        throw new Exception("存在不规范行");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return resultTaskPojo;
    }
    @Test
    public void testgetRootUrlBySeedFileFromClasspath()
    {
        UIManager uiManager = new UIManager();
        List<UrlTaskPojo> rootUrlBySeedFileFromClasspath = uiManager.getRootUrlBySeedFile("seeds.txt",false);
        for (UrlTaskPojo urlTaskPojo : rootUrlBySeedFileFromClasspath) {
            System.out.println(urlTaskPojo);
        }
    }

    /**
     * 将从seeds.txt数据文件中读取出来的List<UrlTaskPojo> seedUrlPojoList数据添加到任务调度器中
     * @return
     */
    public boolean addSeedUrlsToTaskSchedule()
    {
        List<UrlTaskPojo> seedUrlPojoList = this.getRootUrlBySeedFile("seeds.txt",false);
        TaskScheduleManager taskScheduleManager = TaskScheduleManager.getTaskScheduleManager();
        boolean b = taskScheduleManager.addUrlTaskPojoList(seedUrlPojoList);
        System.out.println("种子任务已经添加到任务调度管理器（TaskScheduleManager）中");
        return b;
    }
    @Test
    public void testaddSeedUrlsToTaskSchedule()
    {
        UIManager s = new UIManager();
        s.addSeedUrlsToTaskSchedule();
        TaskScheduleManager taskScheduleManager = TaskScheduleManager.getTaskScheduleManager();
        LinkedList<UrlTaskPojo> todoTaskPojoList = taskScheduleManager.getTodoTaskPojoList();

        System.out.println("任务调度管理器（TaskScheduleManager）中有"+todoTaskPojoList.size()+"个任务如下");
        for (UrlTaskPojo urlTaskPojo : todoTaskPojoList) {
            System.out.println(urlTaskPojo);
        }

    }



}
