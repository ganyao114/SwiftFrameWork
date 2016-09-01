package net.gy.SwiftFrameWork.Service.thread.taskqueue.control;

import net.gy.SwiftFrameWork.Service.thread.taskqueue.ITaskQueue;
import net.gy.SwiftFrameWork.Service.thread.taskqueue.entity.QueueConfigs;
import net.gy.SwiftFrameWork.Service.thread.taskqueue.entity.TaskPrio;

/**
 * Created by gy on 2016/1/27.
 */
public class TaskQueueControl implements ITaskQueue {

    private ITaskQueue impl;
    private QueueConfigs configs;

    public TaskQueueControl(ITaskQueue impl) {
        this.impl = impl;
    }

    public TaskQueueControl(ITaskQueue impl, QueueConfigs configs){
        this(impl);
        this.configs = configs;
    }

    @Override
    public boolean addTask(Runnable runnable, TaskPrio prio) {
        return impl.addTask(runnable,prio);
    }

    @Override
    public boolean runTask() {
        return impl.runTask();
    }

    @Override
    public boolean removeTask(Runnable runnable) {
        return impl.removeTask(runnable);
    }

    @Override
    public boolean clearTaskQueue() {
        return impl.clearTaskQueue();
    }

    public ITaskQueue getImpl() {
        return impl;
    }

    public void setImpl(ITaskQueue impl) {
        this.impl = impl;
    }
}
