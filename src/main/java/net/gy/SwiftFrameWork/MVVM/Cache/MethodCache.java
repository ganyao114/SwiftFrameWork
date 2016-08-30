package net.gy.SwiftFrameWork.MVVM.Cache;

import net.gy.SwiftFrameWork.MVVM.Entity.HttpBinderEntity;

/**
 * Created by pc on 16/8/30.
 */
public class MethodCache {

    private HttpBinderEntity binderEntity;
    private PojoCache ret;

    public PojoCache getRet() {
        return ret;
    }

    public void setRet(PojoCache ret) {
        this.ret = ret;
    }
}
