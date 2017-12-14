package it.alessandra.provafinale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private String control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        control = "";

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        control = preferences.getString("LOGIN","");

        //se non ci sono utenti loggati => devo effettuare il login
        //se è loggato un corriere => lista pacchi
        //se è loggato un utente qualsiasi => lista corrieri
        if(control.equals("Utente")){
            Intent i = new Intent(getApplicationContext(),CouriersActivity.class);
            startActivity(i);
        }else if(control.equals("Corriere")){
            Intent i = new Intent(getApplicationContext(),PackActivity.class);
            startActivity(i);
        }
        else {
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
        }
    }
}
