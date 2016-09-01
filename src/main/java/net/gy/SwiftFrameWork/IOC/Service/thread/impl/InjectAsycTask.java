package net.gy.SwiftFrameWork.IOC.Service.thread.impl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import net.gy.SwiftFrameWork.IOC.Core.invoke.DynamicHandler;
import net.gy.SwiftFrameWork.IOC.Service.thread.annotation.AsycTask;
import net.gy.SwiftFrameWork.IOC.Service.thread.annotation.AsycTaskHandler;
import net.gy.SwiftFrameWork.IOC.Service.thread.entity.Asyc;
import net.gy.SwiftFrameWork.IOC.Service.thread.entity.InjectThreadBean;
import net.gy.SwiftFrameWork.IOC.Service.thread.handler.BaseHandler;
import net.gy.SwiftFrameWork.IOC.Service.thread.handler.IHandler;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;


public final class InjectAsycTask {

    private static InjectAsycTask injectAsycTask;

    private Map<Class<?>,Map<String, InjectThreadBean>> injectlist;

    private String asycProxyMethod = "run";

    private Class<?> asycinter = Runnable.class;

    private String handlerProxyMethod = "handlePost";

    private Class<?> handlerinter = IHandler.class;

    public InjectAsycTask() {
        injectlist = new WeakHashMap<Class<?>,Map<String, InjectThreadBean>>();
    }

    public static InjectAsycTask getInstance() {
        synchronized (InjectAsycTask.class) {
            if (injectAsycTask == null)
                injectAsycTask = new InjectAsycTask();
        }
        return injectAsycTask;
    }

    public Map<String, InjectThreadBean> getInjectlist(Object object) {
        return injectlist.get(object.getClass());
    }

    public void Start(Object object,String key) {
        InjectThreadBean bean = injectlist.get(object.getClass()).get(key);
        Thread thread = new Thread(bean.getRunnable());
        bean.setThread(thread);
        thread.start();
    }

    public void Stop(Object object,String key) {
        injectlist.get(object.getClass()).get(key).getThread().stop();
    }

    public void Destory(Object object,String key) {
        injectlist.get(object.getClass()).get(key).getThread().destroy();
    }

    public InjectThreadBean getBean(Object object,String key) {
        return injectlist.get(object.getClass()).get(key);
    }

    public Thread getThread(Object object,String key) {
        return injectlist.get(object.getClass()).get(key).getThread();
    }

    public Runnable getRunnable(Object object,String key) {
        return injectlist.get(object.getClass()).get(key).getRunnable();
    }

    public Handler getHandler(Object object,String key) {
        return injectlist.get(object.getClass()).get(key).getHandlers().get(0);
    }

    public void Post(Object object,String key, int flag) {
        doPost(object,key, flag, null);
    }

    public void Post(Object object,String key, int flag, Serializable data) {
        doPost(object,key, flag, data);
    }

    private void doPost(Object object,String key, int flag, Serializable data) {
        BaseHandler handler = injectlist.get(object.getClass()).get(key).getHandlers().get(0);
        if (data == null) {
            handler.sendEmptyMessage(flag);
        } else {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putSerializable(key, data);
            msg.setData(bundle);
            msg.what = flag;
            handler.sendMessage(msg);
        }
    }

    public void inject(Object object) {
        if (!injectlist.containsKey(object.getClass()))
            injectlist.put(object.getClass(),new HashMap<String, InjectThreadBean>());
        Class<?> template = object.getClass();
        while (template != null && template != Object.class) {
            // 过滤掉基类 因为基类是不包含注解的
            if (template.getName().equals("android.app.Activity") || template.getName().equals("android.support.v4.app.FragmentActivity") || template.getName().equals("android.support.v4.app.Fragment") || template.getName().equals("android.app.Fragment")) {
                break;
            }
            injectMethod(template, object);
            injectMethodByMark(template, object);
            injectHandler(template, object);
            template = template.getSuperclass();
        }
    }

    public void remove(Object object) {
        injectlist.remove(object.getClass());
        System.gc();
    }

    public void remove(Object object,String key) {
        injectlist.get(object.getClass()).remove(key);
    }

    private void injectMethodByMark(Class<?> clazz, Object object) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            boolean istrue = method.getReturnType().equals(Asyc.class);
            if (istrue) {
                doInjectMethod(method, object);
                continue;
            }
            Class<?>[] classes = method.getReturnType().getInterfaces();
            for (Class<?> tar : classes) {
                istrue = tar.equals(Asyc.class);
                if (istrue) {
                    doInjectMethod(method, object);
                    break;
                }
            }
        }
    }

    private void injectMethod(Class<?> clazz, Object object) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            AsycTask methodinject = method.getAnnotation(AsycTask.class);
            if (methodinject != null)
                doInjectMethod(method, object);
        }
    }

    private void doInjectMethod(Method method, Object object) {
        String key = method.getName();
        InjectThreadBean injectThreadBean = new InjectThreadBean();
        DynamicHandler handler = new DynamicHandler(object);
        handler.addMethod(asycProxyMethod, method);
        Object bussnessproxy = Proxy.newProxyInstance(asycinter.getClassLoader(), new Class<?>[]{asycinter}, handler);
        injectThreadBean.setRunnable((Runnable) bussnessproxy);
        if (!injectlist.containsKey(key))
            injectlist.get(object.getClass()).put(key, injectThreadBean);
    }

    private void injectHandler(Class<?> clazz, Object object) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            AsycTaskHandler handlerinject = method.getAnnotation(AsycTaskHandler.class);
            if (handlerinject != null)
                doInjectHandler(method, handlerinject, object, clazz);
        }
    }

    private void doInjectHandler(Method method, AsycTaskHandler handlerinject, Object object, Class<?> clazz) {
        String key = handlerinject.value();
        if (!injectlist.get(object.getClass()).containsKey(key))
            return;
        InjectThreadBean injectThreadBean = injectlist.get(object.getClass()).get(key);
        DynamicHandler handler = new DynamicHandler(object);
        handler.addMethod(handlerProxyMethod, method);
        Object bussnessproxy = Proxy.newProxyInstance(handlerinter.getClassLoader(), new Class<?>[]{handlerinter}, handler);
        BaseHandler baseHandler = new BaseHandler((IHandler) bussnessproxy);
        List<BaseHandler> handlers = injectThreadBean.getHandlers();

        if (handlers == null)
            handlers = new ArrayList<BaseHandler>();

        handlers.add(baseHandler);
        injectThreadBean.setHandlers(handlers);
    }

    public Map<String, InjectThreadBean> get(Class<?> clazz){
        return injectlist.get(clazz);
    }

    private void initThread(String key) {

    }

    public ExtCall with(Object object,String key){
        return new ExtCall(object,key);
    }

    public static ExtCall With(Object object,String key){
        return getInstance().with(object, key);
    }

    public static void Remove(Object object){
        getInstance().remove(object);
    }

    public static void Inject(Object object){
        getInstance().inject(object);
    }

    public class ExtCall{
        Object object;
        String key;

        public ExtCall(Object object, String key) {
            this.object = object;
            this.key = key;
        }
        public void start(){
            getInstance().Start(object,key);
        }
    }

}
