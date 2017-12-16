package it.alessandra.provafinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import it.alessandra.provafinale.Model.GestorePacchi;
import it.alessandra.provafinale.Model.Users;
import it.alessandra.provafinale.Utils.CourierAdapter;
import it.alessandra.provafinale.Utils.FirebaseRest;
import it.alessandra.provafinale.Utils.InternalStorage;
import it.alessandra.provafinale.Utils.JsonParse;
import it.alessandra.provafinale.Utils.TaskDelegate;

public class MainActivity extends AppCompatActivity implements TaskDelegate{

    private SharedPreferences preferences;
    private String control;
    private GestorePacchi gestore;
    private ProgressDialog dialog;
    private TaskDelegate delegate;

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

    public void restCall(String url){
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Caricamento");
        dialog.show();

        FirebaseRest.get(url, null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String text = new String (responseBody);
                    try {
                        List<Users> allUser = JsonParse.getAllUser(text);
                        gestore.setAllUsers(allUser);
                        delegate.TaskCompletionResult("ok");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.TaskCompletionResult("Errore caricamento");
            }
        });
    }
    @Override
    public void TaskCompletionResult(String result) {
        dialog.dismiss();
        dialog.cancel();
        InternalStorage.writeObject(getApplicationContext(),"ALLUSER",gestore);
    }
}
