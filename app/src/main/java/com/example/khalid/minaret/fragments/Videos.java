package com.example.khalid.minaret.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.khalid.minaret.R;
import com.example.khalid.minaret.Video;
import com.example.khalid.minaret.VideoAdapter;
import com.example.khalid.minaret.YoutubeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.khalid.minaret.MainActivity.title;

/**
 * Created by khalid on 1/15/2018.
 */

public class Videos extends Fragment {
    public static String Youtube_API_Key = "AIzaSyCZAXRteh95_V3uR0em9nrxprCKH6cC0NY";
    String IDPlayList = "UU29hqNLs8sTsLOhv06na11A";
    ArrayList<Video> mangvideoyoutube;
    VideoAdapter videoAdapter;
    ListView listView;

    public static Videos newInstance() {
        Videos news = new Videos();
        return news;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main2, container, false);

        listView = view.findViewById(R.id.listview);
        title.setText("الفيديو");


        mangvideoyoutube = new ArrayList<>();
        videoAdapter = new VideoAdapter(getActivity(), mangvideoyoutube);
        listView.setAdapter(videoAdapter);
        getVideoYoutube(IDPlayList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), YoutubeActivity.class);
                intent.putExtra("resourceid", mangvideoyoutube.get(i).getUrlVideo());
                startActivity(intent);
            }
        });
        return view;
    }

    private void getVideoYoutube(String idPlayList) {
        String url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+idPlayList+"&key="+Youtube_API_Key+"&maxResults=50";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for (int i = 0 ; i<jsonArray.length();i++){
                        JSONObject jsonObjectitem = jsonArray.getJSONObject(i);

                        JSONObject jsonObjectsnippet = jsonObjectitem.getJSONObject("snippet");
                        String title = jsonObjectsnippet.getString("title");

                        JSONObject jsonObjectthumnail = jsonObjectsnippet.getJSONObject("thumbnails");
                        JSONObject jsonObjectdefault = jsonObjectthumnail.getJSONObject("default");
                        String urlimage = jsonObjectdefault.getString("url");

                        JSONObject jsonObjectresourceId = jsonObjectsnippet.getJSONObject("resourceId");
                        String resourceid = jsonObjectresourceId.getString("videoId");

                        mangvideoyoutube.add(new Video(i,title,urlimage,resourceid));
                    }
                    videoAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
