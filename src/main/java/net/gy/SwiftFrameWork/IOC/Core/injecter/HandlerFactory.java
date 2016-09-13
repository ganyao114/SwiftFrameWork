package net.gy.SwiftFrameWork.IOC.Core.injecter;

import net.gy.SwiftFrameWork.IOC.Service.thread.handler.IHandler;
import net.gy.SwiftFrameWork.utils.SyncMapProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gy939 on 2016/9/12.
 */
public class HandlerFactory {

    private static Map<Class<? extends IInjectHandler>,IInjectHandler> map =
            SyncMapProxy.SyncMap(new HashMap<Class<? extends IInjectHandler>, IInjectHandler>());

    public static IInjectHandler getHandler(Class<? extends IInjectHandler> type){
        IInjectHandler handler = map.get(type);
        if (handler == null){
            try {
                handler = type.newInstance();
                map.put(type,handler);
            } catch (Exception e) {
                return null;
            }
        }
        return handler;
    }

}
