package net.gy.SwiftFrameWork.Reactive.entity;


import net.gy.SwiftFrameWork.Reactive.OnObserver;

/**
 * Created by pc on 16/5/14.
 */
public class ObserverInvoker extends IInvokeDirect<OnObserver>{

    @Override
    public <V> V invoke(String methodname, Object... pars) {
        V res = null;
        switch (methodname){
            case "onFinished":
                proxy.onFinished();
                break;
            case "onSuccess":
                proxy.onSuccess(pars[0]);
                break;
            case "onError":
                proxy.onError((Throwable) pars[0]);
                break;
        }
        return res;
    }
}
