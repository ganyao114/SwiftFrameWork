package net.gy.SwiftFrameWork.Reactive.impl;

import net.gy.SwiftFrameWork.Reactive.IObserverProxy;
import net.gy.SwiftFrameWork.Reactive.OnObserver;

import java.util.Vector;

/**
 * Created by pc on 16/5/15.
 */
public class MapedObserverProxy<T> implements OnObserver<T>,IObserverProxy<T> {

    private OnObserver<T> observer;
    private MapProxy mapProxy;

    public MapedObserverProxy(OnObserver<T> observer,MapProxy mapProxy) {
        this.observer = observer;
        this.mapProxy = mapProxy;
    }

    @Override
    public void addObserver(OnObserver<T> observer) {

    }

    @Override
    public void rmObserver(OnObserver<T> observer) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Vector<OnObserver<T>> getObservers() {
        return null;
    }

    @Override
    public void onSuccess(T t) {
        Object par = mapProxy.call(t);
        observer.onSuccess((T) par);
    }

    @Override
    public void onError(Throwable throwable) {
        observer.onError(throwable);
    }

    @Override
    public void onFinished() {
        observer.onFinished();
    }
}
