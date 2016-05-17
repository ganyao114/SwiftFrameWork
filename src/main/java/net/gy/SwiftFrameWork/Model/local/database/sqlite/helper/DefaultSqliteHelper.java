package net.gy.SwiftFrameWork.Model.local.database.sqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.gy.SwiftFrameWork.Model.local.database.IDBInter;

/**
 * Created by gy on 2016/1/23.
 */
public class DefaultSqliteHelper extends SQLiteOpenHelper implements IDBInter{

    private static String SQL_CREAT;
    private static String SQL_DROP;

    public DefaultSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
    public void exeSQL(String commond, Object[] args) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(commond,args);
        db.close();
    }

    @Override
    public Cursor query(String commond, Object[] args,SQLiteDatabase db) {
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery(commond, (String[]) args);
        return cursor;
    }

    @Override
    public void createTab(String tabName, String... args) {

    }
}
