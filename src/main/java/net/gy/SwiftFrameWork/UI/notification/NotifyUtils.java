package net.gy.SwiftFrameWork.UI.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import net.gy.SwiftFrameWork.R;

/**
 * Created by gy on 2016/5/5.
 */
public class NotifyUtils {
    public final static int NOTIFICATION_ID = "NotificationService".hashCode();
    public static void Show(Context context, String title, String content){
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        int smallIconId = R.drawable.ic_launcher;
        Bitmap largeIcon = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
        String info = content;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("host://anotheractivity"));
        builder.setLargeIcon(largeIcon)
                .setSmallIcon(smallIconId)
                .setContentTitle(title)
                .setContentText(info)
                .setTicker(info)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));
        final Notification n = builder.getNotification();
        nm.notify(NOTIFICATION_ID,n);
    }
}
