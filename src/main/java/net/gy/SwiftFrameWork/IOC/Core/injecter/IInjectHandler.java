package net.gy.SwiftFrameWork.IOC.Core.injecter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by gy939 on 2016/9/12.
 */
public interface IInjectHandler {
    public void inject(Field field,Object object, Annotation annotation) throws Exception;
}
