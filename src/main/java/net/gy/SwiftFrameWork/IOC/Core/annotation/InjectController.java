package net.gy.SwiftFrameWork.IOC.Core.annotation;

import net.gy.SwiftFrameWork.IOC.Core.injecter.IInjectHandler;
import net.gy.SwiftFrameWork.Model.entity.Entry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

/**
 * Created by gy939 on 2016/9/12.
 */
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectController {
    Class<? extends IInjectHandler> handler();
    String[] dependFields() default {};
}
