package it.alessandra.provafinale;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.util.ArrayList;
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
import it.alessandra.provafinale.Utils.PackAdapter;
import it.alessandra.provafinale.Utils.PackCourierAdapter;
import it.alessandra.provafinale.Utils.PushNotification;
import it.alessandra.provafinale.Utils.TaskDelegate;

public class PackActivity extends AppCompatActivity implements TaskDelegate{

    private Toolbar toolbar;
    private SharedPreferences preferences;
    private RecyclerView recyclerPack;
    private LinearLayoutManager linearLayoutManager;
    private PackCourierAdapter packCourierAdapter;
    private ProgressDialog dialog;
    private TaskDelegate delegate;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String username;
    private List<Pacco> listaPacchi;


    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack);

        Intent intent = new Intent(getApplicationContext(), PushNotification.class);
        startService(intent);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerPack = findViewById(R.id.recycleruserpack);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerPack.setLayoutManager(linearLayoutManager);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username = preferences.getString("USERNAME","");

        listaPacchi = new ArrayList<>();

        delegate = this;

        url = "Users/Corrieri/" + username +"/Pacchi.json";

        restCallPack(url);

        mSwipeRefreshLayout = findViewById(R.id.container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restCallPack(url);
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

    public void restCallPack(String url){
        dialog = new ProgressDialog(PackActivity.this);
        dialog.setMessage("Caricamento");
        dialog.show();

        FirebaseRest.get(url, null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String text = new String (responseBody);
                    try {
                        listaPacchi = JsonParse.getListPack(text);
                        mSwipeRefreshLayout.setRefreshing(false);
                        delegate.TaskCompletionResult("Pacchi caricati");
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
        packCourierAdapter = new PackCourierAdapter(listaPacchi,getApplicationContext());
        recyclerPack.setAdapter(packCourierAdapter);
        InternalStorage.writeObject(getApplicationContext(),"PACCHICORRIERE",listaPacchi);
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }
}
