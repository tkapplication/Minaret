package com.example.khalid.minaret.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khalid.minaret.R;
import com.example.khalid.minaret.models.Post;

import java.util.ArrayList;

/**
 * Created by khalid-vibes on 5/29/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    ArrayList<Post> list;


    public NewsAdapter(Context context, ArrayList<Post> list) {
        this.list = list;
        this.context = context;

    }


    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getContent());
        holder.comment_count.setText(list.get(position).getComment_count());
        holder.love_count.setText(list.get(position).getFavorite_count());
        holder.date.setText(list.get(position).getDate());

        Glide.with(context)
                .load(list.get(position).getImage())
                .override(300, 250)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.image);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, content, comment_count, love_count;

        ImageView love, love_border, image;

        public ViewHolder(View view) {

            super(view);
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
            date = view.findViewById(R.id.date);
            love = view.findViewById(R.id.love);
            love_border = view.findViewById(R.id.loveborder);
            content = view.findViewById(R.id.content);
            comment_count = view.findViewById(R.id.commentcount);
            love_count = view.findViewById(R.id.lovecount);


            love_border.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    love.setVisibility(View.VISIBLE);
                    love_border.setVisibility(View.GONE);
                }
            });

            love.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    love.setVisibility(View.GONE);
                    love_border.setVisibility(View.VISIBLE);
                }
            });
        }
    }

}

