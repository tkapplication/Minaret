package com.example.khalid.minaret.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.khalid.minaret.R;

import static com.example.khalid.minaret.MainActivity.title;

/**
 * Created by khalid on 12/30/2017.
 */

public class Contact extends Fragment {
    LinearLayout facebook;

    public static Contact newInstance() {
        Contact news = new Contact();
        return news;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact, container, false);
        title.setText("تواصل معنا");

        facebook = view.findViewById(R.id.facebooklayout);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openfacebook();

            }
        });
        return view;
    }

    public void openfacebook() {

        Intent intent = null;
        try {
            // get the Twitter app if possible
            getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/715134628661972"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/MINARET.MENA/"));
        }
        this.startActivity(intent);
    }

    public void opentwitter(View view) {

//        Intent intent = null;
//        try {
//            // get the Twitter app if possible
//            getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
//            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=USERID"));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        } catch (Exception e) {
//            // no Twitter app, revert to browser
//            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/roo7_app"));
//        }
//        this.startActivity(intent);
    }



}
