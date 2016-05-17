package net.gy.SwiftFrameWork.Service.thread.taskqueue.impl;

import net.gy.SwiftFrameWork.Service.thread.IThreadCallback;
import net.gy.SwiftFrameWork.Service.thread.taskqueue.ITaskQueue;
import net.gy.SwiftFrameWork.Service.thread.taskqueue.entity.AutoRunTask;
import net.gy.SwiftFrameWork.Service.thread.taskqueue.entity.TaskPrio;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gy on 2016/2/22.
 */
public class AutoRunTaskQueue implements ITaskQueue,IThreadCallback {

    private List<AutoRunTask> tasks;

    public AutoRunTaskQueue(){
        tasks = new LinkedList<AutoRunTask>();
    }

    @Override
    public boolean addTask(Runnable runnable, TaskPrio prio) {
        return false;
    }

    @Override
    public boolean runTask() {
        return false;
    }

    @Override
    public boolean removeTask(Runnable runnable) {
        return false;
    }

    @Override
    public boolean clearTaskQueue() {
        return false;
    }

    @Override
    public void OnStop() {

    }
}
