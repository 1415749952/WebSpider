package cn.ccsu.utils;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description:正则表达式工具类
 *
 * @author: TheFei
 * @Date: 2019-09-15
 * @Time: 14:10
 */
public class RegexUtil
{

    /**
     *
     * @param input 要切分的字符串
     * @param regex 正则表达式
     * @param groupIndex 组号
     * @return 切分后的字符串
     */
    public static String getMatchText(String input,String regex,int groupIndex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find())
        {
            return matcher.group(groupIndex);
        }
        return null;
    }

}
