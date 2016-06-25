package net.gy.SwiftFrameWork.MVP.View.context;

import net.gy.SwiftFrameWork.MVP.Presenter.Presenter;

/**
 * Created by gy on 2016/3/2.
 */
public interface IContext<T extends Presenter> {
    public T getPresent();
}
