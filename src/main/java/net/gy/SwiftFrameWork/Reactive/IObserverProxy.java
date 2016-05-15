package net.gy.SwiftFrameWork.Reactive;

import java.util.Vector;

/**
 * Created by pc on 16/5/14.
 */
public interface IObserverProxy<T> {
    public void addObserver(OnObserver<T> observer);
    public void rmObserver(OnObserver<T> observer);
    public void clear();
    public Vector<OnObserver<T>> getObservers();
}
