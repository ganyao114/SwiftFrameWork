package net.gy.SwiftFrameWork.MVVM.Cache;

import net.gy.SwiftFrameWork.MVVM.Entity.JsonMem;
import net.gy.SwiftFrameWork.MVVM.Entity.JsonTree;
import net.gy.SwiftFrameWork.MVVM.Entity.ViewBindControl;

/**
 * 返回值映射缓存实体
 * Created by pc on 16/8/30.
 */
public class PojoCache {

    private JsonTree jsonTree;
    private ViewBindControl viewBindControl;

    public ViewBindControl getViewBindControl() {
        return viewBindControl;
    }

    public void setViewBindControl(ViewBindControl viewBindControl) {
        this.viewBindControl = viewBindControl;
    }

    public JsonTree getJsonTree() {
        return jsonTree;
    }

    public void setJsonTree(JsonTree jsonTree) {
        this.jsonTree = jsonTree;
    }
}
