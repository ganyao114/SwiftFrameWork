package net.gy.SwiftFrameWork.MVVM.Entity;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by pc on 16/8/30.
 */
public class ViewBinderLevel {

    private Map<Field,ViewBindEntity> elements;
    private Map<Field,ViewBinderLevel> objs;

    public Map<Field, ViewBindEntity> getElements() {
        return elements;
    }

    public void setElements(Map<Field, ViewBindEntity> elements) {
        this.elements = elements;
    }

    public Map<Field, ViewBinderLevel> getObjs() {
        return objs;
    }

    public void setObjs(Map<Field, ViewBinderLevel> objs) {
        this.objs = objs;
    }
}
