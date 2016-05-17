package net.gy.SwiftFrameWork.Reactive.entity.actions;

import net.gy.SwiftFrameWork.Reactive.IObserverProxy;
import net.gy.SwiftFrameWork.Reactive.OnObserver;

import java.util.Vector;

/**
 * Created by pc on 16/5/17.
 */
public abstract class Change<I,O> implements OnObserver<I>,IObserverProxy<O> {
    protected OnObserver<O> preChange;
    protected Func1 changeImpl;
    protected IObserverProxy iObserverProxy;
    public Change(OnObserver<I> preChange) {
        this.preChange = (OnObserver<O>) preChange;
        iObserverProxy = (IObserverProxy<I>) preChange;
    }
    public Change() {
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

    public void setPreChange(OnObserver<I> preChange){
        this.preChange = (OnObserver<O>) preChange;
        iObserverProxy = (IObserverProxy<I>) preChange;
    }

    public void setChangeImpl(Func1 changeImpl) {
        this.changeImpl = changeImpl;
    }
}
