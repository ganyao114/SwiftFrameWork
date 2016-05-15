package net.gy.SwiftFrameWork.Reactive.utils;

import net.gy.SwiftFrameWork.Reactive.annotation.RunContext;
import net.gy.SwiftFrameWork.Reactive.entity.RunContextType;
import net.gy.SwiftFrameWork.Reactive.impl.Observer;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.ClassMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

/**
 * Created by pc on 16/5/15.
 */
public class AnnotationUtils {
    public static void RefreshAnnotation(Class tar, Class[] parTypes, Method tarMethod, Class annotype, int annoValue, String annoName) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        //获取需要修改的类
        CtClass ct = pool.get(tar.getName());

        CtClass[] parTypects = new CtClass[parTypes.length];
        for (int i = 0;i < parTypes.length;i ++){
            parTypects[i] = pool.get(parTypes[i].getName());
        }

        CtMethod cm = ct.getDeclaredMethod(tarMethod.getName(),parTypects);

        MethodInfo minInfo = cm.getMethodInfo();

        ConstPool cp = minInfo.getConstPool();
        //获取注解信息
        AnnotationsAttribute attribute2 = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation(annotype.getName(), cp);
        //修改名称为unitName的注解
        annotation.addMemberValue(annoName, new IntegerMemberValue(cp, annoValue));
        attribute2.setAnnotation(annotation);
        minInfo.addAttribute(attribute2);
    }

    public static RunContextType getRunContext(Object object,String mtName,Class... partypes){
        try {
            Method method = object.getClass().getMethod(mtName,partypes);
            RunContext runContext = method.getAnnotation(RunContext.class);
            return runContext.value();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return RunContextType.CurrentThread;
    }
}
