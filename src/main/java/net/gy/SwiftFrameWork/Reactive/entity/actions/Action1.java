package net.gy.SwiftFrameWork.Reactive.entity.actions;

/**
 * Created by pc on 16/5/15.
 */
public abstract class Action1<T> implements Action {
    public T t;
    public abstract void call(T t);

    @Override
    public Object invoke() {
        return null;
    }
}
