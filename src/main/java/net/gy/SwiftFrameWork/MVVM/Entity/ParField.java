package net.gy.SwiftFrameWork.MVVM.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pc on 16/8/30.
 */
public class ParField {

    private String url;
    private Map<String,String> pars;
    private Map<String,String> headers;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addPar(String key,String value){
        if (pars == null)
            pars = new HashMap<>();
        pars.put(key, value);
    }

    public void addHeader(String key,String value){
        if (headers == null)
            headers = new HashMap<>();
        headers.put(key, value);
    }

    public Map<String, String> getPars() {
        return pars;
    }

    public void setPars(Map<String, String> pars) {
        this.pars = pars;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
