package net.gy.SwiftFrameWork.IOC.Core.parase;

import android.app.Application;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import net.gy.SwiftFrameWork.IOC.Core.entity.AnnotationPackage;
import net.gy.SwiftFrameWork.IOC.Core.entity.InjectInvoker;
import net.gy.SwiftFrameWork.IOC.Core.entity.InjectMethods;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.ActivityLife;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onBefore;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onCreate;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onNewIntent;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onPause;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onRestart;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onResume;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onStart;
import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.onStop;
import net.gy.SwiftFrameWork.IOC.UI.view.viewinject.annotation.EventBase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gy on 2015/11/18.
 */
public class AnnotationFactory {

    private static AnnotationFactory factory;

    private Application application;
    private AnnotationPackage annotationPackage;

    public static AnnotationFactory getInstance() {
        synchronized (AnnotationFactory.class) {
            if (factory == null)
                factory = new AnnotationFactory();
        }
        return factory;
    }

    public AnnotationFactory() {
        annotationPackage = AnnotationPackage.getInstance();
    }

    public void getContextAnnotations() {
        Class<?>[] contextClazzs = getAllActivity();
        for (Class<?> contextClazz : contextClazzs) {
            doGetContextAnnotations(contextClazz);
        }
    }

    private Class<?>[] getAllActivity() {
        PackageManager pManager = application.getPackageManager();
        Class<?>[] classes = null;
        try {
            PackageInfo packageInfo = pManager.getPackageInfo(application.getPackageName(), PackageManager.GET_ACTIVITIES);
            ActivityInfo[] activityInfos = packageInfo.activities;
            classes = new Class[activityInfos.length];
            for (int i = 0; i < activityInfos.length; i++) {
                ActivityInfo activityInfo = activityInfos[i];
                try {
                    classes[i] = Class.forName(activityInfo.name);
                } catch (ClassNotFoundException e) {
                    continue;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return classes;
    }

    /*
     *过滤life annotation
     */

    private void doGetContextAnnotations(Class<?> contextClazz) {

        if (AnnotationPackage.getInstance().getActivity_life_inject().get(contextClazz) != null)
            return;

        Class<?> template = contextClazz;
        AnnotationPackage.getInstance().getActivity_life_inject().put(contextClazz, new HashMap<Class<?>, ArrayList<InjectInvoker>>());
        while (template != null && template != Object.class) {
            // 过滤掉基类 因为基类是不包含注解的
            if (template.getName().equals("android.app.Activity") || template.getName().equals("android.support.v4.app.FragmentActivity") || template.getName().equals("android.support.v4.app.Fragment") || template.getName().equals("android.app.Fragment")) {
                break;
            }
            Method[] methods = template.getDeclaredMethods();
            for (int j = 0; j < methods.length; j++) {
                Method method = methods[j];
                doInjectContextMethed(method);
            }
            template = template.getSuperclass();
        }
    }

    private void doInjectContextMethed(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(ActivityLife.class)) {
                dogetActivitylifeInject(method, annotation);
            } else if (annotation.annotationType().equals(EventBase.class)) {

            }
        }
    }

    private void dogetActivitylifeInject(Method method, Annotation annotation) {
        Class clazz = method.getDeclaringClass();
        HashMap<Class<?>, ArrayList<InjectInvoker>> lifeinjectlist = AnnotationPackage.getInstance().getActivity_life_inject().get(clazz);
        Class<? extends Annotation> annotationType = annotation
                .annotationType();
        ActivityLife injectLife = annotationType
                .getAnnotation(ActivityLife.class);
        String lifetype = injectLife.value();
        switch (lifetype) {
            case "onCreate":
                if (!lifeinjectlist.containsKey(onCreate.class))
                    lifeinjectlist.put(onCreate.class, new ArrayList<InjectInvoker>());
                lifeinjectlist.get(onCreate.class).add(new InjectMethods(method, null, null, null));
                break;
            case "onResume":
                if (!lifeinjectlist.containsKey(onResume.class))
                    lifeinjectlist.put(onResume.class, new ArrayList<InjectInvoker>());
                lifeinjectlist.get(onResume.class).add(new InjectMethods(method, null, null, null));
                break;
            case "onPause":
                if (!lifeinjectlist.containsKey(onPause.class))
                    lifeinjectlist.put(onPause.class, new ArrayList<InjectInvoker>());
                lifeinjectlist.get(onPause.class).add(new InjectMethods(method, null, null, null));
                break;
            case "onRestart":
                if (!lifeinjectlist.containsKey(onRestart.class))
                    lifeinjectlist.put(onRestart.class, new ArrayList<InjectInvoker>());
                lifeinjectlist.get(onRestart.class).add(new InjectMethods(method, null, null, null));
                break;
            case "onStart":
                if (!lifeinjectlist.containsKey(onStart.class))
                    lifeinjectlist.put(onStart.class, new ArrayList<InjectInvoker>());
                lifeinjectlist.get(onStart.class).add(new InjectMethods(method, null, null, null));
                break;
            case "onStop":
                if (!lifeinjectlist.containsKey(onStop.class))
                    lifeinjectlist.put(onStop.class, new ArrayList<InjectInvoker>());
                lifeinjectlist.get(onStop.class).add(new InjectMethods(method, null, null, null));
                break;
            case "onNewIntent":
                if (!lifeinjectlist.containsKey(onNewIntent.class))
                    lifeinjectlist.put(onNewIntent.class, new ArrayList<InjectInvoker>());
                lifeinjectlist.get(onNewIntent.class).add(new InjectMethods(method, null, null, null));
                break;
            case "onBefore":
                if (!lifeinjectlist.containsKey(onBefore.class))
                    lifeinjectlist.put(onBefore.class, new ArrayList<InjectInvoker>());
                lifeinjectlist.get(onBefore.class).add(new InjectMethods(method, null, null, null));
                break;

        }

    }


}
