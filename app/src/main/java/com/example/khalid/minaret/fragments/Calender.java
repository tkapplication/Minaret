package com.example.khalid.minaret.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khalid.minaret.R;
import com.example.khalid.minaret.models.CalenderModel;
import com.example.khalid.minaret.utils.Database;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;

import static com.example.khalid.minaret.MainActivity.title;

/**
 * Created by khalid on 1/20/2018.
 */

public class Calender extends Fragment {
    MaterialCalendarView materialCalendarView;
    RecyclerView recyclerView;
    ArrayList<CalenderModel> calendarModes;
    Database database;

    public static Calender newInstance() {

        Calender calender = new Calender();
        return calender;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calender, container, false);
        calendarModes = new ArrayList<>();
        database = new Database(getActivity());
        title.setText("التقويم");
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        materialCalendarView = view.findViewById(R.id.calendarView);
        materialCalendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return true;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(Color.WHITE));

            }
        });
        calendarModes = database.getCalender();
        materialCalendarView.setWeekDayTextAppearance(Color.parseColor("#ffffff"));

        return view;
    }


}
