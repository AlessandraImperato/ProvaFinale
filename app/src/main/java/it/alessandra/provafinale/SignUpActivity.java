package it.alessandra.provafinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import it.alessandra.provafinale.Model.GestorePacchi;
import it.alessandra.provafinale.Model.Users;
import it.alessandra.provafinale.Utils.FirebaseRest;
import it.alessandra.provafinale.Utils.TaskDelegate;
import it.alessandra.provafinale.Utils.InternalStorage;

public class SignUpActivity extends AppCompatActivity implements TaskDelegate {

    private EditText editUser;
    private EditText editPass;
    private Button bSave;
    private Spinner spinnerTipe;

    private String username;
    private String password;
    private String tipo;

    private ProgressDialog dialog;
    private TaskDelegate delegate;
    private static FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private GestorePacchi gestore;
    private List<Users> allUser;
    private String url;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editUser = findViewById(R.id.edituserR);
        editPass = findViewById(R.id.editpassR);
        bSave = findViewById(R.id.bsave);
        spinnerTipe = findViewById(R.id.spinner);

        delegate = this;

       // gestore = (GestorePacchi) InternalStorage.readObject(getApplicationContext(), "ALLUSER");
       // allUser = gestore.getAllUsers();

        database = FirebaseDatabase.getInstance();

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editUser.getText().toString();
                password = editPass.getText().toString();
                tipo = spinnerTipe.getSelectedItem().toString();

                if (username.equals("")) {
                    if (password.equals("")) {
                        Toast.makeText(getApplicationContext(), "Inserire username e password", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Inserire l'username", Toast.LENGTH_LONG).show();
                    }
                } else if (password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Inserire la password", Toast.LENGTH_LONG).show();
                } else {
                    if (tipo.equals("Corriere")) {
                        url = "https://provafinale-72a38.firebaseio.com/Users/Corrieri/";
                    } else if (tipo.equals("Utente")) {
                        url = "https://provafinale-72a38.firebaseio.com/Users/Utenti/";
                    }
                    databaseReference = database.getReferenceFromUrl(url);
                    restCallAddUser(url);
                }
            }
        });

    }

    public void restCallAddUser(String url){
        dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setMessage("Caricamento");
        dialog.show();
        FirebaseRest.get(url, null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String text = new String (responseBody);
                    databaseReference.child(username).child("password").setValue(password);
                    delegate.TaskCompletionResult("Utente registrato");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public void TaskCompletionResult(String result) {
        dialog.dismiss();
        dialog.cancel();
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("USERNAME", username);
        editor.commit();
        if (tipo.equals("Corriere")) {
            editor.putString("LOGIN", "Corriere");
            editor.commit();
            Intent i = new Intent(getApplicationContext(),PackActivity.class);
            startActivity(i);
        } else if (tipo.equals("Utente")) {
            editor.putString("LOGIN", "Utente");
            editor.commit();
            Intent i = new Intent(getApplicationContext(),CouriersActivity.class);
            startActivity(i);
        }
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }
}