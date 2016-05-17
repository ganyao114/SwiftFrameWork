package net.gy.SwiftFrameWork.Model.local.database.sqlite.commond;

import net.gy.SwiftFrameWork.Model.local.database.commond.IDBCommondFactory;

/**
 * Created by gy on 2016/1/19.
 */
public class SQLiteCommondFactory implements IDBCommondFactory {

    private static IDBCommondFactory factory;

    public static IDBCommondFactory getInstance(){
        synchronized (SQLiteCommondFactory.class) {
            if (factory == null)
                factory = new SQLiteCommondFactory();
        }
        return factory;
    }

    @Override
    public String createTabCommond(String tabname, String... args) {
        StringBuilder commond = new StringBuilder("create table " + tabname);
        commond.append('(');
        int len = args.length;
        for (int i = 0;i < len;i++){
            commond.append(args[i]);
            if (i!=len - 1)
                commond.append(',');
        }
        commond.append(')');
        return commond.toString();
    }

    @Override
    public String insertCommond(String tabname, String... args) {
        StringBuilder commond = new StringBuilder("insert into " + tabname);
        commond.append('(');
        int len = args.length;
        for (int i = 0;i < len;i++){
            commond.append(args[i]);
            if (i!=len - 1)
                commond.append(',');
        }
        commond.append(") values(");
        for (int i = 0;i < len;i++){
            commond.append('?');
            if (i!=len - 1)
                commond.append(',');
        }
        commond.append(')');
        return commond.toString();
    }

    @Override
    public String deleteCommond(String tabname, String... args) {
        StringBuilder commond = new StringBuilder("delete from " + tabname);
        commond.append(" where ");
        int len = args.length;
        for (int i = 0;i < len;i++){
            commond.append(args[i]);
            if (i!=len - 1)
                commond.append(',');
        }
        commond.append(") values(");
        for (int i = 0;i < len;i++){
            commond.append('?');
            if (i!=len - 1)
                commond.append(',');
        }
        commond.append(')');
        return commond.toString();
    }

    @Override
    public String updateCommond(String tabname, String... args) {
        return null;
    }

    @Override
    public String queryCommond(String tabname, String... args) {
        return null;
    }
}
