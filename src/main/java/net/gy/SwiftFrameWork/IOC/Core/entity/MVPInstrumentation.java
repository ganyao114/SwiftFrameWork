package net.gy.SwiftFrameWork.IOC.Core.entity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;

import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onBefore;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onDestory;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onNewIntent;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onRestart;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onResume;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onStart;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onStop;
import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.impl.ViewInjectUtilsImpl;
import net.gy.SwiftFrameWork.MVP.View.InsProxy.IInsProxy;
import net.gy.SwiftFrameWork.config.configs;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Created by gy on 2015/11/18.
 */
public class MVPInstrumentation extends Instrumentation {

    private IInsProxy proxy;

    public MVPInstrumentation(IInsProxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);

    }

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        inject(activity, onBefore.class, null);
        proxy.injctPresent(activity);
        if (configs.isIOC) {
            ViewInjectUtilsImpl.getInstance().inject(activity);
        }
        super.callActivityOnCreate(activity,icicle);

    }

    @Override
    public void callActivityOnDestroy(Activity activity) {
        inject(activity,onDestory.class,null);
        super.callActivityOnDestroy(activity);
    }

    @Override
    public void callActivityOnNewIntent(Activity activity, Intent intent) {
        inject(activity, onNewIntent.class, intent);
        super.callActivityOnNewIntent(activity, intent);
    }

    @Override
    public void callActivityOnStart(Activity activity) {
        proxy.setContext(activity);
        inject(activity, onStart.class, null);
        super.callActivityOnStart(activity);

    }

    @Override
    public void callActivityOnRestart(Activity activity) {
        inject(activity,onRestart.class,null);
        super.callActivityOnRestart(activity);
    }

    @Override
    public void callActivityOnResume(Activity activity) {
        inject(activity, onResume.class, null);
        super.callActivityOnResume(activity);
    }

    @Override
    public void callActivityOnStop(Activity activity) {
        inject(activity, onStop.class, null);
        super.callActivityOnStop(activity);
    }

    private void inject(Activity activity, Class<?> clazz, Intent intent) {
        ArrayList<InjectInvoker> jArrayList = AnnotationPackage.getInstance().getContextInvokers(activity.getClass(), clazz);
        if (jArrayList == null) {
            return;
        }
        try {
            for (InjectInvoker injectInvoker : jArrayList) {
                if (intent != null) {
                    injectInvoker.invoke(activity, intent);
                } else {
                    injectInvoker.invoke(activity);
                }
            }
        } catch (Exception e) {
            StringWriter buf = new StringWriter();
            PrintWriter w = new PrintWriter(buf);
            e.printStackTrace(w);
        }
    }

}
