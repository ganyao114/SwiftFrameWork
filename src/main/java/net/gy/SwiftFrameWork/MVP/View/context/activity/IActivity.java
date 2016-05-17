package net.gy.SwiftFrameWork.MVP.View.context.activity;

import android.graphics.Bitmap;
import android.view.View;

import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnCreatedListener;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnDestoryListener;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnPauseListener;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnRestartListener;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnResumeListener;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnStartListener;
import net.gy.SwiftFrameWork.MVP.View.context.entity.ActivityOnStopListener;

/**
 * Created by gy on 2016/2/24.
 */
public interface IActivity {
    public  <T extends View> T getView(int ViewId);

    public void setText(int id,String str);

    public void setImg(int id, Bitmap bitmap);

    public void setOnCreateListener(ActivityOnCreatedListener listener);

    public void setOnDestroyListener(ActivityOnDestoryListener listener);

    public void setOnStartListener(ActivityOnStartListener listener);

    public void setOnRestartListener(ActivityOnRestartListener listener);

    public void setOnResumeListener(ActivityOnResumeListener listener);

    public void setOnStopListener(ActivityOnStopListener listener);

    public void setOnPauseListener(ActivityOnPauseListener listener);
}
