package net.gy.SwiftFrameWork.Service.sensormonitor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by gy on 2016/2/25.
 */
public class SenserActionProcessor implements SensorEventListener{

    private static final String TAG = "gy-Senser";
    private ISenserActionCallBack callBack;
    private long lastshaketime = 0;
    private long shakeInter = 1000;
    private float last_x = 0;
    private float last_y = 0;
    private float last_z = 0;

    public SenserActionProcessor(ISenserActionCallBack callBack,Context context) {
        this.callBack = callBack;
        init(context);
    }

    private void init(Context context){
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;

        float tmpx = values[0];
        float tmpy = values[1];
        float tmpz = values[2];

        float x = tmpx - last_x;
        float y = tmpy - last_y;
        float z = tmpz - last_z;

        last_x = tmpx;
        last_y = tmpy;
        last_z = tmpz;

        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            int value = 13;//摇一摇阀值,不同手机能达到的最大值不同,如某品牌手机只能达到20
            if (x >= value || x <= -value || y >= value || y <= -value || z >= value || z <= -value) {

                if (System.currentTimeMillis() - lastshaketime > shakeInter || lastshaketime == 0){
                    Log.i(TAG, "检测到摇动");
                    callBack.onPhoneShaked();
                    if (x < -5){
                        callBack.onPhoneShakeLeft();
                    }else if (x > 5){
                        callBack.onPhoneShakeRight();
                    }
                    lastshaketime = System.currentTimeMillis();
                }
            }

        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void setShakeInter(long shakeInter) {
        this.shakeInter = shakeInter;
    }
}
