package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Annotations.JsonBase;
import net.gy.SwiftFrameWork.MVVM.Annotations.JsonObject;
import net.gy.SwiftFrameWork.MVVM.Annotations.JsonSet;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonAry;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonMem;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonObjType;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonTree;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 16/8/30.
 */
public class JsonParse {
    public static JsonTree establish(Class clazz){
        JsonMem headmem = new JsonMem();
        headmem.setJsontype(JsonObjType.HEAD);
        headmem.setType(clazz);
        establish(headmem);
        JsonTree jsonTree = new JsonTree();
        jsonTree.setTop(headmem);
        return jsonTree;
    }

    private static void establish(JsonMem mem){
        Field[] fields = mem.getType().getDeclaredFields();
        List<JsonMem> childs = new ArrayList<>();
        for (Field field:fields){
            Annotation[] annotations = field.getAnnotations();
            if (annotations == null||annotations.length == 0)
                continue;
            field.setAccessible(true);
            for (Annotation annotation:annotations){
                Class<? extends Annotation> type = annotation.annotationType();
                if (type == JsonSet.class){
                    JsonSet set = (JsonSet) annotation;
                    String key = set.name();
                    if (key.equals(""))
                        key = field.getName();
                    JsonMem jsonMem = new JsonMem();
                    jsonMem.setKey(key);
                    jsonMem.setJsontype(JsonObjType.ARRAY);
                    jsonMem.setField(field);
                    jsonMem.setType(set.clazz());
                    establish(jsonMem);
                    childs.add(jsonMem);
                }else if (type == JsonObject.class){
                    JsonObject jsonObject = (JsonObject) annotation;
                    String key = jsonObject.value();
                    if (key.equals(""))
                        key = field.getName();
                    JsonMem jsonMem = new JsonMem();
                    jsonMem.setKey(key);
                    jsonMem.setJsontype(JsonObjType.OBJECT);
                    jsonMem.setField(field);
                    jsonMem.setType(field.getType());
                    establish(jsonMem);
                    childs.add(jsonMem);
                }else{
                    JsonBase jbase = annotation.annotationType().getAnnotation(JsonBase.class);
                    if (jbase == null)
                        continue;
                    String key =  getItem(annotation);
                    if (key == null||key.equals(""))
                        key = field.getName();
                    Class valueType = jbase.value();
                    JsonMem jsonMem = new JsonMem();
                    jsonMem.setKey(key);
                    jsonMem.setJsontype(JsonObjType.ELEMENT);
                    jsonMem.setField(field);
                    jsonMem.setType(field.getType());
                    childs.add(jsonMem);
                }
            }
        }
    }

    private static String getItem(Annotation annotation){
        Class annoClazz = annotation.getClass();
        Method method = null;
        String item = null;
        try {
            method = annoClazz.getDeclaredMethod("value");
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            item = (String) method.invoke(annotation);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return item;
    }

}
