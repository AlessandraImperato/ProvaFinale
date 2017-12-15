package it.alessandra.provafinale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.camera2.CameraManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import it.alessandra.provafinale.Model.Corriere;
import it.alessandra.provafinale.Model.GestorePacchi;
import it.alessandra.provafinale.Model.Pacco;
import it.alessandra.provafinale.Model.Users;
import it.alessandra.provafinale.Model.Utente;
import it.alessandra.provafinale.Utils.InternalStorage;

public class SinglePackActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SharedPreferences preferences;
    private TextView textDestinatario;
    private TextView textIndirizzo;
    private TextView textDimensione;
    private TextView textDeposito;
    private Button inConsegna;

    private String id;
    private String destinatario;

    private GestorePacchi gestore;
    private List<Users> allUser;
    private List<Pacco> pacchiCorriere;
    private Corriere currentCourier;
    private String usernameCourier;
    private Pacco pacco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pack);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textDeposito = findViewById(R.id.deposito);
        textIndirizzo = findViewById(R.id.indirizzo);
        textDimensione = findViewById(R.id.dim);
        textDestinatario = findViewById(R.id.destinatario);
        inConsegna = findViewById(R.id.consegna);

        Intent i = getIntent();
        destinatario = i.getStringExtra("DESTINATARIO");
        id = i.getStringExtra("IDpacco");

        gestore = (GestorePacchi) InternalStorage.readObject(getApplicationContext(),"ALLUSER");
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        usernameCourier = preferences.getString("USERNAME","");
        currentCourier = gestore.getCorriereByUser(usernameCourier);
        pacchiCorriere = currentCourier.getPacchi();
        pacco = currentCourier.findPackById(id);

        textDestinatario.setText(destinatario);
        textDimensione.setText(pacco.getDimensione());
        textIndirizzo.setText(pacco.getIndirizzo());
        textDeposito.setText(pacco.getDeposito());

        inConsegna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Notifica inviata", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
            //return true;
            preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("LOGIN", null);
            editor.commit();
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
