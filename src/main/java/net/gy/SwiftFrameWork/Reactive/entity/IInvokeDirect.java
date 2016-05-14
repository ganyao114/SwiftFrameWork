package net.gy.SwiftFrameWork.Reactive.entity;

/**
 * Created by pc on 16/5/14.
 */
public abstract class IInvokeDirect<T>{

    protected T proxy;

    public void setProxy(T t){
        proxy = t;
    }
    public abstract Object invoke(String methodname,Object... pars);
}
