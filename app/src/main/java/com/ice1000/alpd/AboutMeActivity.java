package com.ice1000.alpd;

import android.os.Bundle;
import android.view.View;

import util.BaseActivity;

public class AboutMeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
    }

    public void back(View view){
        finish();
    }
}
