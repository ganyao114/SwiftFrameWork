package net.gy.SwiftFrameWork.MVVM.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话工厂
 * Created by pc on 16/9/1.
 */
public class SessionFactory {

    public static Map<String,Session> sessionMap = new ConcurrentHashMap<>();

    public static void addSession(String key,Session session){
        sessionMap.put(key, session);
    }

    public static Session getSession(String key){
        return sessionMap.get(key);
    }

    public static Session remove(String key){
        return sessionMap.remove(key);
    }

    public static class Session{

        private Map<String,String> headers;
        private Map<String,String> pars;

        public Session() {
        }

        public Session(Map<String, String> headers, Map<String, String> pars) {
            this.headers = headers;
            this.pars = pars;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        public Map<String, String> getPars() {
            return pars;
        }

        public void setPars(Map<String, String> pars) {
            this.pars = pars;
        }
    }

}
