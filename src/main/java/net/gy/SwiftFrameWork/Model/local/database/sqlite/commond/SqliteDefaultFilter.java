package net.gy.SwiftFrameWork.Model.local.database.sqlite.commond;

import net.gy.SwiftFrameWork.Model.local.database.commond.IDBFilter;

/**
 * Created by gy on 2016/1/23.
 */
public class SqliteDefaultFilter implements IDBFilter {

    protected String tabName;

    private SqliteDefaultFilter(String tabName){
        tabName = tabName;
    }

    public IDBFilter from(String tabName) {
        return new SqliteDefaultFilter(tabName);
    }

    public IDBFilter where() {
        return null;
    }

    @Override
    public IDBFilter where(String columnName, String op, Object value) {
        return null;
    }

    @Override
    public IDBFilter and(String columnName, String op, Object value) {
        return null;
    }

    @Override
    public IDBFilter or(String columnName, String op, Object value) {
        return null;
    }

    @Override
    public IOrderBy orderby(String columnName, boolean desc) {
        return null;
    }

    class Where{


        class A{

        }
    }

}
