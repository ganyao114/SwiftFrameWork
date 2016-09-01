package net.gy.SwiftFrameWork.Model.local.database.commond;

/**
 * Created by gy on 2016/3/7.
 */
public class DBFilter implements IDBFilter{

    private String commond;

    public DBFilter(Class clazz) {

    }

    public static IDBFilter from(Class clazz){
        return new DBFilter(clazz);
    }


    public IDBFilter where(String str) {
        return this;
    }


    @Override
    public IDBFilter where(String columnName, String op, Object value) {
        return this;
    }

    @Override
    public IDBFilter and(String columnName, String op, Object value) {
        return this;
    }

    @Override
    public IDBFilter or(String columnName, String op, Object value) {
        return this;
    }

    @Override
    public IOrderBy orderby(String columnName, boolean desc) {
        return null;
    }

    @Override
    public String toString() {
        return "DBFilter{" +
                "commond='" + commond + '\'' +
                '}';
    }

    protected class OrderBy implements IOrderBy{

        @Override
        public String orderby(String columnName, boolean desc) {
            return null;
        }
    }
}
