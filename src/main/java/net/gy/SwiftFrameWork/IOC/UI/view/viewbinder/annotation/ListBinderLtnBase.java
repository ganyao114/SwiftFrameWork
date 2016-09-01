package net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2016/4/11.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListBinderLtnBase {
    Class<?> listenerType();
    Class<?> viewType();
    String listenerSetter();
}
