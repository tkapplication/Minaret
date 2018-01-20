package com.example.khalid.minaret.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khalid.minaret.R;

/**
 * Created by khalid on 1/20/2018.
 */

public class PostDetails extends Fragment {
    ImageView imageView;
    TextView date, title, content;
    Bundle bundle;
    String datet, titlet, contentt, imaget;
    Context context;

    public static PostDetails newInstance() {
        PostDetails postDetails = new PostDetails();
        return postDetails;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_details, container, false);
        context = getActivity();
        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        content = view.findViewById(R.id.content);
        date = view.findViewById(R.id.date);
        bundle = getArguments();
        if (bundle != null) {
            try {
                datet = bundle.getString("date");
                contentt = bundle.getString("content");
                imaget = bundle.getString("image");
                titlet = bundle.getString("title");


            } catch (NullPointerException e) {
                datet = "";
                contentt = "";
                imaget = "";
                titlet = "";
            }

        }
        date.setText(datet);
        content.setText(contentt);
        title.setText(titlet);

        Glide.with(context)
                .load(imaget)
                .override(300, 250)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(imageView);

        return view;
    }
}
