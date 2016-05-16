package net.gy.SwiftFrameWork.Reactive.entity.actions;

import net.gy.SwiftFrameWork.Reactive.OnObserver;
import net.gy.SwiftFrameWork.Reactive.impl.Publisher;

/**
 * Created by pc on 16/5/16.
 */
public interface Operator<I,O> extends Func1<OnObserver<I>,OnObserver<O>>{
}
