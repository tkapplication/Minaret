package com.example.khalid.minaret.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.example.khalid.minaret.MainActivity;
import com.example.khalid.minaret.R;
import com.example.khalid.minaret.models.Message;
import com.example.khalid.minaret.utils.Database;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;


public class MyFCMService extends FirebaseMessagingService {

    String title = "", message = "";
    NotificationManager notificationManager;
    public static int NOTID;
    Database database;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        database = new Database(getApplicationContext());
        title = remoteMessage.getData().get("title");
        message = remoteMessage.getData().get("message");
        sendNotification(title, message);
        database.addMessage(new Message(title, message));

    }


    private void sendNotification(String title, String messageBody) {
        NOTID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

//        if (get(getApplicationContext(), "notification_sound").equals("")) {
//            notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
//        } else
//
//            notificationBuilder.setSound(Uri.parse(get(getApplicationContext(), "notification_sound")));
//
//        if (!get(getApplicationContext(), "notification_vibration").equals("") || get(getApplicationContext(), "notification_vibration").equals("yes")) {
//
//            notificationBuilder.setVibrate(new long[]{1000, 1000});
//        }
//        switch (get(getApplicationContext(), "notification_light")) {
//
//            case "1":
//                notificationBuilder.setLights(Color.RED, 500, 500);
//
//                break;
//            case "2":
//                notificationBuilder.setLights(Color.WHITE, 500, 500);
//
//                break;
//            case "3":
//                notificationBuilder.setLights(Color.GREEN, 500, 500);
//
//                break;
//            case "4":
//                notificationBuilder.setLights(Color.YELLOW, 500, 500);
//
//                break;
//            case "5":
//                notificationBuilder.setLights(Color.BLUE, 500, 500);
//
//                break;
//            case "6":
//                notificationBuilder.setLights(Color.CYAN, 500, 500);
//
//                break;

        // }


        notificationManager.notify(NOTID /* ID of notification */, notificationBuilder.build());
    }


}
