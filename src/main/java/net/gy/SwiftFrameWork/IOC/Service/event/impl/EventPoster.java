package net.gy.SwiftFrameWork.IOC.Service.event.impl;

import android.os.Handler;
import android.os.Looper;

import net.gy.SwiftFrameWork.IOC.Core.invoke.DynamicHandler;
import net.gy.SwiftFrameWork.IOC.Service.event.entity.EventPackage;
import net.gy.SwiftFrameWork.IOC.Service.event.parase.EventAnnotationsFactory;
import net.gy.SwiftFrameWork.IOC.Service.event.proxy.EventRunnable;
import net.gy.SwiftFrameWork.IOC.Service.event.proxy.IEventProxy;
import net.gy.SwiftFrameWork.Service.thread.pool.impl.MyWorkThreadQueue;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by gy on 2015/11/24.
 */
public final class EventPoster {

    private static EventPoster eventPoster;

    private static final Map<Class<?>, List<Class<?>>> eventTypesCache = new WeakHashMap<Class<?>, List<Class<?>>>();
    private static WeakHashMap<Class<?>,List<WeakReference<EventPackage>>> broadCastEvents;
    private static HashMap<Class<?>,HashMap<String,EventPackage>> postEvents;
    private String callMethod = "onEvent";
    private Class<?> callInter = IEventProxy.class;
    private Handler mainHandler;

    public static EventPoster getInstance(){
        synchronized (EventPoster.class) {
            if (eventPoster == null)
                eventPoster = new EventPoster();
        }
        return eventPoster;
    }

    private EventPoster() {
        broadCastEvents = new WeakHashMap<Class<?>,List<WeakReference<EventPackage>>>();
        postEvents = new HashMap<Class<?>,HashMap<String,EventPackage>>();
    }

    public void regist(Object object){
        EventAnnotationsFactory.getInstance().getEventAnnotations(object);
    }

    public void unregist(Object object){
        postEvents.remove(object.getClass());
        System.gc();
    }

    public void post(String key,Object object){
        _post(key,object,0);
    }

    private void _post(String key,Object object,long time){
        for (Map.Entry<Class<?>,HashMap<String,EventPackage>> entry:postEvents.entrySet()){
            Map<String,EventPackage> tmp = entry.getValue();
            if (tmp.containsKey(key))
                doPost(tmp.get(key),object,time);
        }
    }

    public void broadcast(Object object){
        _brodcast(object,0);
    }

    private void _brodcast(Object object,long time){
        List<WeakReference<EventPackage>> list = broadCastEvents.get(object.getClass());
        if (list == null||list.size() == 0)
            return;
        for (WeakReference<EventPackage> broadRef:list){
            EventPackage event = broadRef.get();
            if (event == null)
                continue;
            if (event.getRegister().get() == null){
                list.remove(broadRef);
                continue;
            }
            doPost(event,object, time);
        }
    }

    public void broadcastDly(Object object,long time){

    }

    public void postDly(Object object,String name,long time){

    }

    private void doPost(EventPackage event, Object object, long time) {
        switch (event.getMode()){
            case MainThread:
                doPostMainThread(event,object,time);
                break;
            case AsyncTask:
                doPostAsyc(event,object,time);
                break;
            case kidsThread:
                doPostKidsThread(event,object,time);
                break;
            case PostThread:
                break;
            case MainLoop:
                doPostMainLoop(event,object,time);
                break;
        }
    }

    private void doPostMainLoop(EventPackage event, Object object, long time) {
        Object register = event.getRegister().get();
        DynamicHandler handler = new DynamicHandler(event.getRegister().get());
        handler.addMethod(callMethod, event.getMethod());
        Object bussnessproxy = Proxy.newProxyInstance(callInter.getClassLoader(), new Class<?>[]{callInter}, handler);
        if (mainHandler == null)
            mainHandler = new Handler(Looper.getMainLooper());
        if (register == null)
            return;
        if (time == 0)
            mainHandler.post(new EventRunnable((IEventProxy) bussnessproxy,object));
        else
            mainHandler.postDelayed(new EventRunnable((IEventProxy) bussnessproxy,object),time);
    }

    private void doPostKidsThread(EventPackage event, Object object, long time) {
        boolean isMainThread = Looper.getMainLooper() == Looper.myLooper();
        if (isMainThread){
            doPostAsyc(event,object, time);
        }else {
            Object register = event.getRegister().get();
            if (register == null)
                return;
                try {
                    event.getMethod().invoke(register,object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
        }
    }

    private void doPostAsyc(EventPackage event, Object object, long time) {
        Object register = event.getRegister().get();
        if (register == null)
            return;
        DynamicHandler handler = new DynamicHandler(event.getRegister().get());
        handler.addMethod(callMethod, event.getMethod());
        Object bussnessproxy = Proxy.newProxyInstance(callInter.getClassLoader(), new Class<?>[]{callInter}, handler);
        if (register == null)
            return;
        MyWorkThreadQueue.AddTask(new EventRunnable((IEventProxy) bussnessproxy,object));
    }

    private void doPostMainThread(EventPackage event, Object object, long time) {
        boolean isMainThread = Looper.getMainLooper() == Looper.myLooper();
        Object register = event.getRegister().get();
        if (register == null)
            return;
        if (isMainThread){
            try {
                event.getMethod().invoke(register,object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return;
        }
        DynamicHandler handler = new DynamicHandler(event.getRegister().get());
        handler.addMethod(callMethod, event.getMethod());
        Object bussnessproxy = Proxy.newProxyInstance(callInter.getClassLoader(), new Class<?>[]{callInter}, handler);
        if (mainHandler == null)
            mainHandler = new Handler(Looper.getMainLooper());
        if (register == null)
            return;
        if (time == 0)
            mainHandler.post(new EventRunnable((IEventProxy) bussnessproxy,object));
        else
            mainHandler.postDelayed(new EventRunnable((IEventProxy) bussnessproxy,object),time);
    }

    public WeakHashMap<Class<?>, List<WeakReference<EventPackage>>> getBroadCastEvents() {
        return broadCastEvents;
    }


    public HashMap<Class<?>, HashMap<String, EventPackage>> getPostEvents() {
        return postEvents;
    }

    public static void Post(String key,Object arg){
        getInstance().post(key,arg);
    }

    public static void Broadcast(Object object){
        getInstance().broadcast(object);
    }

    //解决Presenter注入时机冲突
    public static void BroadInMainloop(final Object object){
        if (getInstance().mainHandler == null)
            getInstance().mainHandler = new Handler(Looper.getMainLooper());
        getInstance().mainHandler.post(new Runnable() {
            @Override
            public void run() {
                getInstance().broadcast(object);
            }
        });
    }

    //解决Presenter注入时机冲突
    public static void PostInMainloop(final String key,final Object object){
        if (getInstance().mainHandler == null)
            getInstance().mainHandler = new Handler(Looper.getMainLooper());
        getInstance().mainHandler.post(new Runnable() {
            @Override
            public void run() {
                getInstance().post(key,object);
            }
        });
    }

    public static void Regist(Object object){
        getInstance().regist(object);
    }

    public static void Unregist(Object object){
        getInstance().unregist(object);
    }

}
