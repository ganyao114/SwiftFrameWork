package net.gy.SwiftFrameWork.IOC.Core.injecter;

import net.gy.SwiftFrameWork.IOC.Core.annotation.InjectController;
import net.gy.SwiftFrameWork.IOC.Core.cache.FieldEntity;
import net.gy.SwiftFrameWork.IOC.Core.cache.ReflectWithCache;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by gy939 on 2016/9/12.
 */
public class Injecter {

    public static void inject(Object object){
        FieldEntity[] fieldEntities = ReflectWithCache.getFieldsWithType(object.getClass());
        if (fieldEntities == null)
            return;
        for (FieldEntity entity:fieldEntities){
            Annotation injectController = entity.getAnnotations().get(InjectController.class);
            if (injectController != null){
                InjectController canno = (InjectController) injectController;
                IInjectHandler handler = HandlerFactory.getHandler(canno.handler());
                try {
                    handler.inject(entity.getField(),object,injectController);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }
            for (Map.Entry<Class<? extends Annotation>,Annotation> entry:entity.getAnnotations().entrySet()){
                Class type = entry.getKey();
                type.getAnnotation(InjectController.class);
            }
        }
    }

}
