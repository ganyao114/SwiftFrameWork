package net.gy.SwiftFrameWork.Presenter;

import android.app.Activity;

import net.gy.SwiftFrameWork.IOC.Core.annotation.InjectMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy939 on 2016/9/26.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@InjectMethod(ActivityLifeHandler.class)
public @interface ActivityLife {
    ActivityLifeType lifeType();
    Class<? extends Activity>[] activity();
}
