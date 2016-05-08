package com.ice1000.alpd;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import data.Poster;
import util.DownloadingActivity;

public class MainActivity extends DownloadingActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout images;
    private View[] views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFuncs();
        v("init finished.");
        size = 100;
     }

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(views == null) views = new View[images.getChildCount()];
        for(int i = 0; i < images.getChildCount(); i++) {
            views[i] = images.getChildAt(i);
        }
    }

    private void initViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        images = (LinearLayout) findViewById(R.id.images);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_upload:
                toast("this function is still in developing!");
                break;
            case R.id.nav_downloads:
                Intent intent = new Intent(
                        Intent.ACTION_VIEW
                );
                intent.setDataAndType(
                        Uri.fromFile(
                                new File(SAVE_PATH)
                        ),
                        "image/*"
                );
                startActivity(intent);
                break;
            case R.id.nav_settings:
                go2Settings(true);
                break;
            case R.id.nav_share:
                toast("this function is still in developing");
                break;
            default:

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                go2Settings(true);
                return true;
            case R.id.action_refresh:
                haveNew = true;
                refresh();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void refresh(){
        toast("refreshing");
        images.removeAllViews();
        v("size = " + size);
        if(views == null) {
            for(int i = 1; i < size && haveNew; i++){
//        while(Tools.exists(String.format(Locale.CHINESE, MAIN_URL, i))){
                v("downloading " + i);
                getImage(i, true);
            }
        }
        else {
            for (View view : views) {
                images.addView(view);
            }
        }
    }

    @Override
    protected void addView(final Poster poster) {
        byte[] data = poster.bytes;
        LinearLayout linearLayout = (LinearLayout)
                LayoutInflater.from(MainActivity.this).inflate(
                        R.layout.image_meta,
                        null
                );
        TextView textView = (TextView)
                linearLayout.findViewById(R.id.text_source);
        ImageView imageView = (ImageView)
                linearLayout.findViewById(R.id.image_source);
        imageView.setImageBitmap(
                BitmapFactory.decodeByteArray(
                        data,
                        0,
                        data.length
                )
        );
        textView.setText(R.string.default_info);
        textView.append("\nposter No." + poster.cnt);
        linearLayout.setOnClickListener(v -> {
            toast("loading big poster...");
            Intent intent = new Intent(
                    MainActivity.this,
                    ImageViewActivity.class
            );
            intent.putExtra(NUMBER, poster.cnt);
            startActivity(intent);
        });
        images.addView(linearLayout);
    }
}
