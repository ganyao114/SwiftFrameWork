package net.gy.SwiftFrameWork.IOC.Model.net.http.entity;

import net.gy.SwiftFrameWork.Model.net.http.IUploadCallBack;
import net.gy.SwiftFrameWork.Model.net.http.impl.MyHttpService;
import net.gy.SwiftFrameWork.Service.thread.templet.HttpThreadTemplet;
import net.gy.SwiftFrameWork.Service.thread.templet.configs.HttpTheadConfigBean;

import java.io.Serializable;

/**
 * Created by gy on 2015/11/28.
 */
public class HttpInjectBean {
    private HttpTheadConfigBean configBean;
    private HttpThreadTemplet httpThreadTemplet;
    private MyHttpService service;
    private String url;
    private HttpConnectMode connectMode;
    private HttpRunMode runMode;
    private Serializable result;
    private Class<? extends IUploadCallBack> uploadViewCall;

    public HttpThreadTemplet getHttpThreadTemplet() {
        return httpThreadTemplet;
    }

    public void setHttpThreadTemplet(HttpThreadTemplet httpThreadTemplet) {
        this.httpThreadTemplet = httpThreadTemplet;
    }

    public MyHttpService getService() {
        return service;
    }

    public void setService(MyHttpService service) {
        this.service = service;
    }

    public HttpTheadConfigBean getConfigBean() {
        return configBean;
    }

    public void setConfigBean(HttpTheadConfigBean configBean) {
        this.configBean = configBean;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpConnectMode getConnectMode() {
        return connectMode;
    }

    public void setConnectMode(HttpConnectMode connectMode) {
        this.connectMode = connectMode;
    }

    public HttpRunMode getRunMode() {
        return runMode;
    }

    public void setRunMode(HttpRunMode runMode) {
        this.runMode = runMode;
    }

    public Serializable getResult() {

            return result;

    }

    public void setResult(Serializable result) {

            this.result = result;

    }

    public Class<? extends IUploadCallBack> getUploadViewCall() {
        return uploadViewCall;
    }

    public void setUploadViewCall(Class<? extends IUploadCallBack> uploadViewCall) {
        this.uploadViewCall = uploadViewCall;
    }
}
