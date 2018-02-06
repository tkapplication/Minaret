package com.example.khalid.minaret.Adapter;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khalid.minaret.OnItemClickListener;
import com.example.khalid.minaret.R;
import com.example.khalid.minaret.models.CalenderModel;
import com.example.khalid.minaret.services.MyBroadcastReceiver;
import com.example.khalid.minaret.utils.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by khalid on 1/29/2018.
 */

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {
    ArrayList<CalenderModel> list;
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Database database;
    ArrayList<String> positions;
    private Context context;
    private OnItemClickListener clickListener;

    public CalenderAdapter(Context context, ArrayList<CalenderModel> list) {
        this.list = list;
        this.context = context;
        database = new Database(context);
        Collections.reverse(list);
        positions = new ArrayList<>();
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
        try {
            String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
            String day = (String) DateFormat.format("dd", date_day); // 20

            holder.content.setText(list.get(position).getDescription());
            holder.date.setText(dayOfTheWeek);
            holder.day.setText(day);
        } catch (NullPointerException e) {

        }
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i).equals(position + ""))
                holder.tools.setVisibility(View.VISIBLE);
            else
                holder.tools.setVisibility(View.GONE);

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public void startAlert() {
        int i = 15;
        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), pendingIntent);
        Toast.makeText(context, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.date_dialog);
        DatePicker datePicker = dialog.findViewById(R.id.date);
        datePicker.ge
        dialog.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content, date, day;
        LinearLayout tools, linear;
        ImageView delete, clock, check;

        public ViewHolder(View view) {
            super(view);
            content = view.findViewById(R.id.content);
            date = view.findViewById(R.id.date);
            day = view.findViewById(R.id.day);
            tools = view.findViewById(R.id.tools);
            linear = view.findViewById(R.id.linear);
            delete = view.findViewById(R.id.delete);
            clock = view.findViewById(R.id.clock);
            check = view.findViewById(R.id.check);
            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) clickListener.onClick(view, getAdapterPosition());

                }
            });
            linear.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    tools.setVisibility(View.VISIBLE);
                    positions.add(getAdapterPosition() + "");
                    return true;
                }
            });
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tools.setVisibility(View.GONE);
                    positions.remove(getAdapterPosition() + "");

                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    positions.remove(getAdapterPosition() + "");

                    tools.setVisibility(View.GONE);
                    database.deleteCalender(list.get(getAdapterPosition()).getId());
                    list.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
            clock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDialog();
                }
            });
        }
    }
}


