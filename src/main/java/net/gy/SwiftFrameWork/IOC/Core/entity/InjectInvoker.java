package net.gy.SwiftFrameWork.IOC.Core.entity;

/**
 * Created by gy on 2015/11/22.
 */
public interface InjectInvoker {
    public void invoke(Object beanObject, Object... args);
}
