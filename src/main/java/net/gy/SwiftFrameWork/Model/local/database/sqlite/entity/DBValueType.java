package net.gy.SwiftFrameWork.Model.local.database.sqlite.entity;

/**
 * Created by gy on 2016/1/22.
 */
public enum DBValueType {
    INTEGER("INTEGER"), REAL("REAL"), TEXT("TEXT"), BLOB("BLOB");

    private String value;

    DBValueType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
