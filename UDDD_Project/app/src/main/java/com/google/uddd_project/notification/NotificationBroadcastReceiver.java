package com.google.uddd_project.notification;

import static android.os.Build.VERSION_CODES.O;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.uddd_project.R;

import java.util.Random;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    private static final int notification_five = 105;
    private NotificationHelper notificationHelper;

    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= O) {
            this.notificationHelper = new NotificationHelper(context);
        }
        ComponentName componentName = ((ActivityManager.RunningTaskInfo) ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(1).get(0)).topActivity;
        StringBuilder sb = new StringBuilder();
        sb.append("package_name");
        sb.append(context.getPackageName());
        Log.d("package_name", sb.toString());
        String[] stringArray = context.getResources().getStringArray(R.array.notification_text);
        String str = stringArray[new Random().nextInt(stringArray.length)];
        if (componentName.getPackageName().equals(context.getPackageName())) {
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
            return;
        }
        int currentTimeMillis = (int) System.currentTimeMillis();
//        Intent intent2 = new Intent(context, Splash.class);
//        intent2.setFlags(603979776);
//        PendingIntent activity = PendingIntent.getActivity(context, currentTimeMillis, intent2, 0);
//        Uri defaultUri = RingtoneManager.getDefaultUri(2);
//        if (Build.VERSION.SDK_INT >= 26) {
//            postNotification(105, context.getString(R.string.app_name), str, activity);
//            return;
//        }
//        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, new Notification.Builder(context)
//                .setContentTitle(context.getString(R.string.app_name)).setContentText(str).setSmallIcon(R.drawable.logo_noti)
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo1)).setSound(defaultUri).setContentIntent(activity).setAutoCancel(true).build());
    }

    public void postNotification(int i, String str, String str2, PendingIntent pendingIntent) {
        Notification.Builder weeklyNotification = i != 105 ? null : this.notificationHelper.getWeeklyNotification(str, str2, pendingIntent);
        if (weeklyNotification != null) {
            this.notificationHelper.notify(i, weeklyNotification);
        }
    }
}
