package net.gy.SwiftFrameWork.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import net.gy.SwiftFrameWork.Core.S;

import java.util.UUID;

/**
 * Created by pc on 16/8/9.
 */
public class DeviceID {

    public static String getDeviceId(Context context) {


        StringBuilder deviceId = new StringBuilder();
// 渠道标志
        deviceId.append("a");

        try {

//wifi mac地址
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String wifiMac = info.getMacAddress();
            if(!isEmpty(wifiMac)){
                deviceId.append("wifi");
                deviceId.append(wifiMac);
                return deviceId.toString();
            }

//IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if(!isEmpty(imei)){
                deviceId.append("imei");
                deviceId.append(imei);
                return deviceId.toString();
            }

//序列号（sn）
            String sn = tm.getSimSerialNumber();
            if(!isEmpty(sn)){
                deviceId.append("sn");
                deviceId.append(sn);
                return deviceId.toString();
            }

//如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if(!isEmpty(uuid)){
                deviceId.append("id");
                deviceId.append(uuid);
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(getUUID(context));
        }


        return deviceId.toString();
    }


    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context){
        String uuid = null;
        MyUUID myUUID = new MyUUID();
        S.SharePrefrence.Get(context,myUUID);
        if(isEmpty(myUUID.getUuid())){
            uuid = UUID.randomUUID().toString();
            myUUID.setUuid(uuid);
            S.SharePrefrence.Save(myUUID);
        }
        return myUUID.getUuid();
    }

    private static boolean isEmpty(String s){
        if(s==null) {
            return true;
        }
        if(s.equals("")) {
            return true;
        }
        if(s.length()==0) {
            return true;
        }
        return false;
    }

}
