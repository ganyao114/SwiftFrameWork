package net.gy.SwiftFrameWork.MVVM.Interface;

/**
 * Created by pc on 16/8/29.
 */
public interface IHttpModel<T> {
    public void setUrl(String url);
    public void addPar(String key,String value);
    public void addHeader(String key,String value);
    public T dohttp() throws Exception;
    public void destory();
}
