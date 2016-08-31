package net.gy.SwiftFrameWork.MVVM.Interface;

import java.lang.reflect.Method;

/**
 * Created by pc on 16/8/29.
 */
public interface IMethodProxy {
    public void donoret(Method invoker, Object[] pars);
    public Object dohasret(Method invokerName,Object[] pars);
}
