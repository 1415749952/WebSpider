package cn.ccsu.utils;

/**
 * Created with IntelliJ IDEA.
 * Description:系统配置参数工具类，集中读取
 *
 * @author: TheFei
 * @Date: 2019-09-17
 * @Time: 21:09
 */
public class SystemConfigParas
{
    public static ReadConfigUtil configUtil = new ReadConfigUtil(StaticValue.systemConfigFilePath);;

    /**
     * 读取download初始化时候的下载消费线程的数量
     */
    public static int init_consumer_number = Integer.parseInt(configUtil.getValue("init_consumer_number")) ;

    /**
     * 读取download的下载线程获取到空任务的时候，一次睡眠时间，单位秒。
     */
    public static int once_sleep_time_for_empty_task = Integer.parseInt(configUtil.getValue("once_sleep_time_for_empty_task"))*1000 ;
}
