package net.gy.SwiftFrameWork.IOC.UI.view.viewinject.annotation;

import android.widget.AdapterView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2015/11/17.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerType = AdapterView.OnItemClickListener.class, listenerSetter = "setOnItemClickListener", methodName = "onItemClick")
public @interface OnItemClick {
    int[] value();
}
