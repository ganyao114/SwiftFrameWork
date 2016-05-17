package net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.impl;

import android.app.Activity;

import net.gy.SwiftFrameWork.IOC.UI.view.activitylifeinject.annotation.ActivityLife;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by gy on 2015/11/18.
 */
public class AvtivityLifeINjectTest {

    public static void InjectLife(Activity activity) {
        //List<> injectlist = new ArrayList<>();
        Class<? extends Activity> clazz = activity.getClass();
        Method methods[] = clazz.getMethods();
        for (Method method : methods) {
            Annotation annotations[] = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation
                        .annotationType();
                ActivityLife injectLife = annotationType
                        .getAnnotation(ActivityLife.class);
                if (injectLife != null) {
                    String targetLife = injectLife.value();
                    switch (targetLife) {
                        case "onCreate":

                            break;
                        case "onResume":

                            break;
                        case "omPause":

                            break;
                    }
                }
            }
        }
    }


}
