package net.gy.SwiftFrameWork.MVVM.Interface;

import java.util.Map;

/**
 * Http请求模块接口，实现有Get,Post,Pull等
 * Created by pc on 16/8/29.
 */
public interface IHttpModel<T> {
    public void setUrl(String url);
    public void addPar(String key,String value);
    public void setPars(Map<String,String> pars);
    public void setHeaders(Map<String,String> headers);
    public void addHeader(String key,String value);
    public T dohttp() throws Exception;
    public void destory();
}
