package cn.ccsu.utils;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:读取配置文件工具类，既支持classpath下的，也支持读取外置配置
 *
 * @author: TheFei
 * @Date: 2019-09-14
 * @Time: 21:27
 */
public class ReadConfigUtil
{
  //初始化javase自带的配置文件读取工具类
    private Properties configObj = new Properties();

    public ReadConfigUtil(String configFilePath)
    {
        File configFile = new File(configFilePath);
        InputStream is = null;
        Reader reader = null;
        if (configFile.exists())
        {
            try {
                is = new FileInputStream(configFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            is = ReadConfigUtil.class.getClassLoader().getResourceAsStream(configFilePath);
        }
        reader = new InputStreamReader(is);
        try {
            configObj.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key)
    {
        return configObj.getProperty(key);
    }

   public static void main(String[] args)
   {
       String configFilePath = StaticValue.systemConfigFilePath;
       ReadConfigUtil readConfigUtil = new ReadConfigUtil(configFilePath);
       String init_consumer_number = readConfigUtil.getValue("init_consumer_number");
       System.out.println(init_consumer_number);
   }

}
