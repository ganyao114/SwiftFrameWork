package net.gy.SwiftFrameWork.MVVM.Annotations;

import android.support.annotation.IdRes;
import android.widget.TextView;

import net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.annotation.ListBinderBase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2016/3/15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@BindViewBase(viewType = TextView.class,methodName = "setText",dataType = CharSequence.class)
public @interface BindTextView {
    int[] value();
}
