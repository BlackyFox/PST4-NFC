package com.example.pierre.testbdd4;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pierre.testbdd4.BDDFolder.MyBDD;
import com.example.pierre.testbdd4.Objects.People;

public class UserHomeActivity extends Activity {

    public TextView textView_test1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        textView_test1 = (TextView) findViewById(R.id.user_home_textView_test1);

        String username = "";
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            username = bundle.getString("username");
        }

        textView_test1.setText(username);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
