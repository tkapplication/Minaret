package com.example.khalid.minaret;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.khalid.minaret.fragments.Calender;
import com.example.khalid.minaret.fragments.Contact;
import com.example.khalid.minaret.fragments.Favorite;
import com.example.khalid.minaret.fragments.Images;
import com.example.khalid.minaret.fragments.Messages;
import com.example.khalid.minaret.fragments.News;
import com.example.khalid.minaret.fragments.Platform;
import com.example.khalid.minaret.fragments.Settings;
import com.example.khalid.minaret.fragments.Videos;

import static com.example.khalid.minaret.utils.Utils.save;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static TextView title;
    FragmentManager fragmentManager;
    FrameLayout content;
    NavigationView navigationView;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        title = toolbar.findViewById(R.id.toolbar_title);
        title.setText("Minaret");
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        content = findViewById(R.id.content);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bundle = getIntent().getExtras();

        fragmentManager = getSupportFragmentManager();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//        if (bundle != null) {
//
//            final FragmentTransaction transaction4 = fragmentManager.beginTransaction();
//            transaction4.replace(R.id.content, Messages.newInstance()).commit();
//        }
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("news");
        transaction4.replace(R.id.content, News.newInstance()).commit();
        navigationView.setCheckedItem(R.id.news);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            int count = fragmentManager.getBackStackEntryCount();
            if (count <= 1) {
                fragmentManager.popBackStack();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                String name = fragmentManager.getBackStackEntryAt(count - 2).getName();
                switch (name) {
                    case "news":
                        title.setText("الاخبار");
                        navigationView.setCheckedItem(R.id.news);
                        break;
                    case "images":
                        title.setText("الصور");
                        navigationView.setCheckedItem(R.id.images);

                        break;
                    case "contact":
                        title.setText("تواصل معنا");
                        navigationView.setCheckedItem(R.id.contact);

                        break;
                    case "platform":
                        title.setText("المنصة");
                        navigationView.setCheckedItem(R.id.platform);

                        break;
                    case "videos":
                        title.setText("الفيديو");
                        navigationView.setCheckedItem(R.id.videos);

                        break;
                    case "messages":
                        title.setText("الرسائل");
                        navigationView.setCheckedItem(R.id.message);

                        break;
                    case "settings":
                        title.setText("الاعدادات");
                        navigationView.setCheckedItem(R.id.settings);

                        break;
                    case "favorite":
                        title.setText("المقالات المفضلة");
                        navigationView.setCheckedItem(R.id.favorite);

                        break;
                    case "calendar":
                        title.setText("التقويم");
                        navigationView.setCheckedItem(R.id.calender);

                        break;

                }
            }
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.news) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("news");
            transaction4.add(R.id.content, News.newInstance()).commit();
        } else if (id == R.id.contact) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("contact");
            transaction4.add(R.id.content, Contact.newInstance()).commit();
        } else if (id == R.id.images) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("images");
            transaction4.add(R.id.content, Images.newInstance()).commit();
        } else if (id == R.id.videos) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("videos");
            transaction4.add(R.id.content, Videos.newInstance()).commit();
        } else if (id == R.id.settings) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("settings");
            transaction4.add(R.id.content, Settings.newInstance()).commit();
        } else if (id == R.id.message) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("messages");
            transaction4.add(R.id.content, Messages.newInstance()).commit();
        } else if (id == R.id.favorite) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("favorite");
            transaction4.add(R.id.content, Favorite.newInstance()).commit();
        } else if (id == R.id.calender) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("calendar");
            transaction4.add(R.id.content, Calender.newInstance()).commit();
        } else if (id == R.id.logout) {
            Intent i = new Intent(MainActivity.this, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            startActivity(i);
            save(getApplicationContext(), "login", "no");
        } else if (id == R.id.platform) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("platform");
            transaction4.add(R.id.content, Platform.newInstance()).commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.RIGHT);
        return true;
    }


}
