package net.gy.SwiftFrameWork.Service.cache.impl;

import net.gy.SwiftFrameWork.Service.cache.control.CachePool;

import java.lang.ref.PhantomReference;

/**
 * Created by pc on 16/8/10.
 */
public class RecycleWorkThread implements Runnable{

    private boolean isRun = true;
    private Long tick = 1000l;  //ms
    private CachePool pool;

    public void init(CachePool pool){
        this.pool = pool;
    }

    @Override
    public void run() {
        while (isRun){
            recycle();
            try {
                Thread.currentThread().sleep(tick);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void recycle() {
        pool.getSize();
        pool.compress();
        pool.recycle();
        
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public Long getTick() {
        return tick;
    }

    public void setTick(Long tick) {
        this.tick = tick;
    }
}
