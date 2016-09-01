package net.gy.SwiftFrameWork.Service.buffer;

import net.gy.SwiftFrameWork.Service.buffer.entity.ActionType;

/**
 * Created by gy on 2016/4/20.
 */
public interface IObserver {
    public void addSuber(IBufferObject object);
    public void removeSber(IBufferObject object);
    public void notifyAct(ActionType type);
}
