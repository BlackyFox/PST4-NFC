package com.example.pierre.testbdd3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CompanyActivity extends Activity implements View.OnClickListener {

    private EditText tmp_name = null;
    public Button tmp_button = null;
    public Button tmp_button2 = null;
    private TextView tmp_result = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        tmp_name = (EditText) findViewById(R.id.company_name);
        tmp_button = (Button) findViewById(R.id.button2);
        tmp_button2 = (Button) findViewById(R.id.button3);
        tmp_result = (TextView) findViewById(R.id.result_company);

        tmp_button.setOnClickListener(this);
        tmp_button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = tmp_name.getText().toString();

        switch(v.getId()) {
            case R.id.button3:
            {
                if(name == null || name.equals("")) {
                    tmp_result.setText("No company ? Don't be shy !");
                    break;
                }

                MyBDD tmpBDD = new MyBDD(this);
                tmpBDD.open();

                if(tmpBDD.getCompanyWithName(name) != null) {
                    tmp_result.setText("Your company is already in the database !");
                    break;
                }
                else {
                    Company comp = new Company(name);
                    tmpBDD.insertCompany(comp);
                    tmp_result.setText("GOOD !");
                }

                break;
            }
            case R.id.button2:
            {
                tmp_name.setText("");
                tmp_result.setText("");

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
