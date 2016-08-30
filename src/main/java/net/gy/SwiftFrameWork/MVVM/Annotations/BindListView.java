package net.gy.SwiftFrameWork.MVVM.Annotations;

import android.support.annotation.IdRes;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * Created by pc on 16/8/29.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@BindViewBase(viewType = ListView.class,methodName = "null",dataType = List.class)
public @interface BindListView {
    @IdRes int[] value();
}
