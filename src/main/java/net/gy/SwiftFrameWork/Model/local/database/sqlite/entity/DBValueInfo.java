package net.gy.SwiftFrameWork.Model.local.database.sqlite.entity;

import net.gy.SwiftFrameWork.IOC.Model.local.database.entity.DBValueMode;

/**
 * Created by gy on 2016/1/22.
 */
public class DBValueInfo {
    private String name;
    private DBValueMode mode;
    private DBValueType type;
    private ValueType valueType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DBValueMode getMode() {
        return mode;
    }

    public void setMode(DBValueMode mode) {
        this.mode = mode;
    }

    public DBValueType getType() {
        return type;
    }

    public void setType(DBValueType type) {
        this.type = type;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }
}
