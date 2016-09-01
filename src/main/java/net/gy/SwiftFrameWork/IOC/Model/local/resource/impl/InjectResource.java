package net.gy.SwiftFrameWork.IOC.Model.local.resource.impl;

import net.gy.SwiftFrameWork.IOC.Core.kernel.KernelClass;
import net.gy.SwiftFrameWork.IOC.Core.kernel.KernelObject;
import net.gy.SwiftFrameWork.IOC.Core.kernel.KernelString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gy on 2016/3/10.
 */
public class InjectResource {
    /** ID_NONE */
    public final static int ID_NONE = -1;

    /** ID_ZERO */
    public final static int ID_ZERO = 0;

    /** 资源文件类 */
    private static Class<?> R_Resouce_Class = null;

    /** Type_Map_Resouce_Class */
    private static final Map<String, Object> Type_Map_Resouce_Class = new HashMap<String, Object>();
    public static Integer getResouceId(String type, String name) {
        Object resouce = Type_Map_Resouce_Class.get(type);
        if (resouce == null) {
            synchronized (type) {
                resouce = Type_Map_Resouce_Class.get(type);
                if (resouce == null) {
                    resouce = KernelClass.forName(R_Resouce_Class.getName() + "$" + KernelString.capitalize(type));
                }
                if (resouce != null) {
                    Type_Map_Resouce_Class.put(type, resouce);
                }
            }
        }
        return resouce == null ? null : (Integer) KernelObject.get(resouce, name);
    }

    /**
     * @param id
     * @param type
     * @param name
     * @return
     */
    public static int getResouceId(int id, String type, String name) {
        if (id == ID_NONE) {
            Integer integer = getResouceId(type, name);
            id = integer == null ? ID_ZERO : integer;
        }

        return id;
    }

}
