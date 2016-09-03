package net.gy.SwiftFrameWork.utils;

import net.gy.SwiftFrameWork.IOC.Model.local.shareprefrence.annotation.SPColumeName;
import net.gy.SwiftFrameWork.IOC.Model.local.shareprefrence.annotation.SPOptions;

/**
 * Created by pc on 16/8/9.
 */
@SPOptions(name = "uuid")
public class MyUUID {
    @SPColumeName("uuid")
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
