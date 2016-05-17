package net.gy.SwiftFrameWork.Service.thread.taskqueue.entity;

import net.gy.SwiftFrameWork.Service.thread.IThreadCallback;

/**
 * Created by gy on 2016/2/22.
 */
public abstract class AutoRunTask implements Runnable{
    private IThreadCallback callback;
    public AutoRunTask(IThreadCallback callback){
        this.callback = callback;
    }
    @Override
    public void run() {
        onRun();
        callback.OnStop();
    }

    protected abstract void onRun();
}
