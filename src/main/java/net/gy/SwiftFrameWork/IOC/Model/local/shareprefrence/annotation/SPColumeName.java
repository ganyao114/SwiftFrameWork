package net.gy.SwiftFrameWork.IOC.Model.local.shareprefrence.annotation;

import net.gy.SwiftFrameWork.IOC.Model.net.http.annotation.InjectHttpBase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2016/3/13.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@InjectHttpBase
public @interface SPColumeName {
    String value() default "";
}
