package net.gy.SwiftFrameWork.IOC.Core.parase;

import android.app.Application;

import net.gy.SwiftFrameWork.IOC.Core.kernel.KernelClass;
import net.gy.SwiftFrameWork.IOC.Core.kernel.KernelObject;
import net.gy.SwiftFrameWork.IOC.Core.kernel.KernelString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gy on 2015/11/19.
 */
public class ResourceFactory {

    private static Class<?> R_Resouce_Class = null;
    private static final Map<String, Object> Type_Map_Resouce_Class = new HashMap<String, Object>();
    /**
     * ID_NONE
     */
    public final static int ID_NONE = -1;

    /**
     * ID_ZERO
     */
    public final static int ID_ZERO = 0;

    public static void setRclass(Application application) {
        if (R_Resouce_Class == null)
            R_Resouce_Class = KernelClass.forName(application.getPackageName() + ".R");
    }

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

    public static int getResouceId(int id, String type, String name) {
        if (id == ID_NONE) {
            Integer integer = getResouceId(type, name);
            id = integer == null ? ID_ZERO : integer;
        }

        return id;
    }

}
