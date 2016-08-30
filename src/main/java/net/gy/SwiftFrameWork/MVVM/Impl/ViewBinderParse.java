package net.gy.SwiftFrameWork.MVVM.Impl;

import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import net.gy.SwiftFrameWork.MVVM.Annotations.BindObj;
import net.gy.SwiftFrameWork.MVVM.Annotations.BindViewBase;
import net.gy.SwiftFrameWork.MVVM.Entity.ViewBindControl;
import net.gy.SwiftFrameWork.MVVM.Entity.ViewBindEntity;
import net.gy.SwiftFrameWork.MVVM.Entity.ViewBinderLevel;
import net.gy.SwiftFrameWork.MVVM.Entity.ViewType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by pc on 16/8/30.
 */
public class ViewBinderParse {

    public static ViewBindControl establish(Class clazz){
        ViewBindControl viewBindControl = new ViewBindControl();
        ViewBinderLevel root = new ViewBinderLevel();
        viewBindControl.setRoot(root);
        establish(root,clazz);
        return viewBindControl;
    }

    private static void establish(ViewBinderLevel level, Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields){
            Annotation[] annotations = field.getAnnotations();
            if (annotations == null||annotations.length == 0)
                continue;
            field.setAccessible(true);
            for (Annotation annotation:annotations){
                if (annotation.annotationType() == BindObj.class){
                    ViewBinderLevel sublevel = new ViewBinderLevel();
                    establish(sublevel,field.getType());
                    if (level.getObjs() == null)
                        level.setObjs(new HashMap<Field, ViewBinderLevel>());
                    level.getObjs().put(field,sublevel);
                    continue;
                }
                BindViewBase bindbase = annotation.annotationType().getAnnotation(BindViewBase.class);
                if (bindbase == null)
                    continue;
                ViewBindEntity entity = new ViewBindEntity();
                entity.setViewids(getItems(annotation));
                entity.setViewtype(bindbase.viewType());
                entity.setValuefield(field);
                entity.setValuetype(bindbase.dataType());
                if (bindbase.viewType() == RecyclerView.class||bindbase.viewType() == AbsListView.class){
                    entity.setViewType(ViewType.Collection);
                }else {
                    entity.setBindMethod(getMethod(bindbase));
                    entity.setViewType(ViewType.Default);
                }
                if (level.getElements() == null)
                    level.setElements(new HashMap<Field, ViewBindEntity>());
                level.getElements().put(field,entity);
            }
        }
    }

    private static Method getMethod(BindViewBase base){
        Method method = null;
        try {
            method = base.viewType().getDeclaredMethod(base.methodName(),base.dataType());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    private static int[] getItems(Annotation annotation){
        Class annoClazz = annotation.getClass();
        Method method = null;
        int[] items = null;
        try {
            method = annoClazz.getDeclaredMethod("value");
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            items = (int[]) method.invoke(annotation);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return items;
    }

}
