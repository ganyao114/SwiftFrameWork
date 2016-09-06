package net.gy.SwiftFrameWork.Service.thread.taskqueue.impl;

import android.os.Handler;
import android.os.HandlerThread;

import net.gy.SwiftFrameWork.Service.thread.taskqueue.ITaskQueue;
import net.gy.SwiftFrameWork.Service.thread.taskqueue.entity.TaskPrio;

/**
 * Created by pc on 16/8/11.
 */
public class SigleTaskQueueByHandlerThread implements ITaskQueue{

    private Handler handler;
    private HandlerThread queue;

    public void initTaskQueue(String name){
        queue = new HandlerThread(name);
        queue.start();
        handler = new Handler(queue.getLooper());
    }


    @Override
    public boolean addTask(Runnable runnable, TaskPrio prio) {
        if (handler == null) {
            synchronized (this) {
                if (handler == null)
                    initTaskQueue("defaultQueue");
            }
        }
        boolean res = false;
        switch (prio){
            case First:
                res = handler.postAtFrontOfQueue(runnable);
                break;
            case Last:
                res = handler.post(runnable);
                break;
            default:
                res = handler.post(runnable);
                break;
        }
        return res;
    }

    @Override
    public boolean runTask() {
        return false;
    }

    @Override
    public boolean removeTask(Runnable runnable) {
        handler.removeCallbacks(runnable);
        return true;
    }

    @Override
    public boolean clearTaskQueue() {
        queue.getLooper().quit();
        return true;
    }
}
