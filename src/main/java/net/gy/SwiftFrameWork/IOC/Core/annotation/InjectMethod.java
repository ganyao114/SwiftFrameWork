package net.gy.SwiftFrameWork.IOC.Core.annotation;

import net.gy.SwiftFrameWork.IOC.Core.injecter.IInjectMethodHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2015/11/17.
 */
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectMethod {
    Class<? extends IInjectMethodHandler> value();
}
