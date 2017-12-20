package it.alessandra.provafinale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotifyCouriersActivity extends AppCompatActivity {
    private String idPaccoUtente;
    private String idPaccoCorriere;
    private static FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceC;
    private String url;
    private String urlC;
    private String username;
    private String usernameCorriere;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_couriers);

        Intent i = getIntent();
        idPaccoUtente = i.getStringExtra("IDPACCOUTENTE");
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString("USERNAME","");
        /*cambia lo stato all'utente*/
        url = "https://provafinale-72a38.firebaseio.com/Users/Utenti/"+ username + "/Pacchi/" + idPaccoUtente;
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReferenceFromUrl(url);
        databaseReference.child("stato").setValue("Consegnato");

        /*cambia lo stato al corriere*/
        /*preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        usernameCorriere = preferences.getString("COURIER","");
        idPaccoCorriere = ""; ?
        url = "https://provafinale-72a38.firebaseio.com/Users/Corrieri/"+ usernameCorriere + "/Pacchi/" + idPaccoCorriere;
        databaseReference = database.getReferenceFromUrl(urlC);
        databaseReference.child("stato").setValue("Consegnato");*/
        finish();
    }
}
