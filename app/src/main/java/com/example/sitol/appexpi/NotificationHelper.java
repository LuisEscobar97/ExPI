package com.example.sitol.appexpi;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String chaneL1ID="chanel1ID";
    public static final String chaneL1Name="Chanel 1";
    private NotificationManager nManager;
    public static final String chaneL2ID="chanel2ID";
    public static final String chaneL2Name="Chanel 2";
    public static final String chaneL3ID="chanel3ID";
    public static final String chaneL3Name="Chanel 3";


    public NotificationHelper(Context base) {
        super(base);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                createChanels();
            }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChanels(){

        NotificationChannel channel1= new NotificationChannel(chaneL1ID,chaneL1Name, NotificationManager.IMPORTANCE_LOW);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorBlanco);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel1);

        NotificationChannel channel2= new NotificationChannel(chaneL2ID,chaneL2Name, NotificationManager.IMPORTANCE_HIGH);
        channel2.enableLights(true);
        channel2.enableVibration(true);
        channel2.setLightColor(R.color.colorNegro);
        channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel2);

        NotificationChannel channel3= new NotificationChannel(chaneL3ID,chaneL3Name, NotificationManager.IMPORTANCE_HIGH);
        channel2.enableLights(true);
        channel2.enableVibration(true);
        channel2.setLightColor(R.color.colorNegro);
        channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel2);

        }
        public NotificationManager getManager(){
            if (nManager==null) {
                nManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }

        return nManager;
          }

          public NotificationCompat.Builder getChanelotification(String titulo,String mensaje){

                return  new NotificationCompat.Builder(getApplicationContext(),chaneL1ID)
                        .setContentTitle(titulo)
                        .setContentText(mensaje)
                        .setSmallIcon(R.drawable.ic_access_alarm);
          }
    public NotificationCompat.Builder getChanel2otification(String titulo,String mensaje){

        return  new NotificationCompat.Builder(getApplicationContext(),chaneL2ID)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setSmallIcon(R.drawable.ic_access_alarm);
    }
    public NotificationCompat.Builder getChanel3otification(String titulo,String mensaje){

        return  new NotificationCompat.Builder(getApplicationContext(),chaneL3ID)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setSmallIcon(R.drawable.ic_access_alarm);
    }
        }



