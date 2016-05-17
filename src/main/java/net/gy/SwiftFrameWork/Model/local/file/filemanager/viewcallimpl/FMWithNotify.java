package net.gy.SwiftFrameWork.Model.local.file.filemanager.viewcallimpl;

import android.content.Context;

import net.gy.SwiftFrameWork.Model.local.file.filemanager.IFileManagerCallback;
import net.gy.SwiftFrameWork.Model.local.file.filemanager.entity.TmpFileInfo;

/**
 * Created by gy on 2015/11/7.
 */
public class FMWithNotify implements IFileManagerCallback {

    private Context mContext;

    public FMWithNotify(Context context){
        mContext = context;
    }

    @Override
    public void onFileOpDeration(TmpFileInfo info) {
        switch (info.getOperateType()){
            case COPY_FILE:

                break;
            case COPY_FOLDER:

                break;
        }
    }

    @Override
    public void onCompelet(TmpFileInfo info) {
        switch (info.getOperateType()){
            case COPY_FILE:

                break;
            case COPY_FOLDER:

                break;
        }
    }
}
