package net.gy.SwiftFrameWork.IOC.Core.injecter;

import net.gy.SwiftFrameWork.utils.SyncMapProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gy939 on 2016/9/12.
 */
public class HandlerFactory {

    private static Map<Class<? extends IInjectFieldHandler>,IInjectFieldHandler> map =
            SyncMapProxy.SyncMap(new HashMap<Class<? extends IInjectFieldHandler>, IInjectFieldHandler>());

    public static IInjectFieldHandler getHandler(Class<? extends IInjectFieldHandler> type){
        IInjectFieldHandler handler = map.get(type);
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
