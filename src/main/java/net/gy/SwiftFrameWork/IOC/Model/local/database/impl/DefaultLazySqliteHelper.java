package net.gy.SwiftFrameWork.IOC.Model.local.database.impl;

import android.database.Cursor;

import net.gy.SwiftFrameWork.IOC.Core.entity.ClassMemberPackage;
import net.gy.SwiftFrameWork.IOC.Core.parase.ClassMemberParase;
import net.gy.SwiftFrameWork.IOC.Model.local.database.IDBinterIoc;
import net.gy.SwiftFrameWork.Model.local.database.IDBInter;

import java.util.List;

/**
 * Created by gy on 2016/1/19.
 */
public class DefaultLazySqliteHelper implements IDBinterIoc {

    private IDBInter dbProxy;

    private static String SQL_CREAT;
    private static String SQL_DROP;

    @Override
    public void createTab(Class clazz) {
        //Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            List<ClassMemberPackage> list = ClassMemberParase.getInstance().getFileds(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
