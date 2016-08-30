package net.gy.SwiftFrameWork.MVVM.Entity;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by pc on 16/8/30.
 */
public class ValueToView {

    private Map<Field,Object> values;

    public Map<Field, Object> getValues() {
        return values;
    }

    public void setValues(Map<Field, Object> values) {
        this.values = values;
    }
}
