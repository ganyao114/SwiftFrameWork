package net.gy.SwiftFrameWork.MVVM.Interface;

import java.lang.reflect.Method;

/**
 * Created by pc on 16/8/29.
 */
public interface ICallBackInner<S,F> {
    public void onSuccess(Method invoker,S s);
    public void onFailed(Method invoker,F f);
}
