package net.gy.SwiftFrameWork.IOC.Service.event.proxy;

import android.util.Log;

/**
 * Created by gy on 2015/12/21.
 */
public class EventRunnable implements Runnable{

    private IEventProxy proxy;
    private Object arg;

    public EventRunnable(IEventProxy proxy,Object object) {
        this.proxy = proxy;
        arg = object;
    }

    @Override
    public void run() {
        try {
            proxy.onEvent(arg);
        }catch (RuntimeException e){
            Log.e("gy",e.getMessage());
        }
    }
}
