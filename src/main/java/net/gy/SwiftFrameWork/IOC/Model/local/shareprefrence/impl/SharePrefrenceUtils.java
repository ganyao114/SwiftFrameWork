package net.gy.SwiftFrameWork.IOC.Model.local.shareprefrence.impl;

import android.content.Context;
import android.content.SharedPreferences;

import net.gy.SwiftFrameWork.IOC.Core.entity.ClassMemberPackage;
import net.gy.SwiftFrameWork.IOC.Core.impl.IOC;
import net.gy.SwiftFrameWork.IOC.Core.parase.ClassMemberParase;
import net.gy.SwiftFrameWork.IOC.Model.local.shareprefrence.annotation.SPColumeName;
import net.gy.SwiftFrameWork.IOC.Model.local.shareprefrence.annotation.SPOptions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;


/**
 * Created by gy on 2016/3/13.
 */
public class SharePrefrenceUtils {
    private static SharePrefrenceUtils utils;
    public static SharePrefrenceUtils getInstance(){
        synchronized (SharePrefrenceUtils.class){
            if (utils == null)
                utils = new SharePrefrenceUtils();
        }
        return utils;
    }

    public boolean save(Object object){
        Class clazz = object.getClass();
        SPOptions options = (SPOptions) clazz.getAnnotation(SPOptions.class);
        List<ClassMemberPackage> list = null;
        String name = null;
        if (options == null)
            return false;
        try {
            list = ClassMemberParase.GetFileds(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list == null||list.size() == 0)
            return false;
        if (options.name().equals(""))
            name = clazz.getName();
        else
            name = options.name();
        SharedPreferences sharedPreferences = IOC.getInstance()
                .getApplication()
                .getApplicationContext()
                .getSharedPreferences(name, options.mode());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (ClassMemberPackage mem : list){
            doSave(mem,editor);
        }
        editor.commit();
        return true;
    }

    public boolean get(Object object){
        Class clazz = object.getClass();
        SPOptions options = (SPOptions) clazz.getAnnotation(SPOptions.class);
        List<ClassMemberPackage> list = null;
        String name = null;
        if (options == null)
            return false;
        try {
            list = ClassMemberParase.GetFileds(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list == null||list.size() == 0)
            return false;
        if (options.name().equals(""))
            name = clazz.getName();
        else
            name = options.name();
        SharedPreferences sharedPreferences = IOC.getInstance()
                .getApplication()
                .getApplicationContext()
                .getSharedPreferences(name, options.mode());
        for (ClassMemberPackage mem : list){
            try {
                doGet(mem,sharedPreferences,object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean remove(Object entity,Object... fields){

        return true;
    }

    private void doGet(ClassMemberPackage mem, SharedPreferences share, Object object) throws IllegalAccessException {
        String name = null;
        Field field = mem.getField();
        if (field == null)
            return;
        field.setAccessible(true);
        if (mem.getAnnotations() == null||mem.getAnnotations().length == 0){
            name = mem.getName();
        }else {
            for (Annotation annotation:mem.getAnnotations()){
                if (annotation.annotationType() == SPColumeName.class){
                    name = ((SPColumeName)annotation).value();
                    break;
                }
            }
        }
        if (name == null)
            name = mem.getName();
        if (mem.getBasicType() != null){
            switch (mem.getBasicType()){
                case Int:
                    field.setInt(object,share.getInt(name,0));
                    break;
                case Float:
                    field.setFloat(object,share.getFloat(name,0));
                    break;
                case Double:
                    field.setDouble(object,share.getFloat(name,0));
                    break;
                case Long:
                    field.setLong(object,share.getLong(name, 0L));
                    break;
                case Boolean:
                    field.setBoolean(object,share.getBoolean(name,false));
            }
        }else {
            if (mem.getRefType() == String.class){
                field.set(object,share.getString(name,null));
            }else if (mem.getRefType() == Set.class){
                field.set(object,share.getStringSet(name,null));
            }else {
                field.set(object,share.getString(name,null));
            }
        }
    }

    private void doSave(ClassMemberPackage mem, SharedPreferences.Editor editor) {
        String name = null;
        if (mem.getAnnotations() == null||mem.getAnnotations().length == 0){
            name = mem.getName();
        }else {
            for (Annotation annotation:mem.getAnnotations()){
                if (annotation.annotationType() == SPColumeName.class){
                    name = ((SPColumeName)annotation).value();
                    break;
                }
            }
        }
        if (name == null)
            name = mem.getName();
        if (mem.getBasicType() != null){
            switch (mem.getBasicType()){
                case Int:
                    editor.putInt(name,mem.getBasicValue().getInt());
                    break;
                case Float:
                    editor.putFloat(name,mem.getBasicValue().getFloat());
                    break;
                case Double:
                    editor.putFloat(name, (float) mem.getBasicValue().getDouble());
                    break;
                case Long:
                    editor.putLong(name,mem.getBasicValue().getLong());
                    break;
                case Boolean:
                    editor.putBoolean(name,mem.getBasicValue().isBoolean());
                    break;
            }
        }else {
            if (mem.getRefType() == String.class){
                editor.putString(name,(String) mem.getValue());
            }else if (mem.getRefType() == Set.class){
                editor.putStringSet(name, (Set) mem.getValue());
            }else {
                editor.putString(name,mem.getValue().toString());
            }
        }
    }

    public static boolean Save(Object object){
        return getInstance().save(object);
    }

    public static boolean Get(Object object){
        return getInstance().get(object);
    }

    public static boolean Get(Context context,Object object){
        return getInstance().get(context,object);
    }

    private boolean get(Context context, Object object) {
        Class clazz = object.getClass();
        SPOptions options = (SPOptions) clazz.getAnnotation(SPOptions.class);
        List<ClassMemberPackage> list = null;
        String name = null;
        if (options == null)
            return false;
        try {
            list = ClassMemberParase.GetFileds(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list == null||list.size() == 0)
            return false;
        if (options.name().equals(""))
            name = clazz.getName();
        else
            name = options.name();
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(name, options.mode());
        for (ClassMemberPackage mem : list){
            try {
                doGet(mem,sharedPreferences,object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public static void Distory(){
        utils = null;
        System.gc();
    }

    class ExtCall{

    }

}
