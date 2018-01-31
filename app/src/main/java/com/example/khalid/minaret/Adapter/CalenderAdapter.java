package com.example.khalid.minaret.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalid.minaret.R;
import com.example.khalid.minaret.models.CalenderModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by khalid on 1/29/2018.
 */

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {
    ArrayList<CalenderModel> list;
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Context context;

    public CalenderAdapter(Context context, ArrayList<CalenderModel> list) {
        this.list = list;
        this.context = context;

    }


    @Override
    public CalenderAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calender_item, viewGroup, false);
        return new CalenderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalenderAdapter.ViewHolder holder, final int position) {
        Date date_day = null, date = null;
        try {
            date_day = fmt.parse(list.get(position).getStart_date());
            date = fmt.parse(list.get(position).getStart_date());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        String day = (String) DateFormat.format("dd", date_day); // 20

        holder.content.setText(list.get(position).getDescription());
        holder.date.setText(dayOfTheWeek);
        holder.day.setText(day);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content, date, day;

        public ViewHolder(View view) {
            super(view);
            content = view.findViewById(R.id.content);
            date = view.findViewById(R.id.date);
            day = view.findViewById(R.id.day);

        }
    }


}


