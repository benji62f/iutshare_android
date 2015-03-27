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


public class SuscribeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscribe);
        setTitle("IUT Share");

        Spinner statut_sp = (Spinner) findViewById(R.id.inscription_statut_sp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, new String[] {"Etudiant", "Enseignant"});
        statut_sp.setAdapter(adapter);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

    }


    public void suscribe(View view){
        String nom = ((EditText) findViewById(R.id.inscription_nom_et)).getText().toString();
        String prenom = ((EditText) findViewById(R.id.inscription_prenom_et)).getText().toString();
        String pseudo = ((EditText) findViewById(R.id.inscription_pseudo_et)).getText().toString();
        String mdp = ((EditText) findViewById(R.id.inscription_password_et)).getText().toString();
        String type = (String) ((Spinner) findViewById(R.id.inscription_statut_sp)).getSelectedItem();

        System.out.println("Nom : "+nom+"\nPrenom : "+prenom+"\nPseudo : "+pseudo+"\nMot de passe : "+mdp+"\nType : "+type);

        if(nom.equals("") || prenom.equals("") || pseudo.equals("") || mdp.equals("")){
            Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
        } else {
            try {
                URL url;
                DataOutputStream printout;
                DataInputStream input;
                url = new URL(MainActivity.URL + "v1/user/");
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
                    object.put("nom", nom);
                    object.put("prenom", prenom);
                    object.put("login", pseudo);
                    object.put("mdp", mdp);
                    object.put("type", type);
                    object.put("id", 0);
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
                String login = reponse.getString("login");
                if(login == null)
                    Toast.makeText(getApplicationContext(), "L'inscription a échouée", Toast.LENGTH_LONG).show();
                else if(login.equals(pseudo)){
                    Toast.makeText(getApplicationContext(), "Inscription réussie", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SuscribeActivity.this, LoginActivity.class);
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
        getMenuInflater().inflate(R.menu.menu_suscribe, menu);
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
