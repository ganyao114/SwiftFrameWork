package net.gy.SwiftFrameWork.MVVM.Test;

import net.gy.SwiftFrameWork.MVVM.Annotations.BindText;
import net.gy.SwiftFrameWork.MVVM.Annotations.JsonString;
import net.gy.SwiftFrameWork.R;

/**
 * Created by pc on 16/8/30.
 */
public class PojoSub {
    @BindText(1)
    @JsonString
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
