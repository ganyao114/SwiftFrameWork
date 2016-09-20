package net.gy.SwiftFrameWork.MVVM.templet;

import net.gy.SwiftFrameWork.MVVM.Exception.HttpServiceException;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBackInner;
import net.gy.SwiftFrameWork.MVVM.templet.configs.HttpTheadConfigBean;
import net.gy.SwiftFrameWork.Service.thread.IThreadCallback;

import org.apache.http.conn.ConnectTimeoutException;

import java.lang.reflect.Method;
import java.net.SocketTimeoutException;
/**
 * Http线程体基类，封装异常处理等
 * Created by pc on 16/8/30.
 */
@SuppressWarnings("deprecation")
public abstract class HttpThreadTemplet implements Runnable {

    protected boolean isLoop = false;
    protected int tickTime = 0;
    protected HttpTheadConfigBean configBean;
    protected IThreadCallback threadCallback;
    protected ICallBackInner callBack;
    protected Method invoker;

    public HttpThreadTemplet(ICallBackInner callBack,Method invoker) {
        // TODO Auto-generated constructor stub
        this.callBack = callBack;
        this.configBean = HttpTheadConfigBean.Default();
        isLoop = configBean.isLoop;
        tickTime = configBean.tickTime;
        this.invoker = invoker;
    }



    public HttpThreadTemplet(ICallBackInner callBack,HttpTheadConfigBean configBean,Method invoker) {
        // TODO Auto-generated constructor stub
        this.callBack = callBack;
        this.configBean = configBean;
        isLoop = configBean.isLoop;
        tickTime = configBean.tickTime;
        this.invoker = invoker;
    }

    @SuppressWarnings("static-access")
    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (isLoop) {
            while (isLoop) {
                doRun();
                try {
                    Thread.currentThread().sleep(tickTime);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            doRun();
        }
        if (threadCallback != null)
            threadCallback.OnStop();
    }

    private void doRun() {
        try {
            OnRun();
        } catch (HttpServiceException e) {
            e.printStackTrace();
            callBack.onFailed(invoker,e);
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            callBack.onFailed(invoker,e);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            callBack.onFailed(invoker,e);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onFailed(invoker,e);
        }finally {
            onFinally();
        }
    }
    public synchronized void StopThread() {
        isLoop = false;
    }

    protected abstract void OnRun() throws Exception;


    protected void onFinally(){

    }

    public ICallBackInner getCallBack() {
        return callBack;
    }

    public void setCallBack(ICallBackInner callBack) {
        this.callBack = callBack;
    }

    public int getTickTime() {
        return tickTime;
    }

    public void setTickTime(int tickTime) {
        this.tickTime = tickTime;
    }

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }

    public IThreadCallback getThreadCallback() {
        return threadCallback;
    }

    public void setThreadCallback(IThreadCallback threadCallback) {
        this.threadCallback = threadCallback;
    }
}
