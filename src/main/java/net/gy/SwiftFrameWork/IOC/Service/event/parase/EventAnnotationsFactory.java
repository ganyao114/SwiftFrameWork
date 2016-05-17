package net.gy.SwiftFrameWork.IOC.Service.event.parase;

import net.gy.SwiftFrameWork.IOC.Service.event.annotation.InjectEvent;
import net.gy.SwiftFrameWork.IOC.Service.event.entity.EventPackage;
import net.gy.SwiftFrameWork.IOC.Service.event.entity.EventThreadType;
import net.gy.SwiftFrameWork.IOC.Service.event.impl.EventPoster;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by gy on 2015/11/24.
 */
public class EventAnnotationsFactory {

    private static EventAnnotationsFactory factory;

     public static EventAnnotationsFactory getInstance(){
         synchronized (EventAnnotationsFactory.class) {
             if (factory == null)
                 factory = new EventAnnotationsFactory();
         }
        return factory;
    }

    public void getEventAnnotations(Object object) {
        Class<?> clazz = object.getClass();
        Class<?> template = clazz;
        Method[] methods = clazz.getDeclaredMethods();
        while (template != null && template != Object.class) {
            // 过滤掉基类 因为基类是不包含注解的
            if (template.getName().equals("android.app.Activity") || template.getName().equals("android.support.v4.app.FragmentActivity")
                    || template.getName().equals("android.support.v4.app.Fragment") || template.getName().equals("android.app.Fragment"))
                break;
            for (Method method : methods) {
                Annotation annotations[] = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType().equals(InjectEvent.class))
                        doPutAnnotation(clazz, method, annotation,object);
                }
            }
            template = template.getSuperclass();
        }

    }

    private void doPutAnnotation(Class clazz, Method method, Annotation annotation, Object object) {
        InjectEvent eventAnnotation = method.getAnnotation(InjectEvent.class);
        EventThreadType threadType = eventAnnotation.type();
        if (threadType == null)
            return;
        EventPackage eventPackage = new EventPackage();
        eventPackage.setMode(threadType);
        eventPackage.setMethod(method);
        eventPackage.setType(method.getParameterTypes()[0]);
        eventPackage.setRegister(new WeakReference<Object>(object));
        insertEntity(eventPackage,clazz);
    }

    private void insertEntity(EventPackage eventPackage, Class clazz) {
        HashMap<Class<?>,HashMap<String,EventPackage>> posts =  EventPoster.getInstance().getPostEvents();
        WeakHashMap<Class<?>,List<WeakReference<EventPackage>>> broads = EventPoster.getInstance().getBroadCastEvents();
        if (!posts.containsKey(clazz))
            posts.put(clazz,new HashMap<String, EventPackage>());
        posts.get(clazz).put(eventPackage.getMethod().getName(),eventPackage);
        if (!broads.containsKey(eventPackage.getType()))
            broads.put(eventPackage.getType(),new ArrayList<WeakReference<EventPackage>>());
        broads.get(eventPackage.getType()).add(new WeakReference<EventPackage>(eventPackage));
    }

}
