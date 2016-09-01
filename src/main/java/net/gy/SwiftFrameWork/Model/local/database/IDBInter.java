package net.gy.SwiftFrameWork.Model.local.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by gy on 2016/1/23.
 */
public interface IDBInter {
    public void exeSQL(String commond,Object[] args);
    public Cursor query(String commond, Object[] args,SQLiteDatabase db);
    public void createTab(String tabName,String... args);
}
