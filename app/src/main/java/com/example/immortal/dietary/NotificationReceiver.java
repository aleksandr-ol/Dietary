package com.example.immortal.dietary;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;


public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        int id = intent.getIntExtra("id", 0);
//        String text = intent.getStringExtra("text");

        Log.d("myLog", "onReceive");

        Toast.makeText(context, title, Toast.LENGTH_LONG).show();

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title)
//                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setCategory(Notification.CATEGORY_STATUS)
                .build();

        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(id, notification);
    }
}
