package net.gy.SwiftFrameWork.Reactive.entity.actions;

import net.gy.SwiftFrameWork.Reactive.IObserverProxy;
import net.gy.SwiftFrameWork.Reactive.OnObserver;

import java.util.Vector;

/**
 * Created by pc on 16/5/17.
 */
public abstract class Change<I,O> implements OnObserver<O>,IObserverProxy<O> {
    protected OnObserver<I> preChange;
    protected IObserverProxy iObserverProxy;
    public Change(OnObserver<I> preChange) {
        this.preChange = preChange;
        iObserverProxy = (IObserverProxy<I>) preChange;
    }

    @Override
    public void addObserver(OnObserver observer) {
        iObserverProxy.addObserver(observer);
    }

    @Override
    public void rmObserver(OnObserver observer) {
        iObserverProxy.addObserver(observer);
    }

    @Override
    public void clear() {
        iObserverProxy.clear();
    }

    @Override
    public Vector<OnObserver<O>> getObservers() {
        return iObserverProxy.getObservers();
    }

    @Override
    public void onError(Throwable throwable) {
        preChange.onError(throwable);
    }

    @Override
    public void onFinished() {
        preChange.onFinished();
    }
}
