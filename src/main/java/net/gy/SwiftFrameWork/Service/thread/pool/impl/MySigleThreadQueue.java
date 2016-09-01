package net.gy.SwiftFrameWork.Service.thread.pool.impl;

import net.gy.SwiftFrameWork.Service.thread.pool.control.ThreadPoolControl;
import net.gy.SwiftFrameWork.Service.thread.pool.strategy.SigleThreadPool;

/**
 * Created by gy on 2016/2/22.
 */
public class MySigleThreadQueue {

    private static ThreadPoolControl control;

    private static void initPool(){
        control = new ThreadPoolControl(new SigleThreadPool());
        control.poolstart();
    }

    public static void AddTask(Runnable runnable){
        synchronized (MySigleThreadQueue.class){
            if (control == null)
                initPool();
        }
        control.submit(runnable);
    }
}
