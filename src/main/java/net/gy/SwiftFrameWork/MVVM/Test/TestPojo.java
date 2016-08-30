package net.gy.SwiftFrameWork.MVVM.Test;

import net.gy.SwiftFrameWork.MVVM.Annotations.JsonObject;
import net.gy.SwiftFrameWork.MVVM.Annotations.JsonString;

/**
 * Created by pc on 16/8/30.
 */
public class TestPojo {

    @JsonString
    private String name;
    @JsonString
    private String pass;
    @JsonObject
    private PojoSub obj;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public PojoSub getObj() {
        return obj;
    }

    public void setObj(PojoSub obj) {
        this.obj = obj;
    }
}
