package it.alessandra.provafinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import it.alessandra.provafinale.Model.Corriere;
import it.alessandra.provafinale.Model.GestorePacchi;
import it.alessandra.provafinale.Model.Pacco;
import it.alessandra.provafinale.Model.Users;
import it.alessandra.provafinale.Model.Utente;
import it.alessandra.provafinale.Utils.FirebaseRest;
import it.alessandra.provafinale.Utils.InternalStorage;
import it.alessandra.provafinale.Utils.JsonParse;
import it.alessandra.provafinale.Utils.SettingDate;
import it.alessandra.provafinale.Utils.TaskDelegate;

public class AddPackActivity extends AppCompatActivity implements TaskDelegate{

    //private String nomeCorriereAssegnato;
    //private String cognomeCorriereAssegnato;
    private String corriereAssegnato;
    private Button bsave;
    private EditText editDeposito;
    private EditText editIndirizzo;
    private EditText editData;
    private EditText editDimensione;

    private String deposito;
    private String indirizzo;
    private String dimensione;
    private String data;
    private Date dataConsegna;

    private SharedPreferences preferences;
    private Toolbar toolbar;

    private GestorePacchi gestore;
    private List<Users> allUser;
    private List<Pacco> listaPacchi;
    private Pacco newPacco;
    private Utente currentUtente;

    private List<Pacco> pacchiCorriere;
    private Corriere currentCourier;
    private String usernameCourier;

    private String username;
    private String stato;
    private String destinatario;

    private ProgressDialog dialog;
    private TaskDelegate delegate;
    private static FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceC;

    private String idForCourier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pack);

        delegate = this;
        database = FirebaseDatabase.getInstance();
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        gestore = (GestorePacchi) InternalStorage.readObject(getApplicationContext(),"ALLUSER");
        Intent i = getIntent();
        corriereAssegnato = i.getStringExtra("NOMECORRIEREASSEGNATO") + " " + i.getStringExtra("COGNOMECORRIEREASSEGNATO"); ;
        username = preferences.getString("USERNAME","");
        usernameCourier = i.getStringExtra("USERCORRIERE");
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("COURIER", usernameCourier);
        editor.commit();

        allUser = gestore.getAllUsers();
        currentUtente = gestore.getUtenteByUser(username);
        listaPacchi = currentUtente.getPacchi();

        currentCourier = gestore.getCorriereByUser(usernameCourier);
        pacchiCorriere = currentCourier.getPacchi();

        destinatario = currentUtente.getNome() + " " + currentUtente.getCognome();

        bsave = findViewById(R.id.binsertpack);
        editDeposito = findViewById(R.id.editdeposito);
        editData = findViewById(R.id.editdata);
        editIndirizzo = findViewById(R.id.editindirizzo);
        editDimensione = findViewById(R.id.editdim);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deposito = editDeposito.getText().toString();
                indirizzo = editIndirizzo.getText().toString();
                dimensione = editDimensione.getText().toString();
                data = editData.getText().toString();
                dataConsegna = SettingDate.formatToDate(data);
                stato = "commissionato";

                if (deposito.equals("") || indirizzo.equals("") || dimensione.equals("") || data.equals("")){
                    Toast.makeText(getApplicationContext(), "Inserire tutti i campi", Toast.LENGTH_LONG).show();
                }else{
                    newPacco = new Pacco(deposito,indirizzo,destinatario,dimensione,stato,dataConsegna,corriereAssegnato);
                    listaPacchi.add(newPacco);
                    currentUtente.setPacchi(listaPacchi);
                    String url = "https://provafinale-72a38.firebaseio.com/Users/Utenti/" + username;
                    databaseReference = database.getReferenceFromUrl(url);
                    restCallAddUserPack("Users/Utenti/" + username +"/Pacchi.json");

                    String urlC = "https://provafinale-72a38.firebaseio.com/Users/Corrieri/" + usernameCourier;
                    databaseReferenceC = database.getReferenceFromUrl(urlC);
                    restCallAddCourierPack("Users/Corrieri/" + usernameCourier +"/Pacchi.json");
                }
            }
        });

    }

    public void restCallAddUserPack(String url){
        dialog = new ProgressDialog(AddPackActivity.this);
        dialog.setMessage("Caricamento");
        dialog.show();
        FirebaseRest.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String text = new String (responseBody);
                    int index = JsonParse.key(text);
                    newPacco.setId(generaKey(index));
                    idForCourier = generaKey(index);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("IDFORCOURIER", idForCourier);
                    editor.commit();
                    databaseReference.child("Pacchi").child(generaKey(index)).child("deposito").setValue(deposito);
                    databaseReference.child("Pacchi").child(generaKey(index)).child("indirizzo").setValue(indirizzo);
                    databaseReference.child("Pacchi").child(generaKey(index)).child("dimensione").setValue(dimensione);
                    databaseReference.child("Pacchi").child(generaKey(index)).child("stato").setValue(stato);
                    databaseReference.child("Pacchi").child(generaKey(index)).child("data di consegna").setValue(data);
                    databaseReference.child("Pacchi").child(generaKey(index)).child("corriere").setValue(corriereAssegnato);
                    delegate.TaskCompletionResult("Pacco aggiunto con successo");
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.TaskCompletionResult("Impossibile aggiungere il pacco");
            }
        });

    }

    public void restCallAddCourierPack(String url){
        dialog = new ProgressDialog(AddPackActivity.this);
        dialog.setMessage("Invio notifica al corriere");
        dialog.show();
        FirebaseRest.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String text = new String (responseBody);
                    int index = JsonParse.key(text);
                    newPacco.setId(generaKey(index));
                    databaseReferenceC.child("Pacchi").child(generaKey(index)).child("deposito").setValue(deposito);
                    databaseReferenceC.child("Pacchi").child(generaKey(index)).child("indirizzo").setValue(indirizzo);
                    databaseReferenceC.child("Pacchi").child(generaKey(index)).child("dimensione").setValue(dimensione);
                    databaseReferenceC.child("Pacchi").child(generaKey(index)).child("stato").setValue(stato);
                    databaseReferenceC.child("Pacchi").child(generaKey(index)).child("data di consegna").setValue(data);
                    databaseReferenceC.child("Pacchi").child(generaKey(index)).child("destinatario").setValue(username);
                    databaseReferenceC.child("Pacchi").child(generaKey(index)).child("id").setValue(idForCourier);
                    delegate.TaskCompletionResult("Notifica inviata");
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.TaskCompletionResult("Impossibile notificare");
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

    @Override
    public void TaskCompletionResult(String result) {
        dialog.dismiss();
        dialog.cancel();
        InternalStorage.writeObject(getApplicationContext(), "ALLUSER", gestore); //salvo in locale
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }

    public String generaKey(int index) {
        String keyG = "";
        if (index < 10) {
            keyG = "0" + index;
        } else {
            keyG = "" + index;
        }
        return keyG;
    }
}
