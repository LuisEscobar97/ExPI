package com.example.sitol.appexpi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper= new NotificationHelper(context);
        NotificationCompat.Builder nb= notificationHelper.getChanel2otification("Alarma","Ya es hora");
        notificationHelper.getManager().notify(2,nb.build());
    }
}
