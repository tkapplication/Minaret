package com.example.khalid.minaret.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khalid.minaret.R;
import com.example.khalid.minaret.activities.CalenderDetails;
import com.example.khalid.minaret.activities.MessageDetails;
import com.example.khalid.minaret.models.CalenderModel;
import com.example.khalid.minaret.models.Message;
import com.example.khalid.minaret.utils.Database;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.example.khalid.minaret.utils.Utils.base_url;
import static com.example.khalid.minaret.utils.Utils.html2text;


public class MyFCMService extends FirebaseMessagingService {

    public static NotificationManager notificationManager;
    public static int NOTID;
    String title = "", message = "", type = "", event_id = "";
    Database database;
    ArrayList<CalenderModel> calendarModes;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        database = new Database(getApplicationContext());
        type = remoteMessage.getData().get("type");
        calendarModes = new ArrayList<>();
        if (type.equals("event")) {
            event_id = remoteMessage.getData().get("event_id");
            getCalender(event_id);
        } else if (type.equals("message")) {

            title = remoteMessage.getData().get("title");
            message = remoteMessage.getData().get("message");

            sendMessageNotification(title, message);
            database.addMessage(new Message(title, message));
        }


    }


    private void sendMessageNotification(String title, String messageBody) {
        NOTID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        Intent intent = new Intent(this, MessageDetails.class);
        intent.putExtra("title", title);
        intent.putExtra("message", messageBody);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("لديك رسالة جديدة")
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

    private void sendEventNotification(CalenderModel calenderModel) {
        NOTID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        Intent intent = new Intent(this, CalenderDetails.class);
        intent.putExtra("title", calenderModel.getTitle());
        intent.putExtra("description", calenderModel.getDescription());
        intent.putExtra("start", calenderModel.getStart_date());
        intent.putExtra("end", calenderModel.getEnd_date());
        intent.putExtra("address", calenderModel.getAddress());

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("لديك حدث جديد")
                .setContentText(html2text(calenderModel.getTitle()))
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

    private void getCalender(String event_id) {
        String url = base_url + "wp-json/tribe/events/v1/events/" + event_id;
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String id = "", title = "", description = "", start = "", end = "", address = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            id = jsonObject.getString("id");
                            title = jsonObject.getString("title");
                            description = jsonObject.getString("description");
                            start = jsonObject.getString("start_date");
                            end = jsonObject.getString("end_date");
                            address = jsonObject.getJSONObject("venue").getString("address") + "  "
                                    + jsonObject.getJSONObject("venue").getString("city") + "  " +
                                    jsonObject.getJSONObject("venue").getString("country");
                            calendarModes.add(new CalenderModel(id, title, html2text(description), start, end, address));
                            database.addCalender(calendarModes.get(0));
                            sendEventNotification(calendarModes.get(0));
                        } catch (JSONException e) {
                            calendarModes.add(new CalenderModel(id, title, html2text(description), start, end, address));
                            database.addCalender(calendarModes.get(0));
                            sendEventNotification(calendarModes.get(0));
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

}
