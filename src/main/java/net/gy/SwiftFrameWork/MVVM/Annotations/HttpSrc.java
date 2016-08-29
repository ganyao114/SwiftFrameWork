package net.gy.SwiftFrameWork.MVVM.Annotations;

import net.gy.SwiftFrameWork.MVVM.Entity.HttpConnectMode;
import net.gy.SwiftFrameWork.MVVM.Entity.HttpRunMode;
import net.gy.SwiftFrameWork.MVVM.Impl.HttpDataSrcHandler;
import net.gy.SwiftFrameWork.MVVM.Interface.IFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by pc on 16/8/29.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@DataSrcBase(HttpDataSrcHandler.class)
public @interface HttpSrc {

}
