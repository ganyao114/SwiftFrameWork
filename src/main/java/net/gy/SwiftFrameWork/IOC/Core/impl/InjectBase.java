package net.gy.SwiftFrameWork.IOC.Core.impl;

import android.app.Activity;

import net.gy.SwiftFrameWork.IOC.Core.entity.InjectEntity;

import java.util.WeakHashMap;

/**
 * Created by gy on 2015/12/7.
 */
public abstract class InjectBase {


    protected WeakHashMap<Class<?>,InjectEntity> injectMap;

    public InjectBase() {
        injectMap = new WeakHashMap<Class<?>,InjectEntity>();
    }

    /**
     * @Waring 必须调用remove()
     */
    public void inject(Object object){
        if (!injectMap.containsKey(object.getClass()))
            injectMap.put(object.getClass(),new InjectEntity());
        Class<?> template = object.getClass();
        while (template != null && template != Object.class) {
            // 过滤掉基类 因为基类是不包含注解的
            if (template.getName().equals("android.app.Activity") || template.getName().equals("android.support.v4.app.FragmentActivity")
                    || template.getName().equals("android.support.v4.app.Fragment") || template.getName().equals("android.app.Fragment")) {
                break;
            }
            injectCall(template,object);
            template = template.getSuperclass();
        }
    }

    protected abstract void injectCall(Class<?> template, Object object);

    protected Class<?> getKind(Object object){
        Class<?> temp = object.getClass();
        while (!temp.equals(Object.class)){
            if (temp.equals(Activity.class)||temp.equals(android.app.Fragment.class)||temp.equals(android.support.v4.app.Fragment.class))
                break;
            temp = temp.getSuperclass();
        }
        return temp;
    }

    /**
     * @Waring 必须调用
     */
    public void remove(Object object){
        injectMap.remove(object.getClass());
        System.gc();
    }


    public InjectEntity get(Object object){
        return injectMap.get(object.getClass());
    }

}
