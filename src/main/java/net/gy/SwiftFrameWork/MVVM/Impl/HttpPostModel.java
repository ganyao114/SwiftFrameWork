package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Entity.ParField;
import net.gy.SwiftFrameWork.MVVM.Exception.HttpServiceException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pc on 16/8/30.
 */
public class HttpPostModel extends BaseHttpModel{

    @Override
    public Object dohttp() throws Exception{

        HttpClient client = HttpClientFactory.GetHttpClient();
        HttpPost post = new HttpPost(parLocal.get().getUrl());

        List<NameValuePair> parameters = new ArrayList<NameValuePair>();

        if (parLocal.get().getEntity() != null){
            post.setEntity(parLocal.get().getEntity());
        }else if (parLocal.get().getPars() != null) {
            for (Map.Entry<String, String> entity : parLocal.get().getPars().entrySet())
                parameters.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
        }
        post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
        if (parLocal.get().getHeaders() != null){
            for (Map.Entry<String, String> entity : parLocal.get().getHeaders().entrySet())
                post.addHeader(entity.getKey(),entity.getValue());
        }
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpServiceException("连接失败");
        }
        String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
        //	client.getConnectionManager().shutdown();//关闭连接，未验证
//        HttpClientFactory.CloseHttpClient(client, 20);
        if (result.isEmpty())
            throw new HttpServiceException("未返回数据");
        return result;
    }

}
