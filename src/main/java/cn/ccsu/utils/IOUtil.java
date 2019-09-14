package cn.ccsu.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public static List<String> readFileToList(String filePath,boolean isCalsspath,String charSet) throws Exception
    {
        InputStream is = null;
        if (isCalsspath)
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
}
