package net.gy.SwiftFrameWork.Presenter;

import android.app.Activity;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gy939 on 2016/9/28.
 */
public class ActivityLifeCache {

    private static Map<Class,Vector<LifeInvokerEntity>> entityMap = new ConcurrentHashMap<>();

    public static Vector<LifeInvokerEntity> getEntity(Class clazz){
        Vector<LifeInvokerEntity> entitys = entityMap.get(clazz);
        if (entitys != null)
            return entitys;
        Method[] methods = clazz.getDeclaredMethods();
        if (methods == null)
            return null;
        for (Method method:methods){
            ActivityLife lifeAnno = method.getAnnotation(ActivityLife.class);
            if (lifeAnno == null)
                continue;
            if (entitys == null) {
                entitys = new Vector<>();
                entityMap.put(clazz,entitys);
            }
            for (Class<? extends Activity> ac:lifeAnno.activity()) {
                LifeInvokerEntity entity = new LifeInvokerEntity();
                entity.setTarType(ac);
                entity.setType(lifeAnno.lifeType());
                entity.setInvokerType(clazz);
                entity.setMethod(method);
                entitys.add(entity);
            }
        }
        return entitys;
    }

}
