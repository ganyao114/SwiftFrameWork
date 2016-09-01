package net.gy.SwiftFrameWork.Service.thread.templet;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import net.gy.SwiftFrameWork.Model.net.http.IHttpDealCallBack;
import net.gy.SwiftFrameWork.Model.net.http.IHttpService;
import net.gy.SwiftFrameWork.Model.net.http.impl.MyHttpService;
import net.gy.SwiftFrameWork.Exception.model.net.http.HttpServiceException;
import net.gy.SwiftFrameWork.Service.thread.IThreadCallback;
import net.gy.SwiftFrameWork.Service.thread.templet.configs.HttpTheadConfigBean;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.SocketTimeoutException;

@SuppressWarnings("deprecation")
public abstract class HttpThreadTemplet implements Runnable, IHttpDealCallBack {

    protected Handler handler;
    protected IHttpService httpService;
    private boolean isLoop = false;
    private int tickTime = 0;
    private HttpTheadConfigBean configBean;
    private IThreadCallback callback;

    public HttpThreadTemplet(Handler handler, IHttpDealCallBack dealCallBack) {
        // TODO Auto-generated constructor stub
        this.handler = handler;
        httpService = new MyHttpService(dealCallBack);
        configBean = SetConfig();
        isLoop = configBean.isLoop;
        tickTime = configBean.tickTime;
    }

    public HttpThreadTemplet(Handler handler) {
        // TODO Auto-generated constructor stub
        this.handler = handler;
        httpService = new MyHttpService(this);
        configBean = SetConfig();
        isLoop = configBean.isLoop;
        tickTime = configBean.tickTime;
    }

    public HttpThreadTemplet(Handler handler,MyHttpService service) {
        // TODO Auto-generated constructor stub
        this.handler = handler;
        httpService = service;
        configBean = SetConfig();
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
        if (callback != null)
            callback.OnStop();
    }

    private void doRun() {
        try {
            OnRun();
        } catch (HttpServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putSerializable("ErroMsg", e.getMessage());
            msg.setData(data);
            handler.sendMessage(msg);
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putSerializable("ErroMsg",
                    configBean.ErrorMsgLevel1);
            msg.setData(data);
            handler.sendMessage(msg);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putSerializable("ErroMsg",
                    configBean.ErrorMsgLevel2);
            msg.setData(data);
            handler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putSerializable("ErroMsg", configBean.ErrorMsgLevel3);
            msg.setData(data);
            handler.sendMessage(msg);
        }finally {
            onFinally();
        }
    }

    public void setCallback(IThreadCallback callback) {
        this.callback = callback;
    }

    public synchronized void StopThread() {
        isLoop = false;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    protected abstract void OnRun() throws Exception;

    protected abstract HttpTheadConfigBean SetConfig();

    protected void onFinally(){

    }

}
