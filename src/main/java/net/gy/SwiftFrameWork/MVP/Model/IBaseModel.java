package net.gy.SwiftFrameWork.MVP.Model;

/**
 * Created by gy on 2016/4/22.
 */
public interface IBaseModel<T>{
    public void initModel();
    public void destoryModel();
    public <T> void onResult(T t);
    public void onError(Object object);
}
