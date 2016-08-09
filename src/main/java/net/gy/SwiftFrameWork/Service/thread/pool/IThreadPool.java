package net.gy.SwiftFrameWork.Service.thread.pool;

import net.gy.SwiftFrameWork.Service.thread.pool.configs.ThreadPoolConfigs;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by gy on 2015/11/6.
 */
public interface IThreadPool {
    public Future submit(Runnable r);

    public Future submit(Callable r);

    public <T> Future submit(Runnable task, T result);

    public void poolstart();

    public void poolstop();

    public void setconfigs(ThreadPoolConfigs configs);
}
