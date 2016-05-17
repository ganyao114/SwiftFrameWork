package net.gy.SwiftFrameWork.IOC.Core.parase;

import net.gy.SwiftFrameWork.IOC.Core.entity.BasicValuePackage;
import net.gy.SwiftFrameWork.IOC.Core.entity.BasicValueType;
import net.gy.SwiftFrameWork.IOC.Core.entity.ClassMemberPackage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gy on 2016/1/22.
 */
public class ClassMemberParase {

    private static ClassMemberParase parase;

    private ClassMemberParase(){}

    public static ClassMemberParase getInstance(){
        synchronized (ClassMemberParase.class){
            if (parase == null)
                parase = new ClassMemberParase();
        }
        return parase;
    }

    public List<ClassMemberPackage> getFileds(Object object) throws Exception {
        Class clazz;
        if (object instanceof Class)
            clazz = (Class) object;
        else
            clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null||fields.length == 0)
            return null;
        List<ClassMemberPackage> list = new ArrayList<ClassMemberPackage>();
        for (Field field:fields){
            ClassMemberPackage aPackage = new ClassMemberPackage();
            String name = field.getName();
            aPackage.setName(name);
            field.setAccessible(true);
            aPackage.setField(field);
            String modify = Modifier.toString(field.getModifiers());
            aPackage.setModify(modify);
            Annotation[] annotations = field.getAnnotations();
            aPackage.setAnnotations(annotations);
            BasicValueReturn basicValueReturn = getBasicValue(field,object);
            BasicValueType basicValueType = basicValueReturn.type;
            if (basicValueType == null){
                aPackage.setRefType((Class<?>) field.getGenericType());
                if (!(object instanceof Class)) {
                    try {
                        aPackage.setValue(field.get(object));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                aPackage.setBasicType(basicValueType);
                if (!(object instanceof Class))
                    aPackage.setBasicValue(basicValueReturn.value);
            }
            list.add(aPackage);
        }
        return list;
    }

    private BasicValueReturn getBasicValue(Field field, Object object) throws IllegalAccessException {
        String type = field.getGenericType().toString();
        field.setAccessible(true);
        BasicValueType basicValueType = null;
        BasicValuePackage valuePackage = new BasicValuePackage();
        BasicValueReturn basicValueReturn = new BasicValueReturn();
        switch (type){
            case "int":
                basicValueType = BasicValueType.Int;
                if (!(object instanceof Class))
                    valuePackage.setInt(field.getInt(object));
                break;
            case "long":
                basicValueType = BasicValueType.Long;
                if (!(object instanceof Class))
                    valuePackage.setLong(field.getLong(object));
                break;
            case "float":
                basicValueType = BasicValueType.Float;
                if (!(object instanceof Class))
                    valuePackage.setFloat(field.getFloat(object));
                break;
            case "byte":
                basicValueType = BasicValueType.Byte;
                if (!(object instanceof Class))
                    valuePackage.setByte(field.getByte(object));
                break;
            case "boolean":
                basicValueType = BasicValueType.Boolean;
                if (!(object instanceof Class))
                    valuePackage.setBoolean(field.getBoolean(object));
                break;
            case "double":
                basicValueType = BasicValueType.Double;
                if (!(object instanceof Class))
                    valuePackage.setDouble(field.getDouble(object));
                break;
            case "char":
                basicValueType = BasicValueType.Char;
                if (!(object instanceof Class))
                    valuePackage.setChar(field.getChar(object));
                break;
            default:
                break;
        }
        basicValueReturn.type = basicValueType;
        basicValueReturn.value = valuePackage;
        return basicValueReturn;
    }

    class BasicValueReturn{
        public BasicValueType type;
        public BasicValuePackage value;
    }

    public static List<ClassMemberPackage> GetFileds(Object object)throws Exception{
        return getInstance().getFileds(object);
    }

}
