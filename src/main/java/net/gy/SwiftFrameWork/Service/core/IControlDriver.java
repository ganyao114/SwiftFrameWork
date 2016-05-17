package net.gy.SwiftFrameWork.Service.core;

/**
 * Created by gy on 2016/4/1.
 */
public interface IControlDriver<D,V> {
    public boolean control(D data,V views)throws Exception;
}
