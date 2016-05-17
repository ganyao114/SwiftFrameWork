package net.gy.SwiftFrameWork.Service.thread.taskqueue.impl;

import net.gy.SwiftFrameWork.Service.thread.taskqueue.ITaskQueue;
import net.gy.SwiftFrameWork.Service.thread.taskqueue.entity.TaskPrio;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gy on 2016/1/27.
 */
public class DefaultTaskQueue implements ITaskQueue {

    private List<Runnable> list;

    public DefaultTaskQueue() {
        list = new LinkedList<Runnable>();
    }

    @Override
    public synchronized boolean addTask(Runnable runnable, TaskPrio prio) {
        if (runnable == null)
            return false;
        if (prio == TaskPrio.Last){
            list.add(runnable);
        }else if (prio == TaskPrio.First){
            list.add(0,runnable);
        }
        return true;
    }

    @Override
    public synchronized boolean runTask() {
        if (list == null||list.size()==0)
            return false;
        new Thread(list.remove(0))
                .start();
        return true;
    }

    @Override
    public synchronized boolean removeTask(Runnable runnable) {
        if (!list.contains(runnable))
            return false;
        list.remove(runnable);
        return true;
    }

    @Override
    public boolean clearTaskQueue() {
        if (list == null||list.size() == 0)
            return false;
        list.clear();
        return true;
    }
}
