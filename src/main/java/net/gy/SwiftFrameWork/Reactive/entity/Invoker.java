package net.gy.SwiftFrameWork.Reactive.entity;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import net.gy.SwiftFrameWork.IOC.Core.invoke.DynamicHandler;
import net.gy.SwiftFrameWork.IOC.Service.event.proxy.IEventProxy;
import net.gy.SwiftFrameWork.Reactive.OnObserver;
import net.gy.SwiftFrameWork.Reactive.OnPublisher;
import net.gy.SwiftFrameWork.Reactive.annotation.RunContext;
import net.gy.SwiftFrameWork.Service.thread.pool.impl.MySigleThreadQueue;
import net.gy.SwiftFrameWork.Service.thread.pool.impl.MyWorkThreadQueue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by pc on 16/5/14.
 */
public class Invoker {

    private static Invoker invoker;
    private Handler mainHandler;

    public static Invoker getInstance(){
        synchronized (Invoker.class){
            if (invoker == null)
                invoker = new Invoker();
        }
        return invoker;
    }

    public Invoker() {
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public void invoke(Method method, Object object, Object... values){
        RunContext runContext = method.getAnnotation(RunContext.class);
        RunContextType type;
        if (runContext == null)
            type = RunContextType.CurrentThread;
        else
            type = runContext.value();
        switch (type){
            case CurrentThread:
                invoke_current_thread(method,object,values);
                break;
            case CurrentLoop:
                invoke_current_loop(method,object,values);
                break;
            case NewThread:
                invoke_new_thread(method,object,values);
                break;
            case MainThread:
                invoke_main_thread(method,object,values);
                break;
            case MainLoop:
                invoke_main_loop(method, object, values);
                break;
            case Calculate:
                invoke_calculate(method, object, values);
                break;
            case IO:
                invoke_io(method, object, values);
                break;
            case NewHandlerThread:
                invoke_new_handler_thread(method, object, values);
        }
    }

    private void invoke_new_handler_thread(Method method, Object object, Object... values) {
        ProxyEntity entity = new ProxyEntity(method,object,values);
        HandlerThread thread = new HandlerThread(object.getClass().getName()+method.getName());
        thread.start();
        Handler handler = new Handler(thread.getLooper());
        handler.post(entity);
    }

    private void invoke_io(Method method, Object object, Object[] values) {
        ProxyEntity entity = new ProxyEntity(method,object,values);
        MySigleThreadQueue.AddTask(entity);
    }

    private void invoke_calculate(Method method, Object object, Object... values) {
        ProxyEntity entity = new ProxyEntity(method,object,values);
        MyWorkThreadQueue.AddTask(entity);
    }

    private void invoke_new_thread(Method method, Object object, Object... values) {
        ProxyEntity entity = new ProxyEntity(method,object,values);
        new Thread(entity).start();
    }

    private void invoke_main_thread(Method method, Object object, Object... values) {
        Looper mianLoop = Looper.getMainLooper();
        ProxyEntity entity = new ProxyEntity(method,object,values);
        if (Looper.myLooper() != Looper.getMainLooper()){
            invoke_main_loop(method, object, values);
        }else {
            invoke_direct(method, object, values);
        }
    }

    private void invoke_main_loop(Method method, Object object, Object... values) {
        ProxyEntity entity = new ProxyEntity(method,object,values);
        mainHandler.post(entity);
    }

    private void invoke_current_loop(Method method, Object object, Object... values) {
        Looper looper = Looper.myLooper();
        if (looper == null)
            throw new RuntimeException(Thread.currentThread().getName()+"不是HandlerThread");
        Handler handler = new Handler(looper);
        ProxyEntity entity = new ProxyEntity(method,object,values);
        handler.post(entity);
    }

    private <T> T invoke_current_thread(Method method, Object object, Object... values) {
        return invoke_direct(method, object, values);
    }

    public static <T> T invoke_direct(Method method, Object object, Object... values){
        String name = method.getName();
        IInvokeDirect invoke = null;
        if (object instanceof OnObserver){
            invoke = new ObserverInvoker();
            invoke.setProxy(object);
        }else if (object instanceof OnPublisher){
            invoke = new PublisherInvoker();
            invoke.setProxy(object);
        }
        if (invoke == null)
            return null;

        Object ret = invoke.<T>invoke(name,values);
        if (ret == null)
            return null;
        return (T) ret;
    }

}
