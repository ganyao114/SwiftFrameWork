package net.gy.SwiftFrameWork.Service.thread.pool.strategy;

import net.gy.SwiftFrameWork.Service.thread.pool.IThreadPool;
import net.gy.SwiftFrameWork.Service.thread.pool.configs.ThreadPoolConfigs;
import net.gy.SwiftFrameWork.Service.thread.pool.factory.WorkThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by gy on 2016/2/22.
 */
public class SigleThreadPool implements IThreadPool{
    private ExecutorService executor;
    @Override
    public void submit(Runnable r) {
        executor.submit(r);
    }

    @Override
    public void poolstart() {
        ThreadFactory factory = new WorkThreadFactory("thread-pool",
                android.os.Process.THREAD_PRIORITY_BACKGROUND);
        executor = Executors.newSingleThreadExecutor(factory);
    }

    @Override
    public void poolstop() {
        executor.shutdown();
    }

    @Override
    public void setconfigs(ThreadPoolConfigs configs) {

    }
}
