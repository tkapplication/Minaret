package com.example.khalid.minaret.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalid.minaret.Adapter.MessagesAdapter;
import com.example.khalid.minaret.R;
import com.example.khalid.minaret.models.Message;
import com.example.khalid.minaret.utils.Database;

import java.util.ArrayList;

import static com.example.khalid.minaret.activities.MainActivity.title;

/**
 * Created by khalid on 1/15/2018.
 */

public class Messages extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Message> messages;
    Database database;
    TextView nofavorite;

    public static Messages newInstance() {
        Messages news = new Messages();
        return news;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messages, container, false);
        title.setText("الرسائل");
        nofavorite = view.findViewById(R.id.nofavorite);

        recyclerView = view.findViewById(R.id.recycler);
        database = new Database(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        messages = database.getMessages();
        recyclerView.setAdapter(new MessagesAdapter(getActivity(), messages));
        if (messages.size() == 0) {
            nofavorite.setVisibility(View.VISIBLE);
        } else {
            nofavorite.setVisibility(View.GONE);

        }
        return view;
    }
}
