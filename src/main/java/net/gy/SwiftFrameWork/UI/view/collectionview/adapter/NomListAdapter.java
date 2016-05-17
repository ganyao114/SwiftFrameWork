package net.gy.SwiftFrameWork.UI.view.collectionview.adapter;

import android.content.Context;

import net.gy.SwiftFrameWork.IOC.UI.view.viewbinder.entity.BinderPackage;
import net.gy.SwiftFrameWork.UI.view.collectionview.IAdapterCallBack;
import net.gy.SwiftFrameWork.UI.view.collectionview.viewholder.IViewHolder;

import java.util.List;

/**
 * Created by gy on 2015/11/13.
 */
public class NomListAdapter<T> extends CommonAdapter {

    private IAdapterCallBack callBack;
    private BinderPackage binder;

    public NomListAdapter(Context context, List list, int layoutid, IAdapterCallBack callBack) {
        super(context, list, layoutid);
        this.callBack = callBack;
    }

    @Override
    protected void getMyview(IViewHolder holder, int position) {
        callBack.adapterCall(holder, position);
    }

    public void setList(List<T> list){
        this.list = list;
    }

    public List<T> getList(){
        return this.list;
    }

    public IAdapterCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(IAdapterCallBack callBack) {
        this.callBack = callBack;
    }

    public BinderPackage getBinder() {
        return binder;
    }

    public void setBinder(BinderPackage binder) {
        this.binder = binder;
    }
}
