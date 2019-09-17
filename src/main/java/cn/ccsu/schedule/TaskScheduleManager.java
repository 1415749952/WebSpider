package cn.ccsu.schedule;

import cn.ccsu.pojos.UrlTaskPojo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 任务调度器(单例的)，负责任务的调度，决定什么任务先被采集，什么任务后被采集（生产者消费者模式的池子）
 *
 * @author: TheFei
 * @Date: 2019-09-14
 * @Time: 23:33
 */
public class TaskScheduleManager
{
    private static volatile TaskScheduleManager taskScheduleManager;
    private LinkedList<UrlTaskPojo> todoTaskPojoList = new LinkedList<>();//待采集url
    private LinkedList<UrlTaskPojo> doneTaskPojoList = new LinkedList<>();//已经采集完的url


    private TaskScheduleManager() {
    }

    public LinkedList<UrlTaskPojo> getTodoTaskPojoList() {
        return todoTaskPojoList;
    }

    public void setTodoTaskPojoList(LinkedList<UrlTaskPojo> todoTaskPojoList) {
        this.todoTaskPojoList = todoTaskPojoList;
    }

    public LinkedList<UrlTaskPojo> getDoneTaskPojoList() {
        return doneTaskPojoList;
    }

    public void setDoneTaskPojoList(LinkedList<UrlTaskPojo> doneTaskPojoList) {
        this.doneTaskPojoList = doneTaskPojoList;
    }


    public static synchronized TaskScheduleManager getTaskScheduleManager()
    {
        if (taskScheduleManager == null)
        {
            synchronized (TaskScheduleManager.class)
            {
                if (taskScheduleManager == null)
                {
                    taskScheduleManager = new TaskScheduleManager();
                }
            }
        }
        return taskScheduleManager;
    }



    /**
     * 添加待采集的Url,以List<UrlTaskPojo> todoAddTaskList集合的形式添加
     * @param todoAddTaskList
     * @return
     */
    public boolean addUrlTaskPojoList(List<UrlTaskPojo> todoAddTaskList)
    {
        boolean b = todoTaskPojoList.addAll(todoAddTaskList);
        return b;
    }

    /**
     * 添加待采集的Url,以UrlTaskPojo todoAddTaskPojo单个对象的形式添加
     * @param todoAddTaskPojo
     * @return
     */
    public boolean addOneUrlTaskPojo(UrlTaskPojo todoAddTaskPojo)
    {
        boolean b = todoTaskPojoList.add(todoAddTaskPojo);
        return b;
    }

    /**
     * 添加已经采集完了的Url,以UrlTaskPojo doneAddTaskPojo单个对象的形式添加
     * @param doneAddTaskPojo
     * @return
     */
    public boolean addDoneUrlTaskPojo(UrlTaskPojo doneAddTaskPojo)
    {
        boolean b = doneTaskPojoList.add(doneAddTaskPojo);
        return b;
    }

    /**
     * 移除待采集的Url,以List<UrlTaskPojo> todoRemoveTaskList集合的形式移除
     * @param todoRemoveTaskList
     * @return
     */
    public boolean removeUrlTaskPojoList(List<UrlTaskPojo> todoRemoveTaskList)
    {
        boolean b = todoTaskPojoList.removeAll(todoRemoveTaskList);
        return b;
    }
    /**
     * 移除待采集的Url,以UrlTaskPojo todoRemoveTaskPojo单个对象的形式移除
     * @param todoRemoveTaskPojo
     * @return
     */
    public boolean removeOneUrlTaskPojo(UrlTaskPojo todoRemoveTaskPojo)
    {
        boolean b = todoTaskPojoList.remove(todoRemoveTaskPojo);
        return b;
    }

    /**
     * 获取待采集的 UrlTaskPojo
     * @return
     */
    public UrlTaskPojo tack()
    {
        UrlTaskPojo taskPojo = todoTaskPojoList.pollFirst();
        return taskPojo;
    }






}
