package net.gy.SwiftFrameWork.UI.view.recyclerview.viewholder;

import android.view.View;

/**
 * Created by gy on 2016/4/11.
 */
public interface IRcViewHolder {
    public <T extends View> T getView(int ViewId);
}
