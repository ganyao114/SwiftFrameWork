package net.gy.SwiftFrameWork.Service.thread.taskqueue.impl;

import net.gy.SwiftFrameWork.Service.thread.pool.configs.ThreadPoolConfigs;
import net.gy.SwiftFrameWork.Service.thread.pool.control.ThreadPoolControl;
import net.gy.SwiftFrameWork.Service.thread.pool.strategy.ExecutorThreadPool;
import net.gy.SwiftFrameWork.Service.thread.taskqueue.ITaskQueue;
import net.gy.SwiftFrameWork.Service.thread.taskqueue.entity.TaskPrio;

/**
 * Created by gy on 2016/2/22.
 */
public class SinglePoolTaskQueue implements ITaskQueue{
    private ThreadPoolControl poolControl;
    public SinglePoolTaskQueue(){
        ThreadPoolConfigs configs = new ThreadPoolConfigs();
        configs.setPOOL_SIZE(1);
        configs.setMAX_POOL_SIZE(6);
        poolControl = new ThreadPoolControl(new ExecutorThreadPool());
        poolControl.poolstart();
    }
    @Override
    public boolean addTask(Runnable runnable, TaskPrio prio) {
        poolControl.submit(runnable);
        return true;
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
}
