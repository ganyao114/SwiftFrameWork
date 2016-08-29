package net.gy.SwiftFrameWork.IOC.Core.parase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by pc on 16/8/28.
 */
public class ParAnnoFactory {

    public void decode(Method method){
        Annotation[][] annotations = method.getParameterAnnotations();
        Class[] types = method.getParameterTypes();
        for (Class clazz:types){
            
        }
    }

}
