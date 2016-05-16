package net.gy.SwiftFrameWork.Reactive.impl;

import net.gy.SwiftFrameWork.Reactive.entity.RunContextType;
import net.gy.SwiftFrameWork.Reactive.entity.actions.Func1;

import java.lang.reflect.Method;
import java.util.Vector;

/**
 * Created by pc on 16/5/15.
 */
public class MapProxy implements Func1{

    private Vector<Func1> funs;
    private Invoker invoker;
    private RunContextType runContext = RunContextType.CurrentThread;

    public MapProxy() {
        funs = new Vector<Func1>();
        invoker = Invoker.getInstance();
    }

    public void addFun1(Func1 func1){
        funs.add(func1);
    }

    public void rmFun1(Func1 func1){
        funs.remove(func1);
    }

    public void clear(){
        funs.clear();
    }

    public boolean isHasMap(){
        if (funs.size() == 0)
            return false;
        else
            return true;
    }

    @Override
    public Object call(Object i) {
        Object ret = i;
        String methodName = "call";
        for (Func1 func:funs){
            try {
                Method method = func.getClass().getMethod(methodName,Object.class);
                ret = invoker.invoke_direct(method,func,ret);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public void setRunContext(RunContextType runContext) {
        this.runContext = runContext;
    }
}
