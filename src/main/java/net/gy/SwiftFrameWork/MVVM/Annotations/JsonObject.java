package net.gy.SwiftFrameWork.MVVM.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by pc on 16/8/29.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JsonBase(Object.class)
public @interface JsonObject {
    String value() default "";
}
