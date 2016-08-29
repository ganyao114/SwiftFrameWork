package net.gy.SwiftFrameWork.MVVM.Entity;

import net.gy.SwiftFrameWork.MVVM.Annotations.HttpSrcMethod;
import net.gy.SwiftFrameWork.MVVM.Interface.IHttpModel;
import net.gy.SwiftFrameWork.MVVM.Interface.IMethodProxy;
import net.gy.SwiftFrameWork.Model.entity.Entry;

/**
 * Created by pc on 16/8/29.
 */
public class HttpMethodProxy implements IMethodProxy{

    private IHttpModel httpModel;
    private HttpBinderEntity binderEntity;

    public HttpMethodProxy(IHttpModel httpModel, HttpBinderEntity binderEntity) {
        this.httpModel = httpModel;
        this.binderEntity = binderEntity;
        init();
    }

    private void init() {
        HttpSrcMethod control = binderEntity.getControl();
        String url = control.url();
        if (!url.equals(""))
            httpModel.setUrl(url);

    }

    @Override
    public void donoret(Object... pars) {
        if (pars.length != binderEntity.getPars().length)
            throw new IllegalArgumentException("输入参数不匹配");
        //参数初始化
        for (int i = 0;i < pars.length ; i++){
            Entry<ParType,String> par = binderEntity.getPars()[i];
            String parvalue = pars[i].toString();
            switch (par.getKey()){
                case URL:
                    httpModel.setUrl(parvalue);
                    break;
                case PAR:
                    httpModel.addPar(par.getValue(),parvalue);
                    break;
                case HEADER:
                    httpModel.addHeader(par.getValue(),parvalue);
                    break;
            }
        }

        //
    }



    @Override
    public Object dohasret(Object... pars) {
        return null;
    }
}
