package com.example.khalid.minaret.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalid.minaret.R;
import com.example.khalid.minaret.models.Comment;

import java.util.ArrayList;

/**
 * Created by khalid on 1/20/2018.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    ArrayList<Comment> list;
    private Context context;

    public CommentAdapter(Context context, ArrayList<Comment> list) {
        this.list = list;
        this.context = context;

    }


    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item, viewGroup, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, final int position) {
        holder.content.setText(list.get(position).getContent());
        holder.date.setText(list.get(position).getDate());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content, date;

        public ViewHolder(View view) {
            super(view);
            content = view.findViewById(R.id.content);
            date = view.findViewById(R.id.date);


        }
    }


}

