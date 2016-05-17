package net.gy.SwiftFrameWork.Reactive.impl;

import net.gy.SwiftFrameWork.Reactive.IPublisher;
import net.gy.SwiftFrameWork.Reactive.ISubscriber;
import net.gy.SwiftFrameWork.Reactive.OnObserver;

/**
 * Created by pc on 16/5/13.
 */
public class Subscriber<T> implements ISubscriber<T>,OnObserver<T> {

    private OnObserver<T> onObserver;

    public static <T> Subscriber getInstance(OnObserver<T> onObserver){
        return new Subscriber<T>(onObserver);
    }

    public Subscriber(OnObserver<T> onObserver) {
        this.onObserver = onObserver;
    }

    @Override
    public ISubscriber<T> subscribe(IPublisher<T> pub) {
        pub.bind(this);
        return this;
    }

    @Override
    public void onSuccess(T t) {
        onObserver.onSuccess(t);
    }

    @Override
    public void onError(Throwable throwable) {
        onObserver.onError(throwable);
    }

    @Override
    public void onFinished() {
        onObserver.onFinished();
    }

    public OnObserver<T> getOnObserver() {
        return onObserver;
    }

    public void setOnObserver(OnObserver<T> onObserver) {
        this.onObserver = onObserver;
    }

}
