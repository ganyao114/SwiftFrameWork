package net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2016/4/11.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ListBinderLtnBase(listenerType = View.OnFocusChangeListener.class,viewType = View.class,listenerSetter = "setOnFocusChangeListener")
public @interface OnFoucsChange {
    int[] value();
}
