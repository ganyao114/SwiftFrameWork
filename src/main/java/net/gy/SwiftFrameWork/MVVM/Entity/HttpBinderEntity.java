package net.gy.SwiftFrameWork.MVVM.Entity;

import net.gy.SwiftFrameWork.MVVM.Annotations.HttpSrcMethod;
import net.gy.SwiftFrameWork.Model.entity.Entry;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by pc on 16/8/29.
 */
public class HttpBinderEntity {

    private Entry<ParType,String>[] pars;
    private HttpSrcMethod control;

    public Entry<ParType, String>[] getPars() {
        return pars;
    }

    public void setPars(Entry<ParType, String>[] pars) {
        this.pars = pars;
    }

    public HttpSrcMethod getControl() {
        return control;
    }

    public void setControl(HttpSrcMethod control) {
        this.control = control;
    }
}
