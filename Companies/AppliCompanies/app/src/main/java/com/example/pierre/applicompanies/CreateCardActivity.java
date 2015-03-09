package com.example.pierre.applicompanies;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Created by Pierre on 05/03/2015.
 */
public class CreateCardActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //setContentView(R.layout.activity_main_activity2);

        } else {
            //setContentView(R.layout.activity_main_activity2);
        }
    }
}
