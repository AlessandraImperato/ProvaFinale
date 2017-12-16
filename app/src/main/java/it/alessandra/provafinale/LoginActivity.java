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

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import it.alessandra.provafinale.Utils.FirebaseRest;
import it.alessandra.provafinale.Utils.PushNotification;
import it.alessandra.provafinale.Utils.TaskDelegate;

public class LoginActivity extends AppCompatActivity implements TaskDelegate{

    private EditText editUser;
    private EditText editPass;
    private Button bLogin;
    private Button bSignUp;
    private Spinner spinnerTipe;

    private String username;
    private String password;
    private String tipo;
    private String url;

    private ProgressDialog dialog;
    private TaskDelegate delegate;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUser = findViewById(R.id.edituser);
        editPass = findViewById(R.id.editpass);
        bLogin = findViewById(R.id.blogin);
        bSignUp = findViewById(R.id.bsignup);
        spinnerTipe = findViewById(R.id.spinner);

        delegate = this;

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = editUser.getText().toString();
                password = editPass.getText().toString();
                tipo = spinnerTipe.getSelectedItem().toString();

                if (username.equals("")){
                    if (password.equals("")){
                        Toast.makeText(getApplicationContext(),"Inserire username e password", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Inserire l'username", Toast.LENGTH_LONG).show();
                    }
                }else if (password.equals("")){
                    Toast.makeText(getApplicationContext(),"Inserire la password", Toast.LENGTH_LONG).show();
                }
                else {
                    if (tipo.equals("Corriere")) {
                        url = "Users/Corrieri/" + username + "/password.json";
                    } else if (tipo.equals("Utente")) {
                        url = "/Users/Utenti/" + username + "/password.json";
                    }

                    restCallLogin(url);
                }
            }
        });

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });

    }

    public void restCallLogin(String url) {
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Loading");
        dialog.show();

        FirebaseRest.get(url,null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String text = new String (responseBody);
                    if(text.equals(password)){
                        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("USERNAME", username);
                        editor.commit();
                        delegate.TaskCompletionResult("Accesso effettuato!");
                        if(tipo.equals("Utente")){
                            editor.putString("LOGIN", "Utente");
                            editor.commit();
                            Intent i = new Intent(getApplicationContext(),CouriersActivity.class);
                            startActivity(i);
                        }else if(tipo.equals("Corriere")){
                            editor.putString("LOGIN", "Corriere");
                            editor.commit();
                            Intent i = new Intent(getApplicationContext(),PackActivity.class);
                            startActivity(i);
                        }
                    }
                    else {
                        delegate.TaskCompletionResult("Accesso negato!");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                delegate.TaskCompletionResult("Accesso negato!");
            }
        });
    }
    @Override
    public void TaskCompletionResult(String result) {
        dialog.dismiss();
        dialog.cancel();
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }

}
