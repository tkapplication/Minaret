package com.example.khalid.minaret.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalid.minaret.MainActivity;
import com.example.khalid.minaret.R;
import com.example.khalid.minaret.fragments.MessageDetails;
import com.example.khalid.minaret.models.Message;

import java.util.ArrayList;

/**
 * Created by khalid on 1/16/2018.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    ArrayList<Message> list;
    FragmentManager fragmentManager;
    private Context context;

    public MessagesAdapter(Context context, ArrayList<Message> list) {
        this.list = list;
        this.context = context;
        fragmentManager = ((MainActivity) context).getSupportFragmentManager();

    }


    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item, viewGroup, false);
        return new MessagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.ViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.message.setText(list.get(position).getMessage());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, message;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            message = view.findViewById(R.id.message);

            cardView = view.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MessageDetails messageDetails = new MessageDetails();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", list.get(getAdapterPosition()).getTitle());
                    bundle.putString("message", list.get(getAdapterPosition()).getMessage());
                    messageDetails.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack("messages");
                    fragmentTransaction.add(R.id.content, messageDetails).commit();
                }
            });
        }
    }


}

