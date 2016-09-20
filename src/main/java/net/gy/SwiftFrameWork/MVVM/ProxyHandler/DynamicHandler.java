package net.gy.SwiftFrameWork.MVVM.ProxyHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * 动态代理Handler
 * Created by pc on 16/8/29.
 */
public class DynamicHandler implements InvocationHandler {
    private Object handler;
    private final HashMap<String, Method> methodMap = new HashMap<String, Method>(
            1);

    public DynamicHandler(Object handler) {
        this.handler = handler;
    }

    public void addMethod(String name, Method method) {
        methodMap.put(name, method);
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if (handler != null) {
            Method invoker = method;
            String methodName = method.getName();
            method = methodMap.get(methodName);
            if (method != null) {
                return method.invoke(handler,invoker,args);
            }
        }
        return null;
    }
}
