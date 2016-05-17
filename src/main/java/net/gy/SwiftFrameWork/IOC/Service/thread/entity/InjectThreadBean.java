package net.gy.SwiftFrameWork.IOC.Service.thread.entity;

import net.gy.SwiftFrameWork.IOC.Service.thread.handler.BaseHandler;

import java.util.List;

/**
 * Created by gy on 2015/11/26.
 */
public class InjectThreadBean {

    private Thread thread;
    private Runnable runnable;
    private List<BaseHandler> handlers;

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public List<BaseHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<BaseHandler> handlers) {
        this.handlers = handlers;
    }

    public void invoke() {

    }

}
