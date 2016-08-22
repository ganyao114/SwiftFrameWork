package net.gy.SwiftFrameWork.IOC.Core.cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pc on 16/8/20.
 */
public class FieldEntity {

    private Field field;

    private Map<Class<? extends Annotation>,Annotation> annotations;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Map<Class<? extends Annotation>, Annotation> getAnnotations() {
        return annotations;
    }

    public void put(Class<? extends Annotation> key,Annotation annotation){
        if (annotations == null)
            annotations = new HashMap<>();
        annotations.put(key, annotation);
    }

    public Annotation get(Class<? extends Annotation> key){
        if (annotations == null)
            return null;
        return annotations.get(key);
    }

}
