package net.gy.SwiftFrameWork.MVVM.Exception;

/**
 * 异常过滤器接口
 * Created by gy939 on 2016/9/13.
 */
public interface IExceptionFilter {
    public boolean exceptionFilter(Throwable throwable);
}
