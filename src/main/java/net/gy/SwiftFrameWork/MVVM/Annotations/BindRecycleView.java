package net.gy.SwiftFrameWork.MVVM.Annotations;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

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
@BindViewBase(viewType = RecyclerView.class,methodName = "null",dataType = List.class)
public @interface BindRecycleView {
    @IdRes int[] value();
}
