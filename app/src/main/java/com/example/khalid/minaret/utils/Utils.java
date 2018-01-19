package com.example.khalid.minaret.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import org.jsoup.Jsoup;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by khalid on 12/22/2017.
 */

public class Utils {
    public static String base_url = "https://minaretapp.com/";

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    public static void save(Context context, String key, String value) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String get(Context context, String key) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(key, "");
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;


    }

    public static String encodeString(String query) {

        try {
            return query = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {

            return query;
        }
    }
}
