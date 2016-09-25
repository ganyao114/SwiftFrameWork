package net.gy.SwiftFrameWork.MVVM.Impl;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.gy.SwiftFrameWork.MVVM.Exception.HttpServiceException;

import java.util.Map;

/**
 * Created by gy939 on 2016/9/25.
 */
public class HttpVolleyDriver extends BaseHttpDriver implements Response.ErrorListener,Response.Listener{
    @Override
    public Object dohttp() throws Exception {
        RequestFuture future = RequestFuture.newFuture();
        Request<String> request = new StringRequest(parLocal.get().getUrl(),this,this) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return parLocal.get().getHeaders();
            }

            @Override
            protected Map<String, String> getPostParams() throws AuthFailureError {
                return parLocal.get().getPars();
            }
        };
        future.setRequest(request);
        String res = (String) future.get();
        return res;
    }

    @Override
    public void onErrorResponse(VolleyError error){
    }

    @Override
    public void onResponse(Object response) {

    }
}
