package net.gy.SwiftFrameWork.MVVM.Entity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 16/8/30.
 */
public class ViewBindControl {

    private Map<Field,ViewBindItem> bindmap;

    public void show(){

    }

    public Map<Field, ViewBindItem> getBindmap() {
        return bindmap;
    }

    public void setBindmap(Map<Field, ViewBindItem> bindmap) {
        this.bindmap = bindmap;
    }
}
