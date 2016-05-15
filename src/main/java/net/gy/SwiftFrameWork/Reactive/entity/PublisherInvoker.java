package net.gy.SwiftFrameWork.Reactive.entity;

import net.gy.SwiftFrameWork.Reactive.OnObserver;
import net.gy.SwiftFrameWork.Reactive.OnPublisher;
import net.gy.SwiftFrameWork.Reactive.impl.ObserverProxy;

/**
 * Created by pc on 16/5/14.
 */
public class PublisherInvoker extends IInvokeDirect<OnPublisher>{
    @Override
    public <V> V invoke(String methodname, Object... pars) {
        proxy.call((OnObserver) pars[0]);
        return null;
    }
}
