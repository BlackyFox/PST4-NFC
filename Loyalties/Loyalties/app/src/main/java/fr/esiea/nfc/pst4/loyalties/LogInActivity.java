package fr.esiea.nfc.pst4.loyalties;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import bdd.*;
import objects.*;


public class LogInActivity extends ActionBarActivity {

    public EditText editText_username = null;
    public EditText editText_password = null;
    public Button button_logIn = null;
    public TextView textView_wrongText = null;
    public Button button_signIn = null;

    File credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        credentials = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/userCredentials.berzerk");

        editText_username = (EditText) findViewById(R.id.home_editText_username);
        editText_password = (EditText) findViewById(R.id.home_editText_password);
        button_logIn = (Button) findViewById(R.id.home_button_logIn);
        textView_wrongText = (TextView) findViewById(R.id.home_textView_wrongText);
        button_signIn = (Button) findViewById(R.id.home_button_signIn);


        //recupérer les identifiants dans le fichier et si possible écrire les données dans les editext
        //si on se déconnecte, on efface le fichier

        File f = getFileStreamPath("userCredentials.berzerk");
        if(f.length() != 0){
            int l = (int) credentials.length();
            byte[] data = new byte[l];

            try{
                FileInputStream in = new FileInputStream(credentials);
                in.read(data);
                in.close();
            }catch (IOException e){
                e.printStackTrace();
            }
            String creds = new String(data);
//            StringTokenizer tok = new StringTokenizer(creds, "amoslexiii");
//
//            String username = tok.nextToken();
//            String pass = tok.nextToken();

            String[] temp = creds.split("amoslexiii");
            Log.d("TXT", creds);
            Log.d("USER", temp[0]);
            Log.d("PASS", temp[1]);

            editText_username.setText(temp[0]);
            editText_password.setText(temp[1]);
        }

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
                    intent = new Intent(this, MainActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    writeCreds(username, password);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
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

    private void writeCreds(String username, String password){
        File f = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/userCredentials.berzerk");
        if(f.length() > 0)
            f.delete();
        try{
            FileOutputStream os = new FileOutputStream(f);
            os.write(username.getBytes());
            os.write("amoslexiii".getBytes());
            os.write(password.getBytes());
            os.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
