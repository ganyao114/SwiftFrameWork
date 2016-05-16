package net.gy.SwiftFrameWork.Reactive.entity;

import net.gy.SwiftFrameWork.Reactive.impl.Invoker;

import java.lang.reflect.Method;

/**
 * Created by pc on 16/5/14.
 */
public class ProxyEntity implements Runnable{

    private Method method;
    private Object object;
    private Object[] args;
    private Object ret;
    private int delay = 0;

    public ProxyEntity(Method method, Object object, Object... args) {
        this.method = method;
        this.object = object;
        this.args = args;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        if (delay!=0){
            try {
                Thread.currentThread().sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Invoker.invoke_direct(method,object,args);
    }
}
