package cn.ccsu.utils;

/**
 * Created with IntelliJ IDEA.
 * Description:存放项目中的静态变量
 *
 * @author: TheFei
 * @Date: 2019-09-14
 * @Time: 21:20
 */
public class StaticValue
{
    /**
     *  根Url的静态常量定义
     */
    public static String rootUrl="http://news.youth.cn/gn/";
    /**
     *  根Url的标题
     */
    public static String rootTitle="中国青年网-国内新闻";
    /**
     *  种子文件默认文件路径（非classpath下）
     */
    public static String rootUrlFilePath = "seeds.txt";
    /**
     *  系统配置文件,文件路径
     */
    public static String systemConfigFilePath = "spider.properties";
    /**
     *  默认读取文件的编码设置
     */
    public static String defaultEncoding = "UTF-8";
    /**
     *  分隔符号静态设置
     */
    public static String sep_next_line = "\n";
    /**
     *  分号静态设置
     */
    public static String sep_semicolon = ";";
    /**
     *  等号静态设置
     */
    public static String sep_equal = "=";
}
