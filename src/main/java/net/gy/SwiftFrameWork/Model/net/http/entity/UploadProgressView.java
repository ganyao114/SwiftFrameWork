package net.gy.SwiftFrameWork.Model.net.http.entity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import net.gy.SwiftFrameWork.Model.net.http.IUploadCallBack;

/**
 * Created by gy on 2016/2/28.
 */
public class UploadProgressView implements IUploadCallBack{

    public int i = 0;
    private ProgressDialog dialog;
    private Handler handler;

    public void setup(Context context){
        if (dialog == null) {
            handler = new Handler(context.getMainLooper());
            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("上传文档...");
            dialog.setCancelable(false);
        }
    }

    public void onStart(){
        if (dialog != null)
        handler.post(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }

    @Override
    public void onProgress(UploadProgress progress) {
        dialog.setProgress(progress.getPersent());
    }

    public void onComplete(){
        if (dialog.isShowing())
        handler.post(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });
    }

}
