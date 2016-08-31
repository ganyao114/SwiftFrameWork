package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Annotations.JsonBase;
import net.gy.SwiftFrameWork.MVVM.Annotations.JsonObject;
import net.gy.SwiftFrameWork.MVVM.Annotations.JsonSet;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonAry;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonMem;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonObjType;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonTree;

import org.json.JSONArray;
import org.json.JSONException;
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
        headmem.setKey(clazz.getName());
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
                    if (jsonMem.getType()!=String.class)
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
        if (childs.size()!=0)
            mem.setChilds(childs);
    }
    public static <T> T getValue(JsonTree tree,String json) throws Exception{
        Class clazz = tree.getTop().getType();
        T t = null;
        t = (T) clazz.newInstance();
        getValue(tree.getTop(),json,t);
        return t;
    }

    private static void getValue(JsonMem mem,String json,Object object) throws Exception {
        Field field = mem.getField();
        Class type = mem.getType();
        List<JsonMem> childs = mem.getChilds();
        switch (mem.getJsontype()){
            case HEAD:
                JSONObject headobj = new JSONObject(json);
                if (childs!=null){
                    for (JsonMem child:childs){
                        String str = headobj.getString(child.getKey());
                        getValue(child,str,object);
                    }
                }
                break;
            case ELEMENT:
                field.set(object,json);
                break;
            case OBJECT:
                JSONObject jsonObject = new JSONObject(json);
                Object valueobj = type.newInstance();
                if (childs!=null){
                    for (JsonMem child:childs){
                        String str = jsonObject.getString(child.getKey());
                        getValue(child,str,valueobj);
                    }
                }
                field.set(object,valueobj);
                break;
            case ARRAY:
                JSONArray array = new JSONArray(json);
                if (array == null||array.length() == 0)
                    break;
                List list = new ArrayList();
                if (type == String.class){
                    for (int i = 0;i < array.length();i++){
                        list.add(array.getString(i));
                    }
                }else {
                    for (int i = 0;i < array.length();i++){
                        Object arritem = type.newInstance();
                        JSONObject subobj = array.getJSONObject(i);
                        if (childs!=null){
                            for (JsonMem child:childs){
                                String str = subobj.getString(child.getKey());
                                getValue(child,str,arritem);
                            }
                        }
                        list.add(arritem);
                    }
                }
                field.set(object,list);
                break;
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
