package net.gy.SwiftFrameWork.MVVM.Entity;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by pc on 16/8/30.
 */
public class ViewBindItem {

    private List<ViewBindEntity> entities;
    private WeakReference<Object> valueRef;

    public List<ViewBindEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<ViewBindEntity> entities) {
        this.entities = entities;
    }

    public WeakReference<Object> getValueRef() {
        return valueRef;
    }

    public void setValueRef(WeakReference<Object> valueRef) {
        this.valueRef = valueRef;
    }
}
