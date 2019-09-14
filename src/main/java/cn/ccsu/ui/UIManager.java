package cn.ccsu.ui;

import cn.ccsu.utils.StaticValue;

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
    public String getRootUrlByDirect()
    {
        return "http://news.youth.cn/gn/";
    }

    public String getRootUrlByStaticValue()
    {
        return StaticValue.rootUrl;
    }

    public String getRootUrlBySeedFile()
    {
        return null;
    }






}
