package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Annotations.Header;
import net.gy.SwiftFrameWork.MVVM.Annotations.HttpSrcMethod;
import net.gy.SwiftFrameWork.MVVM.Annotations.JsonOrm;
import net.gy.SwiftFrameWork.MVVM.Annotations.Param;
import net.gy.SwiftFrameWork.MVVM.Annotations.Url;
import net.gy.SwiftFrameWork.MVVM.Cache.MethodCache;
import net.gy.SwiftFrameWork.MVVM.Cache.PojoCache;
import net.gy.SwiftFrameWork.MVVM.Entity.HttpBinderEntity;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonMem;
import net.gy.SwiftFrameWork.MVVM.Entity.ParEntry;
import net.gy.SwiftFrameWork.MVVM.Entity.ParType;
import net.gy.SwiftFrameWork.Model.entity.Entry;
import net.gy.SwiftFrameWork.Model.io.databus.entity.HttpDataSrc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 16/8/30.
 */
public class MethodParse {
    public static List<MethodCache> getMethodCaches(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        List<MethodCache> list = new ArrayList<>();
        for (Method method:methods){
            HttpSrcMethod anno = method.getAnnotation(HttpSrcMethod.class);
            if (anno == null)
                continue;
            MethodCache mcache = new MethodCache();
            Class retType = method.getReturnType();
            if (retType != null) {
                mcache.setRettype(retType);
                if (retType.getAnnotation(JsonOrm.class)!=null){
                    PojoCache pojoCache = new PojoCache();
                    pojoCache.setJsonTree(JsonParse.establish(retType));
                    pojoCache.setViewBindControl(ViewBinderParse.establish(retType));
                    mcache.setRet(pojoCache);
                    HttpBinderEntity binderEntity = new HttpBinderEntity();
                    binderEntity.setControl(anno);
                    binderEntity.setPars(getPars(method));
                    mcache.setBinderEntity(binderEntity);
                    mcache.setMethod(method);
                    list.add(mcache);
                }
            }
        }
        return list;
    }

    private static Entry<ParType,String>[] getPars(Method method){
        Class[] pars = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();
        if (pars == null||pars.length == 0)
            return null;
        Entry<ParType,String>[] parsEntries = new ParEntry[pars.length];
        for (int j = 0;j < pars.length;j++){
            Annotation[] annos = annotations[j];
            if (annos == null||annos.length==0)
                continue;
            for (Annotation tar:annos){
                Entry<ParType,String> entry = new ParEntry();
                if (tar.annotationType() == Url.class){
                    entry.set(ParType.URL,null);
                }else if (tar.annotationType() == Param.class){
                    entry.set(ParType.PAR,((Param)tar).value());
                }else if (tar.annotationType() == Header.class){
                    entry.set(ParType.HEADER,((Header)tar).value());
                }else {
                    continue;
                }
                parsEntries[j] = entry;
            }
        }
        return parsEntries;
    }

}
