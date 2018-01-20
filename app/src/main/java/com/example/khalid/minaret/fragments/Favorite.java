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

import com.example.khalid.minaret.Adapter.NewsAdapter;
import com.example.khalid.minaret.R;
import com.example.khalid.minaret.models.Post;
import com.example.khalid.minaret.utils.Database;

import java.util.ArrayList;

import static com.example.khalid.minaret.MainActivity.title;

/**
 * Created by khalid on 1/20/2018.
 */

public class Favorite extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Post> posts;
    Database database;


    public static Favorite newInstance() {
        Favorite favorite = new Favorite();
        return favorite;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite, container, false);
        title.setText("المقالات المفضلة");

        recyclerView = view.findViewById(R.id.recycler);
        database = new Database(getActivity());
        posts = database.getFavorite();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new NewsAdapter(getActivity(), posts));
        return view;
    }
}
