package cn.ccsu.utils;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 读取数据文件
 *
 * @author: TheFei
 * @Date: 2019-09-14
 * @Time: 21:44
 */
public class IOUtil
{
    /**
     *
     * @param filePath 文件路径
     * @param isClasspath 是不是类路径下，是的就为true，否就为false
     * @param charSet 文件的编码
     * @return 读取的配置文件
     * @throws Exception
     */
    public static List<String> readFileToList(String filePath,boolean isClasspath,String charSet) throws Exception
    {
        InputStream is = null;
        if (isClasspath)
        {
            is = ReadConfigUtil.class.getClassLoader().getResourceAsStream(filePath);
        }
        else
        {
            is = new FileInputStream(filePath);
        }
        InputStreamReader isr = new InputStreamReader(is,charSet);
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        List<String> lineList = new ArrayList<>();
        while ((line = br.readLine()) != null)
        {
            lineList.add(line);
        }
        br.close();
        return lineList;
    }
    @Test
    public void testreadFileToList()
    {
        try {
            List<String> strings = IOUtil.readFileToList("seeds.txt", false, StaticValue.defaultEncoding);
            for (String string : strings) {
                System.out.println(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
