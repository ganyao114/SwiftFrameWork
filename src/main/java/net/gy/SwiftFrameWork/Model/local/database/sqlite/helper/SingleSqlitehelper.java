package net.gy.SwiftFrameWork.Model.local.database.sqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.gy.SwiftFrameWork.IOC.Model.local.database.IDBinterIoc;

import java.util.List;

/**
 * Created by gy on 2015/11/10.
 * <p/>
 * 项目中单例使用
 */
public class SingleSqlitehelper extends SQLiteOpenHelper implements IDBinterIoc {

    private static SingleSqlitehelper baseSqlitehelper = null;

    private static String SQL_CREAT;
    private static String SQL_DROP;

    public static SingleSqlitehelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String create, String drop) {
        synchronized (SingleSqlitehelper.class) {
            if (baseSqlitehelper == null) {
                SQL_CREAT = create;
                SQL_DROP = drop;
                baseSqlitehelper = new SingleSqlitehelper(context, name, factory, version);
            }
        }
        return baseSqlitehelper;
    }

    public SingleSqlitehelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREAT);
    }

    @Override
    public void createTab(Class<?> clazz) {

    }

    @Override
    public void insert(Object info, String... fields) {

    }

    @Override
    public void delete(Object info, String... fields) {

    }

    @Override
    public void update(Object info, String... fields) {

    }

    @Override
    public Cursor get(Object info, String... fields) {
        return null;
    }

    @Override
    public List getList(Object info, String... fields) {
        return null;
    }
}
