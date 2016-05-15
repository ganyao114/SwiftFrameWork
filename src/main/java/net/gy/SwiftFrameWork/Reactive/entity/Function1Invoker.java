package net.gy.SwiftFrameWork.Reactive.entity;

import net.gy.SwiftFrameWork.Reactive.entity.actions.Func1;

/**
 * Created by pc on 16/5/15.
 */
public class Function1Invoker<I,O> extends IInvokeDirect<Func1<I,O>> {
    @Override
    public <O> O invoke(String methodname, Object... pars) {
        O ret = (O) proxy.call((I) pars[0]);
        return ret;
    }
}
