package net.gy.SwiftFrameWork.Service.thread.taskqueue;

import net.gy.SwiftFrameWork.Service.thread.taskqueue.entity.TaskPrio;

/**
 * Created by gy on 2016/1/27.
 */
public interface ITaskQueue {
    public boolean addTask(Runnable runnable, TaskPrio prio);
    public boolean runTask();
    public boolean removeTask(Runnable runnable);
    public boolean clearTaskQueue();
}
