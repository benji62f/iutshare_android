package com.example.lefebvrb.iutshare;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class AddAd extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);
        setTitle("IUT Share");

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        Spinner type_sp = (Spinner) findViewById(R.id.annonces_type_sp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, new String[] {"Je propose", "Je recherche"});
        type_sp.setAdapter(adapter);
    }


    public void addAnAd(View view){
        String titre = ((EditText) findViewById(R.id.annonces_titre_et)).getText().toString();
        String description = ((EditText) findViewById(R.id.annonces_description_et)).getText().toString();
        String lieu = ((EditText) findViewById(R.id.annonces_lieu_et)).getText().toString();
        String type = (String) ((Spinner) findViewById(R.id.annonces_type_sp)).getSelectedItem();

        System.out.println("Titre : "+titre+"\nDescription : "+description+"\nLieu : "+lieu+"\nType : "+type);

        if(titre.equals("") || description.equals("") || lieu.equals("") || type.equals("")){
            Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
        } else {
            try {
                URL url;
                DataOutputStream printout;
                DataInputStream input;
                url = new URL(MainActivity.URL + "v1/annonce/");
                System.out.println(url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setUseCaches(false);

                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);

                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("User-Agent", "GYUserAgentAndroid");
                urlConnection.setRequestProperty("Accept","*/*");
                //urlConnection.connect();

                //int reponse = urlConnection.getResponseCode();

                JSONObject object = new JSONObject();
                try {
                    object.put("titre", titre);
                    object.put("msg", description);
                    object.put("lieu", lieu);
                    object.put("type", type);
                    object.put("ano", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(object);

                DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
                out.writeBytes(object.toString());
                out.flush();
                out.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                JSONObject reponse = new JSONObject(in.readLine());
                String tmp = reponse.getString("titre");
                if(tmp == null)
                    Toast.makeText(getApplicationContext(), "Echec du dépôt", Toast.LENGTH_LONG).show();
                else if(tmp.equals(titre)){
                    Toast.makeText(getApplicationContext(), "Annonce déposée avec succès", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddAd.this, MainActivity.class);
                    startActivity(intent);
                }
            } catch(Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Erreur connexion réseau", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_ad, menu);
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
