package net.gy.SwiftFrameWork.IOC.Core.injecter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by gy939 on 2016/9/26.
 */
public interface IInjectMethodHandler {
    public void inject(Annotation annotation, Method method,Object object);
}
