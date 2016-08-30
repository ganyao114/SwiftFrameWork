package net.gy.SwiftFrameWork.MVVM.Entity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 16/8/30.
 */
public class ViewBindControl {

    private ViewBinderLevel root;

    public ViewBinderLevel getRoot() {
        return root;
    }

    public void setRoot(ViewBinderLevel root) {
        this.root = root;
    }
}
