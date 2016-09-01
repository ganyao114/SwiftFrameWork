package net.gy.SwiftFrameWork.Service.thread.pool.strategy;

import net.gy.SwiftFrameWork.Service.thread.pool.IThreadPool;
import net.gy.SwiftFrameWork.Service.thread.pool.configs.ThreadPoolConfigs;
import net.gy.SwiftFrameWork.Service.thread.pool.factory.WorkThreadFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorThreadPool implements IThreadPool {

    private ExecutorService mExecutor;
    private ThreadPoolConfigs configs;


    public Future submit(Runnable command) {
        return mExecutor.submit(command);
    }

    @Override
    public Future submit(Callable r) {
        return mExecutor.submit(r);
    }

    @Override
    public <T> Future submit(Runnable task, T result) {
        return mExecutor.submit(task,result);
    }


    @Override
    public void poolstart() {
        ThreadFactory factory = new WorkThreadFactory("thread-pool",
                android.os.Process.THREAD_PRIORITY_BACKGROUND);
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
        mExecutor = new ThreadPoolExecutor(configs.getPOOL_SIZE(), configs.getMAX_POOL_SIZE(),
                configs.getKEEP_ALIVE_TIME(), TimeUnit.SECONDS, workQueue, factory);
    }

    @Override
    public void poolstop() {

    }

    @Override
    public void setconfigs(ThreadPoolConfigs configs) {
        this.configs = configs;
    }

}
