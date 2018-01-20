package com.example.khalid.minaret.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalid.minaret.R;

/**
 * Created by khalid on 1/20/2018.
 */

public class MessageDetails extends Fragment {

    TextView message, title;
    String titlet, messaget;
    Bundle bundle;

    public static MessageDetails newInstance() {

        MessageDetails messageDetails = new MessageDetails();
        return messageDetails;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_details, container, false);
        message = view.findViewById(R.id.message);
        title = view.findViewById(R.id.title);
        bundle = getArguments();
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

        return view;
    }
}
