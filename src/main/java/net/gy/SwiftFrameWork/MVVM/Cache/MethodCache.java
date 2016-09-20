package net.gy.SwiftFrameWork.MVVM.Cache;

import net.gy.SwiftFrameWork.MVVM.Entity.HttpBinderEntity;

import java.lang.reflect.Method;

/**
 * 方法反射信息缓存实体
 * Created by pc on 16/8/30.
 */
public class MethodCache {

    private Method method;
    private HttpBinderEntity binderEntity;
    private PojoCache ret;
    private Class rettype;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public HttpBinderEntity getBinderEntity() {
        return binderEntity;
    }

    public void setBinderEntity(HttpBinderEntity binderEntity) {
        this.binderEntity = binderEntity;
    }

    public Class getRettype() {
        return rettype;
    }

    public void setRettype(Class rettype) {
        this.rettype = rettype;
    }

    public PojoCache getRet() {
        return ret;
    }

    public void setRet(PojoCache ret) {
        this.ret = ret;
    }
}
