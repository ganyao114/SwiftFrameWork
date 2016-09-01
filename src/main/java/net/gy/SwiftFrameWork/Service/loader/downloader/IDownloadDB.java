package net.gy.SwiftFrameWork.Service.loader.downloader;

import net.gy.SwiftFrameWork.Service.loader.downloader.entity.DwThreadInfo;

import java.util.List;

/**
 * Created by gy on 2015/11/9.
 */
public interface IDownloadDB {
    public void insertThread(DwThreadInfo info);

    public void deleteThread(DwThreadInfo info);

    public void updateThread(DwThreadInfo info, int progress);

    public List<DwThreadInfo> getThreads(String url);

    public boolean isExists(String url, int thread_id);
}
