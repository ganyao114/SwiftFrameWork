package net.gy.SwiftFrameWork.IOC.Core.cache;

import android.content.Context;
import android.util.Log;

import net.gy.SwiftFrameWork.IOC.Core.impl.IOC;
import net.gy.SwiftFrameWork.Service.cache.control.CachePool;
import net.gy.SwiftFrameWork.Service.cache.control.CachePoolGroup;
import net.gy.SwiftFrameWork.Service.cache.entity.C;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

/**
 * Created by pc on 16/8/21.
 */
public class ReflectCacheControl {

    private static ReflectCacheControl control;

    private Map<ClassType,List<Class>> needcache;

    private Map<Class<Annotation>,Class<Annotation>> anooClazz;

    public void decode(){

    }

    private ReflectCacheControl() {
        CachePool.getRoot().putPool(new Object[]{ReflectCacheRoute.clazz},new ReflectClassCache());
        CachePool.getRoot().putPool(new Object[]{ReflectCacheRoute.field},new ReflectFieldCache());
        CachePool.getRoot().putPool(new Object[]{ReflectCacheRoute.methed},new ReflectMethedCache());
    }

    public static ReflectCacheControl getInstance(){
        if (control == null) {
            synchronized (ReflectCacheControl.class) {
                if (control == null)
                    control = new ReflectCacheControl();
            }
        }
        return control;
    }

    public void dodecode(List<Class> classes){

        for (Class clazz:classes){
            decodeClass(clazz);
            decodeMethod(clazz);
            decodeFeild(clazz);
        }

    }

    public void decodeFeild(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null||fields.length == 0)
            return;
        List<FieldEntity> entities = new ArrayList<>();
        for (Field field:fields){
            Annotation[] annotations = field.getAnnotations();
            if (annotations == null||annotations.length == 0)
                continue;
            field.setAccessible(true);
            FieldEntity entity = new FieldEntity();
            entity.setField(field);
            for (Annotation annotation:annotations){
                entity.put(annotation.annotationType(),annotation);
            }
            entities.add(entity);
        }

        if (entities.size()==0)
            return;
        FieldEntity[] fieldEntities = entities.toArray(new FieldEntity[]{});
        Object[] route = new Object[]{ReflectCacheRoute.field,clazz};
        CachePool.getRoot().put(route,fieldEntities);
    }

    public void decodeMethod(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        if (methods == null||methods.length == 0)
            return;
        List<MethodEntity> entities = new ArrayList<>();
        for (Method method:methods){
            Annotation[] annotations = method.getAnnotations();
            if (annotations == null||annotations.length == 0)
                continue;
            method.setAccessible(true);
            MethodEntity entity = new MethodEntity();
            entity.setMethod(method);
            for (Annotation annotation:annotations){
                entity.put(annotation.annotationType(),annotation);
            }
            entities.add(entity);
        }
        if (entities.size()==0)
            return;
        MethodEntity[] methodEntity = entities.toArray(new MethodEntity[]{});
        Object[] route = new Object[]{ReflectCacheRoute.methed,clazz};
        CachePool.getRoot().put(route,methodEntity);
    }

    public void decodeClass(Class clazz) {
        Annotation[] annotations = clazz.getAnnotations();
        if (annotations==null||annotations.length==0)
            return;
        Map<Class<? extends Annotation>,Annotation> map = new HashMap<>();
        for (Annotation annotation:annotations){
            map.put(annotation.annotationType(),annotation);
        }
        Object[] route = new Object[]{ReflectCacheRoute.clazz,clazz};
        CachePool.getRoot().put(route,map);
    }

//    private List<>

//    public static void getClazzes(final Context context){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    DexFile df = new DexFile(context.getPackageCodePath());
//                    Log.e("gy",context.getPackageCodePath());
//                    Enumeration<String> n=df.entries();
//                    int i = 0;
//                    while(n.hasMoreElements()){
//                        Log.e("gy",n.nextElement());
//                        i++;
//                    }
//                    Log.e("gy",i+"");
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }).start();
//
//
//    }




}
