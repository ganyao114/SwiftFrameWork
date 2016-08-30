package net.gy.SwiftFrameWork.MVVM.templet;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import net.gy.SwiftFrameWork.Exception.model.net.http.HttpServiceException;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBack;
import net.gy.SwiftFrameWork.MVVM.templet.configs.HttpTheadConfigBean;
import net.gy.SwiftFrameWork.Model.net.http.IHttpDealCallBack;
import net.gy.SwiftFrameWork.Model.net.http.IHttpService;
import net.gy.SwiftFrameWork.Model.net.http.impl.MyHttpService;
import net.gy.SwiftFrameWork.Service.thread.IThreadCallback;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.SocketTimeoutException;

@SuppressWarnings("deprecation")
public abstract class HttpThreadTemplet implements Runnable {

    protected boolean isLoop = false;
    protected int tickTime = 0;
    protected HttpTheadConfigBean configBean;
    protected IThreadCallback threadCallback;
    protected ICallBack callBack;

    public HttpThreadTemplet(ICallBack callBack) {
        // TODO Auto-generated constructor stub
        this.callBack = callBack;
        this.configBean = HttpTheadConfigBean.Default();
        isLoop = configBean.isLoop;
        tickTime = configBean.tickTime;
    }



    public HttpThreadTemplet(ICallBack callBack,HttpTheadConfigBean configBean) {
        // TODO Auto-generated constructor stub
        this.callBack = callBack;
        this.configBean = configBean;
        isLoop = configBean.isLoop;
        tickTime = configBean.tickTime;
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
            // TODO Auto-generated catch block
            e.printStackTrace();
            callBack.onFailed(e);
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            callBack.onFailed(e);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            callBack.onFailed(e);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onFailed(e);
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

    public ICallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(ICallBack callBack) {
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
