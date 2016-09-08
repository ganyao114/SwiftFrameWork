package net.gy.SwiftFrameWork.MVVM.Entity;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by pc on 16/8/29.
 */
public class JsonMem {

    //json中对应的key
    private String key;
    //json元素类型,普通元素，JSONObject，JSONArrary，或者根结点
    private JsonObjType jsontype;
    //对应数据模型model中的类型
    private Class type;
    //对应数据模型model中的field
    private Field field;
    //子节点列表
    private List<JsonMem> childs;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public List<JsonMem> getChilds() {
        return childs;
    }

    public void setChilds(List<JsonMem> childs) {
        this.childs = childs;
    }

    public JsonObjType getJsontype() {
        return jsontype;
    }

    public void setJsontype(JsonObjType jsontype) {
        this.jsontype = jsontype;
    }
}
