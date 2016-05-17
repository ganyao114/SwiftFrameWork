package net.gy.SwiftFrameWork.IOC.Model.local.database;

import android.database.Cursor;

import java.util.List;

/**
 * Created by gy on 2015/11/5.
 */
public interface IDBinterIoc {
    public void createTab(Class<?> clazz);
    public void insert(Object info,String...fields);
    public void delete(Object info,String... fields);
    public void update(Object info,String... fields);
    public Cursor get(Object info, String... fields);
    public List getList(Object info,String... fields);
}
