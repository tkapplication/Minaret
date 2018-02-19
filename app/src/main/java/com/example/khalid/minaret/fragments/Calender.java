package com.example.khalid.minaret.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khalid.minaret.Adapter.CalenderAdapter;
import com.example.khalid.minaret.OnItemClickListener;
import com.example.khalid.minaret.R;
import com.example.khalid.minaret.activities.CalenderDetails;
import com.example.khalid.minaret.models.CalenderModel;
import com.example.khalid.minaret.utils.Database;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.khalid.minaret.activities.MainActivity.title;

/**
 * Created by khalid on 1/20/2018.
 */

public class Calender extends Fragment implements OnItemClickListener {
    MaterialCalendarView materialCalendarView;
    RecyclerView recyclerView;
    ArrayList<CalenderModel> calendarModes;
    Database database;
    CalenderAdapter calenderAdapter;
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        calendarModes = database.getCalender();

        calenderAdapter = new CalenderAdapter(getActivity(), calendarModes);
        calenderAdapter.setClickListener(this);
        materialCalendarView.setWeekDayTextAppearance(Color.parseColor("#ffffff"));
        recyclerView.setAdapter(calenderAdapter);
        return view;
    }


    @Override
    public void onClick(View view, int position) {
        Date date = null;
        try {
            date = fmt.parse(calendarModes.get(position).getStart_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        materialCalendarView.setSelectedDate(date);
        Intent intent = new Intent(getActivity(), CalenderDetails.class);
        intent.putExtra("title", calendarModes.get(position).getTitle());
        intent.putExtra("description", calendarModes.get(position).getDescription());
        intent.putExtra("start", calendarModes.get(position).getStart_date());
        intent.putExtra("end", calendarModes.get(position).getEnd_date());
        intent.putExtra("address", calendarModes.get(position).getAddress());
        startActivity(intent);
    }


}
