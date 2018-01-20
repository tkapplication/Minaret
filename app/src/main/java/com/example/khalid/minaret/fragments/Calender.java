package com.example.khalid.minaret.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khalid.minaret.R;

import static com.example.khalid.minaret.MainActivity.title;

/**
 * Created by khalid on 1/20/2018.
 */

public class Calender extends Fragment {

    public static Calender newInstance() {

        Calender calender = new Calender();
        return calender;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calender, container, false);
        title.setText("التقويم");

        return view;
    }
}
