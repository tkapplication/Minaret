package com.example.khalid.minaret.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.khalid.minaret.Adapter.ImagesAdapter;
import com.example.khalid.minaret.R;
import com.example.khalid.minaret.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.khalid.minaret.activities.MainActivity.title;

/**
 * Created by khalid on 12/30/2017.
 */

public class Images extends Fragment {
    public static ImageView imageView;
    Context context;
    ArrayList<String> images;
    ImagesAdapter imagesAdapter;
    ProgressBar progressBar;
    ImageView scroll_left, scroll_right;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;

    public static Images newInstance() {
        Images news = new Images();
        return news;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.images, container, false);
        title.setText("الصور");
        progressBar = view.findViewById(R.id.progressBar);
        scroll_left = view.findViewById(R.id.scrollleft);
        scroll_right = view.findViewById(R.id.scrollright);
        context = getActivity();
        recyclerView = view.findViewById(R.id.recycler);
        imageView = view.findViewById(R.id.imageView);
        gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        images = new ArrayList<>();
        imagesAdapter = new ImagesAdapter(context, images);
        getImages();
        scroll_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scroll_right.setVisibility(View.VISIBLE);
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                if (totalItemCount <= 0) return;
                int lastVisibleItemIndex = gridLayoutManager.findLastVisibleItemPosition();

                if (lastVisibleItemIndex >= totalItemCount) return;
                gridLayoutManager.smoothScrollToPosition(recyclerView, null, lastVisibleItemIndex + 1);
                if (lastVisibleItemIndex == totalItemCount - 2)
                    scroll_left.setVisibility(View.GONE);

            }
        });
        scroll_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.getLayoutManager().scrollToPosition(gridLayoutManager.findFirstVisibleItemPosition() - 1);

                scroll_left.setVisibility(View.VISIBLE);
                int FirstVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();

                if (FirstVisibleItemPosition == 5)
                    scroll_right.setVisibility(View.GONE);
            }
        });
        return view;
    }

    private void getImages() {

        String url = Utils.base_url + "api/get_post/?id=42";

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("post");
                            JSONArray jsonArray = jsonObject1.getJSONArray("attachments");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                images.add(jsonArray.getJSONObject(i).getString("url"));
                            }
                            Glide.with(context)
                                    .load(images.get(0))
                                    .override(350, 350)
                                    .placeholder(R.drawable.no_image)
                                    .error(R.drawable.no_image)
                                    .into(imageView);
                            recyclerView.setAdapter(imagesAdapter);
                            progressBar.setVisibility(View.GONE);
                            scroll_left.setVisibility(View.VISIBLE);
                            //  scroll_right.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

            }
        });
        Volley.newRequestQueue(context).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
