package net.gy.SwiftFrameWork.Service.thread.pool.control;

import net.gy.SwiftFrameWork.Service.thread.pool.IThreadPool;
import net.gy.SwiftFrameWork.Service.thread.pool.configs.ThreadPoolConfigs;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by gy on 2015/11/6.
 */
public class ThreadPoolControl implements IThreadPool {

    private IThreadPool poolstrategy;
    protected ThreadPoolConfigs configs;

    public ThreadPoolControl() {
        super();
    }

    public ThreadPoolControl(IThreadPool poolstrategy) {
        configs = new ThreadPoolConfigs();
        this.poolstrategy = poolstrategy;
        poolstrategy.setconfigs(configs);
    }

    @Override
    public synchronized Future submit(Runnable r) {
        return poolstrategy.submit(r);
    }

    @Override
    public Future submit(Callable r) {
        return poolstrategy.submit(r);
    }

    @Override
    public <T> Future submit(Runnable task, T result) {
        return poolstrategy.submit(task, result);
    }

    @Override
    public synchronized void poolstart() {
        poolstrategy.poolstart();
    }

    @Override
    public synchronized void poolstop() {
        poolstrategy.poolstop();
    }

    @Override
    public void setconfigs(ThreadPoolConfigs configs) {
        poolstrategy.setconfigs(configs);
    }
}
