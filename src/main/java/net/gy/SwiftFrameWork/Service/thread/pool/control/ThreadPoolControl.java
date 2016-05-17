package net.gy.SwiftFrameWork.Service.thread.pool.control;

import net.gy.SwiftFrameWork.Service.thread.pool.IThreadPool;
import net.gy.SwiftFrameWork.Service.thread.pool.configs.ThreadPoolConfigs;

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
    public synchronized void submit(Runnable r) {
        poolstrategy.submit(r);
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
