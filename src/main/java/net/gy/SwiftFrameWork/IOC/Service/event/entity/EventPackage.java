package net.gy.SwiftFrameWork.IOC.Service.event.entity;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * Created by gy on 2015/11/24.
 */
public final class EventPackage {

    private WeakReference<Object> register;
    private EventThreadType mode;
    private Method method;
    private Class<?> type;

    public WeakReference<Object> getRegister() {
        return register;
    }

    public void setRegister(WeakReference<Object> register) {
        this.register = register;
    }

    public EventThreadType getMode() {
        return mode;
    }

    public void setMode(EventThreadType mode) {
        this.mode = mode;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
