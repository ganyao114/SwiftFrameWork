package net.gy.SwiftFrameWork.Reactive;

/**
 * Created by pc on 16/5/13.
 */
public interface ISubscriber<T> {
    public ISubscriber<T> subscribe(IPublisher<T> pub);
}
