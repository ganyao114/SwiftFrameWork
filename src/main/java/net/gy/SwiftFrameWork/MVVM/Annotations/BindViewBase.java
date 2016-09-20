package net.gy.SwiftFrameWork.MVVM.Annotations;

import android.view.View;
import android.widget.TextView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 视图绑定基础注解
 * Created by pc on 16/8/29.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindViewBase {
    Class<? extends View> viewType() default TextView.class;
    String methodName() default "setText";
    Class<?> dataType() default String.class;
}
