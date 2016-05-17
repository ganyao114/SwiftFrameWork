package net.gy.SwiftFrameWork.Reactive;

/**
 * Created by pc on 16/5/14.
 */
public interface OnPublisher<T> {
    public void call(OnObserver<T> observer);
}
