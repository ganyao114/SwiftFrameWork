package net.gy.SwiftFrameWork.Model.io.databus.entity;

import net.gy.SwiftFrameWork.IOC.Model.net.http.entity.HttpConnectMode;

import org.apache.http.HttpEntity;

import java.util.Map;

/**
 * Created by gy on 2016/3/30.
 */
public class HttpDataSrc {
    private String url;
    private HttpConnectMode mode;
    private Map<String,String> headers;
    private Map<String,String> parms;
    private Map<String,HttpEntity> entitys;
    private String Cookie;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpConnectMode getMode() {
        return mode;
    }

    public void setMode(HttpConnectMode mode) {
        this.mode = mode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParms() {
        return parms;
    }

    public void setParms(Map<String, String> parms) {
        this.parms = parms;
    }

    public Map<String, HttpEntity> getEntitys() {
        return entitys;
    }

    public void setEntitys(Map<String, HttpEntity> entitys) {
        this.entitys = entitys;
    }

    public String getCookie() {
        return Cookie;
    }

    public void setCookie(String cookie) {
        Cookie = cookie;
    }
}
