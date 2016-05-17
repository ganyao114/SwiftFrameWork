package net.gy.SwiftFrameWork.Service.verification;

/**
 * Created by gy on 2016/4/21.
 */
public interface IValidater<K,V> {
    public V beforeValidate(K object);
    public V onValidate(K object);
    public V afterValidate(K object);
    public void setResult(IValiResult result);
}
