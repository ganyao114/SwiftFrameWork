package net.gy.SwiftFrameWork.Reactive;

/**
 * Created by pc on 16/5/14.
 */
public interface OnObserver<T> {
    public void onSuccess(T t);
    public void onError(Throwable throwable);
    public void onFinished();
}
