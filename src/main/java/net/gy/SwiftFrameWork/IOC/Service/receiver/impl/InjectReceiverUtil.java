package net.gy.SwiftFrameWork.IOC.Service.receiver.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import net.gy.SwiftFrameWork.IOC.Core.impl.IOC;
import net.gy.SwiftFrameWork.IOC.Core.invoke.DynamicHandler;
import net.gy.SwiftFrameWork.IOC.Service.receiver.annotation.RegistReceiver;
import net.gy.SwiftFrameWork.IOC.Service.receiver.entity.BaseBroadCastReceiver;
import net.gy.SwiftFrameWork.IOC.Service.receiver.entity.IReciver;
import net.gy.SwiftFrameWork.IOC.Service.receiver.entity.ReceiverBean;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gy on 2015/12/1.
 */
public final class InjectReceiverUtil {

    private static InjectReceiverUtil injectutil;
    private Map<Class<?>,Map<String, ReceiverBean>> injectlist;
    private Class<?> inter = IReciver.class;
    private final String ProxyMethod = "onReceiver";
    private final String registMethodName = "registerReceiver";
    private final String UnregistMethodName = "unregisterReceiver";

    public static InjectReceiverUtil getInstance(){
        synchronized (InjectReceiverUtil.class) {
            if (injectutil == null)
                injectutil = new InjectReceiverUtil();
        }
        return injectutil;
    }

    private InjectReceiverUtil(){
        injectlist = new HashMap<Class<?>,Map<String, ReceiverBean>>();
    }

    public static void setInjectutil(InjectReceiverUtil injectutil) {
        InjectReceiverUtil.injectutil = injectutil;
    }

    public void regist(Object object){
        if (!injectlist.containsKey(object.getClass()))
            injectlist.put(object.getClass(),new HashMap<String, ReceiverBean>());
        Class<?> template = object.getClass();
        while (template != null && template != Object.class) {
            // 过滤掉基类 因为基类是不包含注解的
            if (template.getName().equals("android.app.Activity") || template.getName().equals("android.support.v4.app.FragmentActivity") || template.getName().equals("android.support.v4.app.Fragment") || template.getName().equals("android.app.Fragment")) {
                break;
            }
            injectMethod(template, object);
            template = template.getSuperclass();
        }
    }

    private void injectMethod(Class<?> template, Object object) {
        Method[] methods = template.getDeclaredMethods();
        for (Method method : methods) {
            RegistReceiver methodinject = method.getAnnotation(RegistReceiver.class);
            if (methodinject != null)
                doInjectMethod(method, object,methodinject);
        }
    }

    private void doInjectMethod(Method method, Object object, RegistReceiver methodinject) {
        String key = method.getName();
        if (injectlist.get(object.getClass()).containsKey(key))
            return;
        String[] strs = methodinject.value();
        IntentFilter filter = new IntentFilter();
        for (String str:strs)
            filter.addAction(str);
        ReceiverBean receiverBean = new ReceiverBean();
        DynamicHandler handler = new DynamicHandler(object);
        handler.addMethod(ProxyMethod, method);
        Object bussnessproxy = Proxy.newProxyInstance(inter.getClassLoader(), new Class<?>[]{inter}, handler);
        BaseBroadCastReceiver receiver = new BaseBroadCastReceiver((IReciver) bussnessproxy);
        receiverBean.setReceiver(receiver);
        doRegist(object,receiver,filter);
        injectlist.get(object.getClass()).put(key,receiverBean);
    }


    public void unregist(Object object,String key){
        ReceiverBean bean = injectlist.get(object.getClass()).remove(key);
        if (bean == null)
            return;
        doUnRegist(object,bean.getReceiver());
    }

    public void unregist(Object object){
        Map<String, ReceiverBean> map = injectlist.remove(object.getClass());
        if (map == null)
            return;
        for (Map.Entry<String, ReceiverBean> entry:map.entrySet()){
            doUnRegist(object,entry.getValue().getReceiver());
        }
        System.gc();
    }


    public BroadcastReceiver getReceiver(Object object,String key){
        return injectlist.get(object.getClass()).get(key).getReceiver();
    }

    public BroadcastReceiver removeReceiver(Object object,String key){
        return injectlist.get(object.getClass()).remove(key).getReceiver();
    }


    private void doRegist(Object object,BroadcastReceiver receiver, IntentFilter filter){
        Method method = null;
        try {
            if (object instanceof Context) {
                method = Context.class.getDeclaredMethod(registMethodName,BroadcastReceiver.class, IntentFilter.class);
                method.invoke(object, receiver, filter);
            }
            else
                IOC.getInstance().getApplication().getApplicationContext().registerReceiver(receiver,filter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doUnRegist(Object object,BroadcastReceiver receiver){
        Method method = null;
        try {
            if (object instanceof Context) {
                method = Context.class.getDeclaredMethod(UnregistMethodName,BroadcastReceiver.class);
                method.invoke(object, receiver);
            }
            else
                IOC.getInstance().getApplication().getApplicationContext().unregisterReceiver(receiver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void Regist(Object object){
        getInstance().regist(object);
    }

    public static void Unregist(Object object){
        getInstance().unregist(object);
    }

}
