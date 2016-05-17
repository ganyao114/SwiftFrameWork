package net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.annotation;

import android.view.View;
import android.widget.TextView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2016/3/15.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListBinderBase {
    Class<? extends View> viewType() default TextView.class;
    String methodName() default "setText";
    Class<?> dataType() default String.class;
}
