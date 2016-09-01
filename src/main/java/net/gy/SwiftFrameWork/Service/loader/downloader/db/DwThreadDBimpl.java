package net.gy.SwiftFrameWork.Service.loader.downloader.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.gy.SwiftFrameWork.Service.loader.downloader.IDownloadDB;
import net.gy.SwiftFrameWork.Service.loader.downloader.entity.DwThreadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gy on 2015/11/9.
 */
public class DwThreadDBimpl implements IDownloadDB {

    private DwDBHelper dbHelper;

    public DwThreadDBimpl(Context context) {
        this.dbHelper = new DwDBHelper(context);
    }

    @Override
    public void insertThread(DwThreadInfo info) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?)",
                new Object[]{info.getId(), info.getUrl(), info.getStart(), info.getEnd(), info.getFinished()});
        db.close();
    }

    @Override
    public void deleteThread(DwThreadInfo info) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url = ? and thread_id = ?",
                new Object[]{info.getUrl(), info.getId()});
        db.close();
    }

    @Override
    public void updateThread(DwThreadInfo info, int progress) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?",
                new Object[]{info.getFinished(), info.getUrl(), info.getId()});
        db.close();
    }

    @Override
    public List<DwThreadInfo> getThreads(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{url});
        List<DwThreadInfo> list = new ArrayList<DwThreadInfo>();
        while (cursor.moveToNext()) {
            DwThreadInfo info = new DwThreadInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            info.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            info.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            info.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            info.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(info);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url, thread_id + ""});
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }
}
