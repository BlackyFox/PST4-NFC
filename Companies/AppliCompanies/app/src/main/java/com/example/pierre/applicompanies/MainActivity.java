package com.example.pierre.applicompanies;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pierre.applicompanies.library_http.AsyncHttpClient;
import com.example.pierre.applicompanies.library_http.AsyncHttpResponseHandler;
import com.example.pierre.applicompanies.library_http.RequestParams;
import com.example.pierre.applicompanies.objectsPackage.Company;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView title = (TextView) findViewById(R.id.home_company_name);

        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        String name = getIntent().getStringExtra("name");
        String logo = getIntent().getStringExtra("logo");
        String card = getIntent().getStringExtra("card");
        String up_date = getIntent().getStringExtra("up_date");

        title.setText(name);

        company = new Company(id, name, logo, card);
        company.setUp_date(up_date);
    }

    // Fonction réagissant à l'appui sur l'un des boutons (log, sign in)
    public void toDo(View v) {
        switch(v.getId()) {
            case R.id.see_clients:
            {
                Intent intent = new Intent(this, SeeClientsActivity.class);
                intent.putExtra("id", Integer.toString(company.getId()));
                intent.putExtra("name", company.getName());
                intent.putExtra("logo", company.getLogo());
                intent.putExtra("card", company.getCard());
                intent.putExtra("up_date", company.getUp_date());
                startActivity(intent);
                break;
            }
            case R.id.add_client:
            {
                Intent intent = new Intent(this, AddClientActivity.class);
                intent.putExtra("id", Integer.toString(company.getId()));
                intent.putExtra("name", company.getName());
                intent.putExtra("logo", company.getLogo());
                intent.putExtra("card", company.getCard());
                intent.putExtra("up_date", company.getUp_date());
                startActivity(intent);
                break;
            }
            case R.id.see_reductions:
            {
                Intent intent = new Intent(this, SeeReductionsActivity.class);
                intent.putExtra("id", Integer.toString(company.getId()));
                intent.putExtra("name", company.getName());
                intent.putExtra("logo", company.getLogo());
                intent.putExtra("card", company.getCard());
                intent.putExtra("up_date", company.getUp_date());
                startActivity(intent);
                break;
            }
            case R.id.add_reduction:
            {
                Intent intent = new Intent(this, AddReductionActivity.class);
                intent.putExtra("id", Integer.toString(company.getId()));
                intent.putExtra("name", company.getName());
                intent.putExtra("logo", company.getLogo());
                intent.putExtra("card", company.getCard());
                intent.putExtra("up_date", company.getUp_date());
                startActivity(intent);
                break;
            }
            case R.id.add_offer:
            {
                Intent intent = new Intent(this, AddOfferActivity.class);
                intent.putExtra("id", Integer.toString(company.getId()));
                intent.putExtra("name", company.getName());
                intent.putExtra("logo", company.getLogo());
                intent.putExtra("card", company.getCard());
                intent.putExtra("up_date", company.getUp_date());
                startActivity(intent);
                break;
            }
            case R.id.remove_offer:
            {
                Intent intent = new Intent(this, RemoveOfferActivity.class);
                intent.putExtra("id", Integer.toString(company.getId()));
                intent.putExtra("name", company.getName());
                intent.putExtra("logo", company.getLogo());
                intent.putExtra("card", company.getCard());
                intent.putExtra("up_date", company.getUp_date());
                startActivity(intent);
                break;
            }
            case R.id.create_card:
            {
                Intent intent = new Intent(this, CreateCardActivity.class);
                intent.putExtra("id", Integer.toString(company.getId()));
                intent.putExtra("name", company.getName());
                intent.putExtra("logo", company.getLogo());
                intent.putExtra("card", company.getCard());
                intent.putExtra("up_date", company.getUp_date());
                startActivity(intent);
                break;
            }
            default: {}
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
