package com.example.khalid.minaret.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.khalid.minaret.R;

import java.util.ArrayList;

import static com.example.khalid.minaret.fragments.Images.imageView;

/**
 * Created by khalid on 12/30/2017.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    private Context context;
    ArrayList<String> list;


    public ImagesAdapter(Context context, ArrayList<String> list) {
        this.list = list;
        this.context = context;

    }


    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item, viewGroup, false);
        return new ImagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context)
                .load(list.get(position))
                .override(400, 400)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.image);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.imageView);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Glide.with(context)
                            .load(list.get(getAdapterPosition()))
                            .override(400, 400)
                            .placeholder(R.drawable.no_image)
                            .error(R.drawable.no_image)
                            .into(imageView);
                }
            });
        }
    }

}
