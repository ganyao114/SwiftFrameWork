package net.gy.SwiftFrameWork.MVVM.Impl;


import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class HttpClientFactory {
    public static HttpClient client;
    private static boolean unusing = true;
    private static Integer timeout = 3*1000;

    public HttpClientFactory() {
        // TODO Auto-generated constructor stub

    }

    public static HttpClient GetHttpClient() {
        unusing = false;
        if (client == null) {
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            HttpConnectionParams.setConnectionTimeout(params, timeout);
            HttpConnectionParams.setSoTimeout(params, timeout);
            SchemeRegistry schreg = new SchemeRegistry();
            schreg.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            schreg.register(new Scheme("https", PlainSocketFactory
                    .getSocketFactory(), 80));
            ClientConnectionManager coman = new ThreadSafeClientConnManager(
                    params, schreg);

            client = new DefaultHttpClient(coman, params);

        }
        return client;
    }

    public static void CloseHttpClient(HttpClient mclient, final int timemile) {
        unusing = true;
        new Thread(new Runnable() {

            @SuppressWarnings("static-access")
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.currentThread().sleep(timemile * 1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (unusing && client != null) {
                    client.getConnectionManager().shutdown();
                    client = null;
                }
            }
        }).start();
    }

    public static Integer getTimeout() {
        return timeout;
    }

    public static void setTimeout(Integer timeout) {
        HttpClientFactory.timeout = timeout;
    }
}
