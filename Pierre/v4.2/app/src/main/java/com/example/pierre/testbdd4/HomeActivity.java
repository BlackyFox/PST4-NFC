package com.example.pierre.testbdd4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pierre.testbdd4.BDDFolder.*;
import com.example.pierre.testbdd4.Objects.*;


public class HomeActivity extends Activity {

    public EditText editText_username = null;
    public EditText editText_password = null;
    public Button button_logIn = null;
    public TextView textView_wrongText = null;
    public Button button_signIn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        editText_username = (EditText) findViewById(R.id.home_editText_username);
        editText_password = (EditText) findViewById(R.id.home_editText_password);
        button_logIn = (Button) findViewById(R.id.home_button_logIn);
        textView_wrongText = (TextView) findViewById(R.id.home_textView_wrongText);
        button_signIn = (Button) findViewById(R.id.home_button_signIn);
    }

    public void toDo(View v) {
        String username = editText_username.getText().toString();
        String password = editText_password.getText().toString();

        Intent intent;

        switch(v.getId()) {
            case R.id.home_button_logIn:
            {
                textView_wrongText.setText("");

                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(username == null || username.equals("")) { textView_wrongText.setText("No username."); break; }
                if(!bdd.doesPeopleAlreadyExists(username)) { textView_wrongText.setText("This username doesn't exists !\nRegister yourself !"); break; }
                if(password == null || password.equals("")) { textView_wrongText.setText("No password."); break; }

                People peop = bdd.getPeopleWithUsername(username);
                String correct_password = peop.getPassword();
                String role = peop.getRole();

                bdd.close();

                if(correct_password.equals(password)) {
                    if(role.equals("A")) {
                        intent = new Intent(this, AdminHomeActivity.class);
                        startActivity(intent);
                    }
                    else if (role.equals("U")) {
                        intent = new Intent(this, UserHomeActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }
                }
                else { textView_wrongText.setText("Wrong password."); break; }

                break;
            }
            case R.id.home_button_signIn:
            {
                intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                break;
            }
            default: {}
        }
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
