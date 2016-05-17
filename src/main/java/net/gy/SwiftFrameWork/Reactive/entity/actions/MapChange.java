package net.gy.SwiftFrameWork.Reactive.entity.actions;

import net.gy.SwiftFrameWork.Reactive.OnObserver;

/**
 * Created by pc on 16/5/17.
 */
public class MapChange<I,O> extends Change<I,O>{

    @Override
    public void onSuccess(I i) {
        preChange.onSuccess((O) changeImpl.call(i));
    }
}
