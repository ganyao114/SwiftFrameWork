package net.gy.SwiftFrameWork.Reactive.impl;

import net.gy.SwiftFrameWork.Reactive.IObserverProxy;
import net.gy.SwiftFrameWork.Reactive.OnObserver;

import java.util.Vector;

/**
 * Created by pc on 16/5/15.
 */
public class MapedObserverProxy<T> implements OnObserver<T>,IObserverProxy<T> {

    private OnObserver<T> observerproxy;
    private MapProxy mapProxy;

    public MapedObserverProxy(OnObserver<T> observer,MapProxy mapProxy) {
        this.observerproxy = observer;
        this.mapProxy = mapProxy;
    }

    @Override
    public void addObserver(OnObserver<T> observer) {
        ((IObserverProxy<T>)observerproxy).addObserver(observer);
    }

    @Override
    public void rmObserver(OnObserver<T> observer) {
        ((IObserverProxy<T>)observerproxy).rmObserver(observer);
    }

    @Override
    public void clear() {
        ((IObserverProxy<T>) observerproxy).clear();
    }

    @Override
    public Vector<OnObserver<T>> getObservers() {
        return ((IObserverProxy<T>)observerproxy).getObservers();
    }

    @Override
    public void onSuccess(T t) {
        Object par = mapProxy.call(t);
        observerproxy.onSuccess((T) par);
    }

    @Override
    public void onError(Throwable throwable) {
        observerproxy.onError(throwable);
    }

    @Override
    public void onFinished() {
        observerproxy.onFinished();
    }
}
