package net.gy.SwiftFrameWork.Presenter;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/9/28.
 */
public class ActivityLifeManager implements Application.ActivityLifecycleCallbacks{

    private Map<Class<? extends Activity>,ActivityBinder> activityBinderMap = new ConcurrentHashMap<>();
    private Map<Class,Vector> invokers = new ConcurrentHashMap<>();

    public void addInvoker(Object object){
        Class type = object.getClass();
        Vector<LifeInvokerEntity> entities = ActivityLifeCache.getEntity(type);
        if (entities == null)
            return;
        Vector iks = invokers.get(type);
        synchronized (invokers) {
            if (iks == null) {
                iks = new Vector();
                invokers.put(type,iks);
            }
            if (iks.contains(object))
                return;
            iks.add(object);
            dispathEntities(entities);
        }
    }

    public void removeInvoker(Object object){
        Class type = object.getClass();
        Vector iks = invokers.get(type);
        if (iks == null)
            return;
        synchronized (invokers) {
            iks.remove(object);
            if (iks.size() == 0) {
                invokers.remove(iks);
                Vector<LifeInvokerEntity> entities = ActivityLifeCache.getEntity(type);
                if (entities == null)
                    return;
                removeEntities(entities);
            }
        }
    }

    private void removeEntities(Vector<LifeInvokerEntity> entities) {
        for (LifeInvokerEntity entity:entities){
            Class<? extends Activity> tarAcType = entity.getTarType();
            ActivityLifeType lifeType = entity.getType();
            ActivityBinder binder = activityBinderMap.get(tarAcType);
            if (binder == null)
                continue;
            Vector<LifeInvokerEntity> lifeInvokerEntities = binder.getLifes()[lifeType.value()];
            if (lifeInvokerEntities == null)
                continue;
            lifeInvokerEntities.remove(entity);
        }
    }

    private void dispathEntities(Vector<LifeInvokerEntity> entities) {
        for (LifeInvokerEntity entity:entities){
            Class<? extends Activity> tarAcType = entity.getTarType();
            ActivityLifeType lifeType = entity.getType();
            ActivityBinder binder = activityBinderMap.get(tarAcType);
            if (binder == null){
                binder = new ActivityBinder();
                activityBinderMap.put(tarAcType,binder);
            }
            Vector<LifeInvokerEntity> lifeInvokerEntities = binder.getLifes()[lifeType.value()];
            if (lifeInvokerEntities == null){
                lifeInvokerEntities = new Vector<>();
                binder.getLifes()[lifeType.value()] = lifeInvokerEntities;
            }
            if (!lifeInvokerEntities.contains(entity))
                lifeInvokerEntities.add(entity);
        }
    }

    private void dispatchEvent(Class acType,ActivityLifeType type,Object... pars) {
        ActivityBinder binder = activityBinderMap.get(acType);
        if (binder == null)
            return;
        Vector<LifeInvokerEntity> list = binder.getLifes()[type.value()];
        if (list == null)
            return;
        for (LifeInvokerEntity entity:list){
            Vector iks = invokers.get(entity.getInvokerType());
            if (iks == null||iks.size() == 0)
                continue;
            for (Object invoker:iks){
                entity.invoke(invoker,pars);
            }
        }
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnCreate,activity,savedInstanceState);
    }



    @Override
    public void onActivityStarted(Activity activity) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnStart,activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnResume,activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnPause,activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnStop,activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnSave,activity,outState);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        dispatchEvent(activity.getClass(),ActivityLifeType.OnDestory,activity);
    }
}
