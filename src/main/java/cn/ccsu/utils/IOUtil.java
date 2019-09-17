package cn.ccsu.utils;

import jdk.internal.util.xml.impl.Input;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    private static Logger logger = Logger.getLogger(IOUtil.class);
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
        logger.info("配置文件"+filePath+"读取成功");
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

    public static BufferedReader getBufferedReader(URLConnection urlConnection,String charset) throws IOException
    {
        InputStream is = urlConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, charset);
        BufferedReader br = new BufferedReader(isr);
        return br;
    }
    public static BufferedReader getBufferedReader(String url,String charset) throws IOException
    {
        URL urlObj = new URL(url);
        URLConnection urlConnection = urlObj.openConnection();
        return getBufferedReader(urlConnection,charset);
    }

    /**
     * 将字节输入流转变成字节数字
     * @param inputStream 自己输入流
     * @return 字节数组
     * @throws IOException
     */
    public static byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException
    {
        byte[] byteBuffer = new byte[1024*4];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int readlenth = 0;
        while ((readlenth = inputStream.read(byteBuffer)) != -1)
        {
            bos.write(byteBuffer,0,readlenth);
        }
        return bos.toByteArray();
    }

    public static URLConnection getUrlConnection(String url)
    {
        URL urlObj = null;
        try {
            urlObj = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection urlConnection = null;
        try {
            urlConnection = urlObj.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlConnection;
    }


}
