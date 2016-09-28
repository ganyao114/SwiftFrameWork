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

    }

    private void dispathEntities(Vector<LifeInvokerEntity> entities) {
        for (LifeInvokerEntity entity:entities){

        }
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
