package net.gy.SwiftFrameWork.Service.thread.pool.impl;

import net.gy.SwiftFrameWork.Service.thread.pool.control.ThreadPoolControl;
import net.gy.SwiftFrameWork.Service.thread.pool.strategy.ExecutorThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public class MyWorkThreadQueue {

    private static ThreadPoolControl mythreadpool;

    private static void initPool() {
        mythreadpool = new ThreadPoolControl(new ExecutorThreadPool());
        mythreadpool.poolstart();
    }


    public static Future AddTask(Runnable r) {
        if (mythreadpool == null)
            initPool();
        return mythreadpool.submit(r);
    }

}
