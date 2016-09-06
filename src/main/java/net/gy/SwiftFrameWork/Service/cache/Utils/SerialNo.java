package net.gy.SwiftFrameWork.Service.cache.Utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by gy on 2016/9/6.
 */
public class SerialNo {
    private static AtomicLong SerialContext = new AtomicLong(0);
    public static String getSerial(Object object){
        if (object == null)
            return null;
        long time = System.currentTimeMillis();
        String name = object.getClass().getName();
        int hash = object.hashCode();
        StringBuilder serial = new StringBuilder(name);
        serial.append("_");
        serial.append(hash);
        serial.append("_");
        serial.append(time);
        serial.append("_");
        serial.append(SerialContext.getAndIncrement());
        return serial.toString();
    }
}
