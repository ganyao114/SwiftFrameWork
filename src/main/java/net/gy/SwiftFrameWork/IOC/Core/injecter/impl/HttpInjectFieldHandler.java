package net.gy.SwiftFrameWork.IOC.Core.injecter.impl;

import android.app.Activity;
import android.view.View;

import net.gy.SwiftFrameWork.IOC.Core.annotation.InjectController;
import net.gy.SwiftFrameWork.IOC.Core.annotation.InjectHttpService;
import net.gy.SwiftFrameWork.IOC.Core.injecter.IInjectFieldHandler;
import net.gy.SwiftFrameWork.MVVM.Impl.HttpProxyFactory;
import net.gy.SwiftFrameWork.MVVM.Interface.ICallBack;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by gy939 on 2016/9/12.
 */
public class HttpInjectFieldHandler implements IInjectFieldHandler {

    @Override
    public void inject(Field field, Object object, Annotation annotation) throws Exception {
        Class interType = field.getType();
        Class conClazz = object.getClass();
        String[] fieldsName = null;
        if (annotation instanceof InjectHttpService){
            InjectHttpService httpService = (InjectHttpService) annotation;
            fieldsName = httpService.value();
        }else if (annotation instanceof InjectController){
            InjectController httpService = (InjectController) annotation;
            fieldsName = httpService.dependFields();
        }else {
            throw new IllegalArgumentException("注解类型错误");
        }
        HttpProxyFactory.ExtCall extCall = HttpProxyFactory.With(interType);
        if (fieldsName != null) {
            for (String fieldName:fieldsName){
                Field tarField = null;
                Object v = null;
                try {
                    tarField = conClazz.getDeclaredField(fieldName);
                    tarField.setAccessible(true);
                    v = tarField.get(object);
                } catch (Exception e) {
                    continue;
                }
                if (v instanceof View){
                    extCall.setViewContent((View) v);
                }else if (v instanceof Activity){
                    extCall.setViewContent((Activity) v);
                }else if (v instanceof ICallBack){
                    extCall.setCallBack((ICallBack) v);
                }
            }
        }
        Object proxy = extCall.establish();
        field.set(object,proxy);
    }

}
