package net.gy.SwiftFrameWork.Reactive.impl;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import net.gy.SwiftFrameWork.Reactive.OnObserver;
import net.gy.SwiftFrameWork.Reactive.OnPublisher;
import net.gy.SwiftFrameWork.Reactive.annotation.Delay;
import net.gy.SwiftFrameWork.Reactive.annotation.RunContext;
import net.gy.SwiftFrameWork.Reactive.entity.Function1Invoker;
import net.gy.SwiftFrameWork.Reactive.entity.IInvokeDirect;
import net.gy.SwiftFrameWork.Reactive.entity.ObserverInvoker;
import net.gy.SwiftFrameWork.Reactive.entity.ProxyEntity;
import net.gy.SwiftFrameWork.Reactive.entity.PublisherInvoker;
import net.gy.SwiftFrameWork.Reactive.entity.RunContextType;
import net.gy.SwiftFrameWork.Reactive.entity.actions.Func1;
import net.gy.SwiftFrameWork.Service.thread.pool.impl.MySigleThreadQueue;
import net.gy.SwiftFrameWork.Service.thread.pool.impl.MyWorkThreadQueue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Future;

/**
 * Created by pc on 16/5/14.
 */
public class Invoker {

    private static Invoker invoker;
    private Handler mainHandler;

    public final static String DynamicFlagname = "runcontext";
    public final static String DynamicFlagDelay = "delay";

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

    public Future invoke(Method method, Object object, Object... values){
        RunContext runContext = method.getAnnotation(RunContext.class);
        RunContextType type;
        int delay = 0;
        if (runContext == null)
            type = RunContextType.CurrentThread;
        else
            type = runContext.value();
        Delay delayAnno = method.getAnnotation(Delay.class);
        if (delayAnno!=null)
            delay = delayAnno.value();
        Future future = null;
        switch (type){
            case CurrentThread:
                invoke_current_thread(method,object,delay,values);
                break;
            case CurrentLoop:
                invoke_current_loop(method,object,delay,values);
                break;
            case NewThread:
                invoke_new_thread(method,object,delay,values);
                break;
            case MainThread:
                invoke_main_thread(method,object,delay,values);
                break;
            case MainLoop:
                invoke_main_loop(method,object,delay,values);
                break;
            case Calculate:
                future = invoke_calculate(method,object,delay,values);
                break;
            case IO:
                invoke_io(method,object,delay,values);
                break;
            case NewHandlerThread:
                invoke_new_handler_thread(method,object,delay,values);
                break;
            case Dynamic:
                invoke_dynamic(method, object, values);
                break;
        }
        return future;
    }

    private Future invoke_dynamic(Method method, Object object, Object... values) {
        RunContextType type = null;
        int delay = 0;
        try {
            Field field = object.getClass().getField(DynamicFlagname);
            type = (RunContextType) field.get(object);
            Field field1 = object.getClass().getField(DynamicFlagDelay);
            delay = field1.getInt(object);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (type == null)
            return null;
        Future future = null;
        switch (type){
            case CurrentThread:
                invoke_current_thread(method,object,delay,values);
                break;
            case CurrentLoop:
                invoke_current_loop(method,object,delay,values);
                break;
            case NewThread:
                invoke_new_thread(method,object,delay,values);
                break;
            case MainThread:
                invoke_main_thread(method,object,delay,values);
                break;
            case MainLoop:
                invoke_main_loop(method,object,delay,values);
                break;
            case Calculate:
                invoke_calculate(method,object,delay,values);
                break;
            case IO:
                invoke_io(method,object,delay,values);
                break;
            case NewHandlerThread:
                invoke_new_handler_thread(method,object,delay,values);
                break;
            case Dynamic:
                invoke_dynamic(method, object, values);
                break;
        }
        return future;
    }

    private void invoke_new_handler_thread(Method method, Object object,int delay,Object... values) {
        ProxyEntity entity = new ProxyEntity(method,object,values);
        HandlerThread thread = new HandlerThread(object.getClass().getName()+method.getName());
        thread.start();
        Handler handler = new Handler(thread.getLooper());
        if (delay == 0)
            handler.post(entity);
        else
            handler.postDelayed(entity,delay);
    }

    private void invoke_io(Method method, Object object,int delay,Object... values) {
        ProxyEntity entity = new ProxyEntity(method,object,values);
        entity.setDelay(delay);
        MySigleThreadQueue.AddTask(entity);
    }

    private Future invoke_calculate(Method method, Object object,int delay,Object... values) {
        ProxyEntity entity = new ProxyEntity(method,object,values);
        entity.setDelay(delay);
        return MyWorkThreadQueue.AddTask(entity);
    }

    private void invoke_new_thread(Method method, Object object,int delay,Object... values) {
        ProxyEntity entity = new ProxyEntity(method,object,values);
        entity.setDelay(delay);
        new Thread(entity).start();
    }

    private void invoke_main_thread(Method method, Object object,int delay,Object... values) {
        Looper mianLoop = Looper.getMainLooper();
        ProxyEntity entity = new ProxyEntity(method,object,values);
        if (Looper.myLooper() != Looper.getMainLooper()){
            invoke_main_loop(method, object,delay,values);
        }else {
            invoke_current_thread(method, object, delay, values);
        }
    }

    private void invoke_main_loop(Method method, Object object,int delay,Object... values) {
        ProxyEntity entity = new ProxyEntity(method,object,values);
        if (delay == 0)
            mainHandler.post(entity);
        else
            mainHandler.postDelayed(entity,delay);
    }

    private void invoke_current_loop(Method method, Object object,int delay, Object... values) {
        Looper looper = Looper.myLooper();
        if (looper == null)
            throw new RuntimeException(Thread.currentThread().getName()+"不是HandlerThread");
        Handler handler = new Handler(looper);
        ProxyEntity entity = new ProxyEntity(method,object,values);
        if (delay == 0)
            handler.post(entity);
        else
            handler.postDelayed(entity,delay);
    }

    private <T> T invoke_current_thread(Method method, Object object,int delay,Object... values) {
        if (delay!=0) {
            try {
                Thread.currentThread().sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        }else if (object instanceof Func1){
            invoke = new Function1Invoker();
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
