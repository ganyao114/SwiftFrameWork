package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Annotations.JsonBase;
import net.gy.SwiftFrameWork.MVVM.Annotations.JsonObject;
import net.gy.SwiftFrameWork.MVVM.Annotations.JsonSet;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonAry;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonMem;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonObjType;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonTree;
import net.gy.SwiftFrameWork.MVVM.Interface.IHandler;

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
 * JSON解析器
 * Created by pc on 16/8/30.
 */
public class JsonParse {
    public static JsonTree establish(Class clazz){
        JsonMem headmem = new JsonMem();
        headmem.setJsontype(JsonObjType.HEAD);
        headmem.setType(clazz);
        headmem.setKey(clazz.getName());
        //开始递归建立树
        establish(headmem);
        JsonTree jsonTree = new JsonTree();
        jsonTree.setTop(headmem);
        return jsonTree;
    }

    private static void establish(JsonMem mem){
        Field[] fields = mem.getType().getDeclaredFields();
        List<JsonMem> childs = new ArrayList<>();
        //遍历所有域
        for (Field field:fields){
            Annotation[] annotations = field.getAnnotations();
            if (annotations == null||annotations.length == 0)
                continue;
            field.setAccessible(true);
            //便利所有注解
            for (Annotation annotation:annotations){
                Class<? extends Annotation> type = annotation.annotationType();
                //如果元素是JSONArray
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
                    //判断JSONArray的item是否是普通元素还是JSONObject，这里普通元素暂时偷懒用String代替
                    //如果不是普通类型则需要递归解析
                    if (jsonMem.getType()!=String.class)
                        establish(jsonMem);
                    childs.add(jsonMem);
                    //如果元素是JsonObject
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
                    //递归解析
                    establish(jsonMem);
                    childs.add(jsonMem);
                }else{
                    //只是普通元素
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
        //反射生成一个Model对象
        t = (T) clazz.newInstance();
        //取出根结点开始遍历解析
        getValue(tree.getTop(),json,t);
        return t;
    }

    public static String getJson(JsonTree tree,Object o) throws Exception{
        String jsonStr = getJson(tree.getTop(),o);
        return jsonStr;
    }

    public static String getJson(JsonMem mem,Object o) throws Exception{
        String jsonStr = null;
        Field field = mem.getField();
        Class type = mem.getType();
        List<JsonMem> childs = mem.getChilds();
        //依旧是判断元素类型
        switch (mem.getJsontype()){
            //如果是根节点
            case HEAD:
                JSONObject headobj = new JSONObject();
                if (childs!=null){
                    //遍历子元素
                    for (JsonMem child:childs){
                        Object co = mem.getField().get(o);
                        headobj.put(mem.getKey(),getJson(child,o));
                    }
                    jsonStr =  headobj.toString();
                }
                break;
            case ELEMENT:
                //普通元素直接赋值
                jsonStr =  mem.getField().get(o).toString();
                break;
            //JSONObject需要递归其子元素
            case OBJECT:
                JSONObject jsonObject = new JSONObject();
                if (childs!=null){
                    for (JsonMem child:childs){
                        Object co = mem.getField().get(o);
                        jsonObject.put(mem.getKey(),getJson(child,o));
                    }
                }
                jsonStr =  jsonObject.toString();
                break;
            //如果是JSONArrary
            case ARRAY:
                JSONArray array = new JSONArray();
                List list = (List) o;
                if (array == null||array.length() == 0)
                    return null;
                //如果item是普通元素则直接赋值
                if (type == String.class){
                    for (int i = 0;i < list.size();i++){
                        array.put(list.get(i).toString());
                    }
                }else {
                    //否则递归
                    for (int i = 0;i < list.size();i++){
                        JSONObject subjobj = new JSONObject();
                        if (childs!=null){
                            for (JsonMem child:childs){
                                subjobj.put(child.getKey(),getJson(child,list.get(i)));
                            }
                        }
                        array.put(subjobj);
                    }
                }
                jsonStr = array.toString();
                break;
        }
        return jsonStr;
    }

    private static void getValue(JsonMem mem,String json,Object object) throws Exception {
        Field field = mem.getField();
        Class type = mem.getType();
        List<JsonMem> childs = mem.getChilds();
        //依旧是判断元素类型
        switch (mem.getJsontype()){
            //如果是根节点
            case HEAD:
                JSONObject headobj = new JSONObject(json);
                if (childs!=null){
                    //遍历子元素
                    for (JsonMem child:childs){
                        String str = headobj.getString(child.getKey());
                        getValue(child,str,object);
                    }
                }
                break;
            case ELEMENT:
                //普通元素直接赋值
                field.set(object,json);
                break;
            //JSONObject需要递归其子元素
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
            //如果是JSONArrary
            case ARRAY:
                JSONArray array = new JSONArray(json);
                if (array == null||array.length() == 0)
                    break;
                List list = new ArrayList();
                //如果item是普通元素则直接赋值
                if (type == String.class){
                    for (int i = 0;i < array.length();i++){
                        list.add(array.getString(i));
                    }
                }else {
                    //否则递归
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
