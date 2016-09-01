package net.gy.SwiftFrameWork.IOC.Model.local.database.entity;

import net.gy.SwiftFrameWork.Model.local.database.sqlite.entity.DBValueInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gy on 2016/1/23.
 */
public class DBColumes {

    private Map<String,DBValueInfo> map;

    public DBColumes() {
        map = new HashMap<String,DBValueInfo>();
    }
}
