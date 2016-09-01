package net.gy.SwiftFrameWork.MVP.Model;

import net.gy.SwiftFrameWork.IOC.Model.net.http.entity.HttpConnectMode;
import net.gy.SwiftFrameWork.Model.net.http.IHttpDealCallBack;
import net.gy.SwiftFrameWork.Service.thread.templet.configs.HttpTheadConfigBean;

/**
 * Created by gy on 2016/4/22.
 */
public interface IBaseModel<T>{
    public void initModel();
    public void destoryModel();
    public <T> void onResult(T t);
    public void onError(Object object);

    public String setUrl();
    public IHttpDealCallBack setCallBack();
    public HttpConnectMode setconMode();
    public HttpTheadConfigBean setConfig();
}
