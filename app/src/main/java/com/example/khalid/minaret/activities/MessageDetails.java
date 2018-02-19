package com.example.khalid.minaret.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.khalid.minaret.R;

import static com.example.khalid.minaret.services.MyFCMService.notificationManager;

/**
 * Created by khalid on 1/20/2018.
 */

public class MessageDetails extends AppCompatActivity {

    TextView message, title;
    String titlet, messaget;
    Bundle bundle;

    public static MessageDetails newInstance() {

        MessageDetails messageDetails = new MessageDetails();
        return messageDetails;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (notificationManager != null)
            notificationManager.cancelAll();
        setContentView(R.layout.message_details);
        message = findViewById(R.id.message);
        title = findViewById(R.id.title);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            try {
                titlet = bundle.getString("title");
                messaget = bundle.getString("message");

            } catch (NullPointerException e) {
                titlet = "";
                messaget = "";
            }

        }

        title.setText(titlet);
        message.setText(messaget);
    }


}
