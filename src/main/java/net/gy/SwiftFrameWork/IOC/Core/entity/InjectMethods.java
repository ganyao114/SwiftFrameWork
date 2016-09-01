package net.gy.SwiftFrameWork.IOC.Core.entity;

import java.lang.reflect.Method;

/**
 * Created by gy on 2015/11/23.
 */
public class InjectMethods implements InjectInvoker {

    private Method srcMethod;
    private Class[] targetClazzs;
    private int[] ids;
    private InjectExcutor<?> injectExcutor;

    public InjectMethods(Method srcMethod, Class[] targetClazzs, int[] ids, InjectExcutor<?> injectExcutor) {
        this.srcMethod = srcMethod;
        this.targetClazzs = targetClazzs;
        this.ids = ids;
        this.injectExcutor = injectExcutor;
    }

    @Override
    public void invoke(Object beanObject, Object... args) {
        if (ids == null || targetClazzs == null)
            invokeDefault(beanObject, args);
        else
            invokeListener(beanObject, args);
    }

    private void invokeDefault(Object beanObject, Object... args) {
        srcMethod.setAccessible(true);
        try {
            if (args != null && args.length > 0) {
                srcMethod.invoke(beanObject, args);
            } else {
                srcMethod.invoke(beanObject);
            }
        } catch (Exception e) {

        }
    }

    private void invokeListener(Object beanObject, Object... args) {
        for (int id : ids) {
            //View view =
        }
    }
}
