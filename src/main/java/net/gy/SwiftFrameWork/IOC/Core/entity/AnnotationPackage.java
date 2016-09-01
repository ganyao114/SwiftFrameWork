package net.gy.SwiftFrameWork.IOC.Core.entity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gy on 2015/11/18.
 */
public class AnnotationPackage {

    private static AnnotationPackage annotationPackage;

    private static Map<Class<?>, HashMap<Class<?>, ArrayList<InjectInvoker>>> activity_life_inject;

    public static AnnotationPackage getInstance() {
        synchronized (AnnotationPackage.class) {
            if (annotationPackage == null)
                annotationPackage = new AnnotationPackage();
        }
        return annotationPackage;
    }

    public Map<Class<?>, HashMap<Class<?>, ArrayList<InjectInvoker>>> getActivity_life_inject() {
        return activity_life_inject;
    }

    public void setActivity_life_inject(Map<Class<?>, HashMap<Class<?>, ArrayList<InjectInvoker>>> activity_life_inject) {
        AnnotationPackage.activity_life_inject = activity_life_inject;
    }

    public ArrayList<InjectInvoker> getContextInvokers(Class<? extends Activity> contextType, Class<?> method) {
        return activity_life_inject.get(contextType).get(method);
    }
}
