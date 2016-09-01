package net.gy.SwiftFrameWork.Service.cache.serializable;

/**
 * Created by gy on 2016/4/8.
 */
public interface ISerializableUtils {
    public boolean saveObject(Object object,String name)throws Exception;
    public Object readObject(String name)throws Exception;
    public void setPath(String path);
}
