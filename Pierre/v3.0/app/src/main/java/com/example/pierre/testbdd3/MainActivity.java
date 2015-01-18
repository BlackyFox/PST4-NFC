package com.example.pierre.testbdd3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    public Button b1 = null;
    public Button b2 = null;
    public Button b3 = null;
    public Button b4 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.butt_bdd_client);
        b2 = (Button) findViewById(R.id.butt_bdd_company);
        b3 = (Button) findViewById(R.id.butt_bdd_info);
        b4 = (Button) findViewById(R.id.butt_bdd_reset);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch(v.getId()) {
            case R.id.butt_bdd_client:
            {
                intent = new Intent(this, ClientActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.butt_bdd_company:
            {
                intent = new Intent(this, CompanyActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.butt_bdd_info:
            {
                intent = new Intent(this, GetInfoActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.butt_bdd_reset:
            {
                MyBDD tmpBDD = new MyBDD(this);
                tmpBDD.open();
                tmpBDD.reset();
                tmpBDD.close();
                break;
            }
            default: {}
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
