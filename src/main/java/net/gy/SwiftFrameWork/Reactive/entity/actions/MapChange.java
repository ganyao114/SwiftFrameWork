package net.gy.SwiftFrameWork.Reactive.entity.actions;

import net.gy.SwiftFrameWork.Reactive.OnObserver;

/**
 * Created by pc on 16/5/17.
 */
public class MapChange<I,O> extends Change<I,O>{
    public MapChange(OnObserver<I> preChange) {
        super(preChange);
    }

    @Override
    public void onSuccess(O o) {

    }
}
