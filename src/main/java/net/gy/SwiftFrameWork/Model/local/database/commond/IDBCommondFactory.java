package net.gy.SwiftFrameWork.Model.local.database.commond;

/**
 * Created by gy on 2016/1/19.
 */
public interface IDBCommondFactory {
    public String createTabCommond(String tabname,String... args);
    public String insertCommond(String tabname,String... args);
    public String deleteCommond(String tabname,String... args);
    public String updateCommond(String tabname,String... args);
    public String queryCommond(String tabname,String... args);
}
