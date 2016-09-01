package net.gy.SwiftFrameWork.Model.local.database.commond;

/**
 * Created by gy on 2016/1/23.
 */
public interface IDBFilter {
    public IDBFilter where(String columnName, String op, Object value);
    public IDBFilter and(String columnName, String op, Object value);
    public IDBFilter or(String columnName, String op, Object value);
    public IOrderBy orderby(String columnName, boolean desc);
    interface IOrderBy{
        public String orderby(String columnName, boolean desc);
    }
}
