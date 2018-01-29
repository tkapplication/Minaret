package com.example.khalid.minaret.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khalid.minaret.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import static com.example.khalid.minaret.MainActivity.title;

/**
 * Created by khalid on 1/20/2018.
 */

public class Calender extends Fragment {
    MaterialCalendarView materialCalendarView;

    public static Calender newInstance() {

        Calender calender = new Calender();
        return calender;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calender, container, false);
        title.setText("التقويم");
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
        materialCalendarView.setWeekDayTextAppearance(Color.parseColor("#ffffff"));
        return view;
    }
}
