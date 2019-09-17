package cn.ccsu.iface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:线程管理接口
 *
 * @author: TheFei
 * @Date: 2019-09-17
 * @Time: 13:14
 */
public interface ThreadManager
{
    public void createThread(int consumerNumber);
    public int getActiveThreads();
    public int getInitThreads();
    public void stopAllThreads();
    public void stopOneThread(Runnable runnable);
}
