package net.gy.SwiftFrameWork.IOC.Core.helper;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.List;
import java.util.Map;

/**
 * Created by gy939 on 2016/9/25.
 */
public class IocInjectHealper implements Application.ActivityLifecycleCallbacks{

    private List<InjectHandler> handlers;
    private Map<Class<? extends Activity>,Class<? extends Activity>> classes;

    public List<InjectHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<InjectHandler> handlers) {
        this.handlers = handlers;
    }

    public Map<Class<? extends Activity>, Class<? extends Activity>> getClasses() {
        return classes;
    }

    public void setClasses(Map<Class<? extends Activity>, Class<? extends Activity>> classes) {
        this.classes = classes;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (!classes.containsKey(activity.getClass()))
            return;
        for (InjectHandler handler:handlers)
            handler.inject(activity);
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
        if (!classes.containsKey(activity.getClass()))
            return;
        for (InjectHandler handler:handlers)
            handler.remove(activity);
    }
}
