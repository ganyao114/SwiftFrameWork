package net.gy.SwiftFrameWork.MVVM.Interface;

/**
 * Created by pc on 16/8/29.
 */
public interface ICallBack<S,F> {
    public void onSuccess(S s);
    public void onFailed(F f);
}
