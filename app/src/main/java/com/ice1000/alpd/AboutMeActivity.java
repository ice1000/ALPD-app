package com.ice1000.alpd;

import android.os.Bundle;
import android.app.ActionBar;
import android.view.View;

import util.BaseActivity;

public class AboutMeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ActionBar actionBar = getActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void openIceGithub(View view){
        openWeb("https://github.com/ice1000");
    }
    public void openWeiGithub(View view){
        openWeb("https://github.com/iXinwei");
    }
    public void openHeGithub(View view){
        openWeb("https://github.com/hthclyde");
    }
    public void openCWOJ(View view) {
        openWeb("https://github.com/CDFLS/CWOJ");
    }
    public void openAppRaw(View view) {
        openWeb("https://coding.net/u/ice1000/p/App-raw/git");
    }
}
