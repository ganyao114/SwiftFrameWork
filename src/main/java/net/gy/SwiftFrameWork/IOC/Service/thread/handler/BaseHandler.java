package net.gy.SwiftFrameWork.IOC.Service.thread.handler;

import android.os.Handler;
import android.os.Message;

/**
 * Created by gy on 2015/11/27.
 */
public class BaseHandler extends Handler {

    private IHandler handlerProxy;

    public BaseHandler(IHandler handlerProxy) {
        this.handlerProxy = handlerProxy;
    }

    @Override
    public void handleMessage(Message msg) {
        handlerProxy.handlePost(msg);
    }
}
