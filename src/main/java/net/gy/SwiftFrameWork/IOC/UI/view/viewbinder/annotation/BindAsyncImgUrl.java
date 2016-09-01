package net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.annotation;

import net.gy.SwiftFrameWork.UI.customwidget.autoloadimgview.AutoLoadImgView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2016/4/9.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ListBinderBase(viewType = AutoLoadImgView.class,methodName = "ShowImg",dataType = String.class)
public @interface BindAsyncImgUrl {
    int[] value();
}
