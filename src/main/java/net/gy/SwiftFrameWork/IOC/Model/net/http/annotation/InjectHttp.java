package net.gy.SwiftFrameWork.IOC.Model.net.http.annotation;

import net.gy.SwiftFrameWork.IOC.Model.net.http.entity.HttpConnectMode;
import net.gy.SwiftFrameWork.IOC.Model.net.http.entity.HttpRunMode;
import net.gy.SwiftFrameWork.Model.net.http.IUploadCallBack;
import net.gy.SwiftFrameWork.Model.net.http.entity.UploadProgressView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by gy on 2015/11/25.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectHttp {
    String url();

    HttpRunMode mode() default HttpRunMode.Async;

    HttpConnectMode connectmode() default HttpConnectMode.Post;

    Class<? extends IUploadCallBack> uploadCall() default UploadProgressView.class;
}
