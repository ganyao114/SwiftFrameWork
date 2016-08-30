package net.gy.SwiftFrameWork.MVVM.Entity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 16/8/30.
 */
public class ViewBindControl {

    private Map<Field,ViewBindEntity> bindmap;

    public void show(){

    }

    public Map<Field, ViewBindEntity> getBindmap() {
        return bindmap;
    }

    public void setBindmap(Map<Field, ViewBindEntity> bindmap) {
        this.bindmap = bindmap;
    }
}
