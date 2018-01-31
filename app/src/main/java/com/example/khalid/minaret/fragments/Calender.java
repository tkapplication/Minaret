package com.example.khalid.minaret.fragments;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khalid.minaret.Adapter.CalenderAdapter;
import com.example.khalid.minaret.R;
import com.example.khalid.minaret.models.CalenderModel;
import com.example.khalid.minaret.utils.Database;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.khalid.minaret.MainActivity.title;
import static com.example.khalid.minaret.utils.Utils.base_url;
import static com.example.khalid.minaret.utils.Utils.html2text;

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

        calendarModes = database.getCalender();
        materialCalendarView.setWeekDayTextAppearance(Color.parseColor("#ffffff"));
        getCalender();
        return view;
    }

    private void getCalender() {
        String url = base_url + "wp-json/tribe/events/v1/events";
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String id = "", title = "", description = "", start = "", end = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("events");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                id = jsonArray.getJSONObject(i).getString("id");
                                title = jsonArray.getJSONObject(i).getString("title");
                                description = jsonArray.getJSONObject(i).getString("description");
                                start = jsonArray.getJSONObject(i).getString("start_date");
                                end = jsonArray.getJSONObject(i).getString("end_date");

                                calendarModes.add(new CalenderModel(id, title, html2text(description), start, end));

                            }
                            recyclerView.setAdapter(new CalenderAdapter(getActivity(), calendarModes));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

}
