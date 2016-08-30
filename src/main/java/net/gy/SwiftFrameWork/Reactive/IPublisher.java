package net.gy.SwiftFrameWork.Reactive;

import android.app.Notification;

import net.gy.SwiftFrameWork.Reactive.entity.RunContextType;
import net.gy.SwiftFrameWork.Reactive.entity.actions.Action1;
import net.gy.SwiftFrameWork.Reactive.entity.actions.Change;
import net.gy.SwiftFrameWork.Reactive.entity.actions.Func1;

import java.util.Iterator;
import java.util.concurrent.Future;

/**
 * Created by pc on 16/5/13.
 */
public interface IPublisher<T> {
    public IPublisher<T> create(OnPublisher<T> onPublisher);
    public IPublisher<T> create(T[] ts);
    public IPublisher<T> create(Iterator<T> ts);
    public IPublisher<T> name(String t);
    public IPublisher<T> save();
    public void destroy();
    public <I,O> IPublisher<T> change(Func1<I,O> change,Change changeProxy);
    public IPublisher<T> with(T t);
    public IPublisher<T> with(T[] ts);
    public IPublisher<T> with(Iterator<T> ts);
    public IPublisher<T> bind(OnObserver<T> observer);
    public IPublisher<T> bind(Action1<T> action1);
    public IPublisher<T> filter(OnFilter<T> filter);
    public IPublisher<T> filted(OnFilter<T> filter);
    public IPublisher<T> just(T t);
    public <I,O> IPublisher<T> map(Func1<I,O> map);
    public <I,O> IPublisher<T> maped(Func1<I,O> maped);
    public IPublisher<T> mapOn(RunContextType contextType);
    public IPublisher<T> remove(OnObserver<T> observer);
    public IPublisher<T> reMap(Func1<T,IPublisher<T>> publisher);
    public IPublisher<T> link();
    public IPublisher<T> clear();
    public Future post();
}
