package com.example.lefebvrb.iutshare;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("IUT Share");

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
    }

    public void login(View view){
        String login = ((EditText) findViewById(R.id.username_et)).getText().toString();
        String password = ((EditText) findViewById(R.id.password_et)).getText().toString();
        System.out.println("Login :\""+login+"\"\nPassword : \""+password+"\"");
        if(login.equals("") || password.equals(""))
            Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
         else {

            /*
            URL url;
            DataOutputStream printout;
            DataInputStream input;
            url = new URL (MainActivity.URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput (true);
            urlConnection.setDoOutput (true);
            urlConnection.setUseCaches (false);

            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.connect();

            JSONObject json;
            */

            String url = MainActivity.URL+"v1/user/"+login+"/"+password;
            System.out.println(url);

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response;
            String result = null;
            try {
                response = client.execute(request);
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
                System.out.println("Réponse : "+result);
                boolean connected = false;
                String tmp = "";
                for(int i=0 ; i<6 ; i++)
                    tmp+=result.charAt(i);

                if(!tmp.equals("<html>")){ // Si le serveur renvoie la page 404
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else { // Le serveur renvoie la ligne de table utilisateur de l'utilisateur connecté
                    Toast.makeText(getApplicationContext(), "Pseudo et/ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
