package net.gy.SwiftFrameWork.Service.loader.downloader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gy on 2015/11/9.
 */
public class DwDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download.db";
    private static final int VERSION = 1;
    private static final String SQL_CREAT = "create table thread_info(_id integer primary key autoincrement," +
            "thread_id integer,url text,start integer,end integer,finished integer)";
    private static final String SQL_DROP = "drop table if exits thread_info";

    public DwDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
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


}
