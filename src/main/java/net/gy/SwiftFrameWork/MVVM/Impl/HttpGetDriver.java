package net.gy.SwiftFrameWork.MVVM.Impl;

import net.gy.SwiftFrameWork.MVVM.Exception.HttpServiceException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.Map;

/**
 * HttpGet实现
 * Created by pc on 16/8/30.
 */
public class HttpGetDriver extends BaseHttpDriver {

    @Override
    public Object dohttp() throws Exception{

        HttpClient client = HttpClientFactory.GetHttpClient();

        HttpGet get = new HttpGet(parLocal.get().getUrl());



        BasicHttpParams basicHttpParams = new BasicHttpParams();
        if (parLocal.get().getPars() != null) {
            for (Map.Entry<String, String> entity : parLocal.get().getPars().entrySet())
                basicHttpParams.setParameter(entity.getKey(), entity.getValue());
        }
        get.setParams(basicHttpParams);
        if (parLocal.get().getHeaders() != null){
            for (Map.Entry<String, String> entity : parLocal.get().getHeaders().entrySet())
                get.addHeader(entity.getKey(),entity.getValue());
        }
        HttpResponse response = client.execute(get);

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
