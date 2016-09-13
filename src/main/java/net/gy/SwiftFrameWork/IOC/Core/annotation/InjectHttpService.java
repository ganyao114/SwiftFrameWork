package net.gy.SwiftFrameWork.IOC.Core.annotation;

import net.gy.SwiftFrameWork.IOC.Core.injecter.impl.HttpInjectHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy939 on 2016/9/12.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@InjectController(handler = HttpInjectHandler.class)
public @interface InjectHttpService {
    String[] value();
}
