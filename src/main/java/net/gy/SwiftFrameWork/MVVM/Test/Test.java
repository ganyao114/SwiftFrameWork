package net.gy.SwiftFrameWork.MVVM.Test;

import android.util.Log;


import net.gy.SwiftFrameWork.MVVM.Entity.JsonTree;
import net.gy.SwiftFrameWork.MVVM.Impl.JsonParse;
import net.gy.SwiftFrameWork.MVVM.ProxyHandler.DynamicHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by pc on 16/8/29.
 */
public class Test {

    public String json = "{\n" +
            "    \"name\": \"gy\",\n" +
            "    \"pass\": \"dadw\",\n" +
            "    \"obj\": {\n" +
            "        \"name\": \"gy\"\n" +
            "    }\n" +
            "}";

    public void test(){
        Method[] methods = Test.class.getDeclaredMethods();

        DynamicHandler handler = new DynamicHandler(new Test());
        handler.addMethod("login", methods[0]);
        Object bussnessproxy = Proxy.newProxyInstance(ILogin.class.getClassLoader(), new Class<?>[]{ILogin.class}, handler);
        ILogin login = (ILogin) bussnessproxy;
        login.login("gy","login","par3");
    }

    public Object dosth(Method invoker,Object[] pars){
        Log.e(pars[0].toString(),invoker.getName()+pars[1].toString()+pars[2].toString());
        return null;
    }

    public JsonTree getTree(){
        return JsonParse.establish(TestPojo.class);
    }




}
