package com.example.pierre.testbdd3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GetInfoActivity extends Activity implements View.OnClickListener {

    public Button tmp_button1 = null;
    public Button tmp_button2 = null;
    public Button tmp_button3 = null;
    public Button tmp_button4 = null;
    private TextView tmp_result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getinfo);

        tmp_button1 = (Button) findViewById(R.id.buttshow1);
        tmp_button2 = (Button) findViewById(R.id.buttshow2);
        tmp_button3 = (Button) findViewById(R.id.buttshow3);
        tmp_button4 = (Button) findViewById(R.id.buttshow4);
        tmp_result = (TextView) findViewById(R.id.show);

        tmp_button1.setOnClickListener(this);
        tmp_button2.setOnClickListener(this);
        tmp_button3.setOnClickListener(this);
        tmp_button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        MyBDD bdd = new MyBDD(this);
        bdd.open();

        switch(v.getId()) {
            case R.id.buttshow1:
            {
                tmp_result.setText(bdd.showCompany());
                break;
            }
            case R.id.buttshow2:
            {
                tmp_result.setText(bdd.showPeople());
                break;
            }
            case R.id.buttshow3:
            {
                tmp_result.setText(bdd.showReduction());
                break;
            }
            case R.id.buttshow4:
            {
                tmp_result.setText(bdd.showLink());
                break;
            }
            default: {}
        }

        bdd.close();
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
