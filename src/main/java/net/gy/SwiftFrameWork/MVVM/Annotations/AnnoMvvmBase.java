package net.gy.SwiftFrameWork.MVVM.Annotations;

import android.view.View;
import android.widget.TextView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by pc on 16/8/29.
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnoMvvmBase {
    Class<? extends View> viewType() default TextView.class;
    String setMethod() default "setText";
    Class<?> dataType() default String.class;
}
