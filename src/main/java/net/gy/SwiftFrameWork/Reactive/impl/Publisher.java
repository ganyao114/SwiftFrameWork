package net.gy.SwiftFrameWork.Reactive.impl;


import net.gy.SwiftFrameWork.Reactive.IObserverProxy;
import net.gy.SwiftFrameWork.Reactive.IPublisher;
import net.gy.SwiftFrameWork.Reactive.OnFilter;
import net.gy.SwiftFrameWork.Reactive.OnObserver;
import net.gy.SwiftFrameWork.Reactive.OnPublisher;
import net.gy.SwiftFrameWork.Reactive.annotation.RunContext;
import net.gy.SwiftFrameWork.Reactive.entity.DataPack;
import net.gy.SwiftFrameWork.Reactive.entity.RunContextType;
import net.gy.SwiftFrameWork.Reactive.entity.actions.Action1;
import net.gy.SwiftFrameWork.Reactive.entity.actions.Change;
import net.gy.SwiftFrameWork.Reactive.entity.actions.FilterChange;
import net.gy.SwiftFrameWork.Reactive.entity.actions.FilterOperator;
import net.gy.SwiftFrameWork.Reactive.entity.actions.Func1;
import net.gy.SwiftFrameWork.Reactive.entity.actions.MapChange;
import net.gy.SwiftFrameWork.Reactive.utils.AnnotationUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Future;

/**
 * Created by pc on 16/5/13.
 */
public class Publisher<T> implements IPublisher<T>{

    private static HashMap<String,IPublisher> publishSaved = new HashMap<String,IPublisher>();

    private String name;

    private OnObserver<T> onObserver;
    private Change changeHead;
    private Change changeLast;
    private OnPublisher<T> onPublisher;
    private MapProxy mapProxy;
    private FilterProxy<T> filterProxy;

    private DataPack<T> data;

    private boolean isInit = true;

    public static <T> IPublisher<T> getInstance(){
        return new Publisher<T>();
    }

    public static <T> IPublisher<T> getPublisher(String name){
        return (IPublisher<T>)publishSaved.get(name);
    }

    private Publisher() {
        onObserver = new ObserverProxy<T>();
        changeHead = new Change() {
            @Override
            public void onSuccess(Object o) {
                preChange.onSuccess(o);
            }
        };
        changeLast = changeHead;
        data = new DataPack<T>();
        mapProxy = new MapProxy();
        filterProxy = new FilterProxy<T>();
    }

    @Override
    public IPublisher<T> create(OnPublisher<T> onPublisher) {
        this.onPublisher = onPublisher;
        return this;
    }

    @Override
    public IPublisher<T> create(final T[] ts) {
        onPublisher = new OnPublisher<T>() {
            @Override
            public void call(OnObserver<T> observer) {
                for (T t:ts)
                    observer.onSuccess(t);
                observer.onFinished();
            }
        };
        return this;
    }

    @Override
    public IPublisher<T> create(final Iterator<T> ts) {
        onPublisher = new OnPublisher<T>() {
            @Override
            public void call(OnObserver<T> observer) {
                while (ts.hasNext())
                    observer.onSuccess(ts.next());
                observer.onFinished();
            }
        };
        return this;
    }

    @Override
    public IPublisher<T> name(String t) {
        name = t;
        return this;
    }

    @Override
    public synchronized IPublisher<T> save() {
        if (name  == null)
            throw new RuntimeException("Publisher未命名");
        if (publishSaved.containsKey(name))
            throw new RuntimeException("Publisher重名");
        publishSaved.put(name,this);
        return this;
    }

    @Override
    public void destroy() {
        publishSaved.remove(name);
    }

    @Override
    public <I, O> IPublisher<T> change(Func1<I, O> change,Change changeProxy) {
        changeProxy.setChangeImpl(change);
        changeLast.setPreChange(changeProxy);
        changeLast = changeProxy;
        return this;
    }

    @Override
    public IPublisher<T> with(T t) {
        data.t = t;
        return this;
    }

    @Override
    public IPublisher<T> with(T[] ts) {
        data.arrary = ts;
        create(ts);
        return this;
    }

    @Override
    public IPublisher<T> with(Iterator<T> ts) {
        data.iterator = ts;
        create(ts);
        return this;
    }

    @Override
    public IPublisher<T> bind(OnObserver<T> observer) {
        ((IObserverProxy) onObserver).addObserver(observer);
        return this;
    }

    @Override
    public IPublisher<T> bind(final Action1<T> action1) {

        final RunContextType type = AnnotationUtils.getRunContext(action1,"call",Object.class);
        final int delayms = AnnotationUtils.getDelayAnno(action1,"call",Object.class);

        OnObserver<T> observer = new OnObserver<T>() {

            public RunContextType runcontext = type;
            public int delay = delayms;

            @RunContext(RunContextType.Dynamic)
            @Override
            public void onSuccess(T t) {
                action1.call(t);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onFinished() {

            }
        };
        ((IObserverProxy) onObserver).addObserver(observer);
        return this;
    }

    @Override
    public IPublisher<T> filter(OnFilter<T> filter) {
        filterProxy.addFilter(filter);
        return this;
    }

    @Override
    public IPublisher<T> filted(OnFilter<T> filter) {
        Change<T,T> filterChange = new FilterChange<T>();
        change(filter,filterChange);
        return this;
    }

    @Override
    public IPublisher<T> just(T t) {

        return this;
    }

    @Override
    public <I,O> IPublisher<T> map(Func1<I, O> action1) {
        mapProxy.addFun1(action1);
        return this;
    }

    @Override
    public <I, O> IPublisher<T> maped(Func1<I, O> maped) {
        Change<I,O> mapChange = new MapChange<I,O>();
        change(maped,mapChange);
        return this;
    }

    @Override
    public IPublisher<T> mapOn(RunContextType contextType) {
        return this;
    }

    @Override
    public IPublisher<T> remove(OnObserver<T> observer) {
        ((IObserverProxy<T>) onObserver).rmObserver(observer);
        return this;
    }

    @Override
    public IPublisher<T> reMap(Func1<T, IPublisher<T>> publisher) {
        return null;
    }

    @Override
    public IPublisher<T> link() {
        if (isInit) {
            OnPublisherProxy<T> publisherProxy = new OnPublisherProxy<T>(onPublisher);
            onPublisher = publisherProxy;
            if (mapProxy.isHasMap()) {
                MapedObserverProxy<T> mapedObserverProxy = new MapedObserverProxy<T>(onObserver, mapProxy);
                onObserver = mapedObserverProxy;
            }
            if (filterProxy.isFilter()){
                FiltedObserverProxy<T> filtedObserverProxy = new FiltedObserverProxy<T>(onObserver,filterProxy);
                onObserver = filtedObserverProxy;
            }
            changeLast.setPreChange(onObserver);
            isInit = false;
        }
        return this;
    }

    @Override
    public IPublisher<T> clear() {
        ((IObserverProxy<T>) onObserver).clear();
        return this;
    }

    @Override
    public Future post() {
        link();
        onPublisher.call(changeHead);
        OnPublisherProxy proxy = (OnPublisherProxy) onPublisher;
        return proxy.getFuture();
    }
}