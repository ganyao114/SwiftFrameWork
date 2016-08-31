package net.gy.SwiftFrameWork.MVVM.Test;

import net.gy.SwiftFrameWork.MVVM.Annotations.BindObj;
import net.gy.SwiftFrameWork.MVVM.Annotations.BindTextView;
import net.gy.SwiftFrameWork.MVVM.Annotations.JsonObject;
import net.gy.SwiftFrameWork.MVVM.Annotations.JsonOrm;
import net.gy.SwiftFrameWork.MVVM.Annotations.JsonString;

/**
 * Created by pc on 16/8/30.
 */
@JsonOrm
public class TestPojo {

    @BindTextView(1)
    @JsonString
    private String name;
    @JsonString
    private String pass;
    @BindObj
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
