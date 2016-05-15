package net.gy.SwiftFrameWork.Reactive.entity;

import net.gy.SwiftFrameWork.Service.buffer.entity.Observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by pc on 16/5/14.
 */
public class ProxyEntity implements Runnable{

    private Method method;
    private Object object;
    private Object[] args;
    private Object ret;

    public ProxyEntity(Method method, Object object, Object... args) {
        this.method = method;
        this.object = object;
        this.args = args;
    }

    @Override
    public void run() {
        Invoker.invoke_direct(method,object,args);
    }
}
