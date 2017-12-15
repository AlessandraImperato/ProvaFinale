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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import it.alessandra.provafinale.Model.Corriere;
import it.alessandra.provafinale.Model.GestorePacchi;
import it.alessandra.provafinale.Model.Users;
import it.alessandra.provafinale.Utils.CourierAdapter;
import it.alessandra.provafinale.Utils.FirebaseRest;
import it.alessandra.provafinale.Utils.InternalStorage;
import it.alessandra.provafinale.Utils.JsonParse;
import it.alessandra.provafinale.Utils.TaskDelegate;

public class CouriersActivity extends AppCompatActivity implements TaskDelegate{

    private Toolbar toolbar;
    private SharedPreferences preferences;
    private RecyclerView recyclerCouriers;
    private LinearLayoutManager linearLayoutManager;
    private CourierAdapter courierAdapter;
    private ProgressDialog dialog;
    private TaskDelegate delegate;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Button goPack;
    private GestorePacchi gestore;
    private List<Corriere> listaCorrieri;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couriers);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        goPack = findViewById(R.id.bpack);
        recyclerCouriers = findViewById(R.id.recyclercourier);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerCouriers.setLayoutManager(linearLayoutManager);

        delegate = this;

        //gestore = (GestorePacchi) InternalStorage.readObject(getApplicationContext(),"ALLUSER");
        //listaCorrieri = gestore.getCouriers();
        gestore = new GestorePacchi();
        listaCorrieri = new ArrayList<>();
        url = "Users/Corrieri.json";
        restCallCouriers(url);

        mSwipeRefreshLayout = findViewById(R.id.container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restCallCouriers(url);
            }
        });

        goPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),UserPackActivity.class);
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

    public void restCallCouriers(final String url){
        dialog = new ProgressDialog(CouriersActivity.this);
        dialog.setMessage("Caricamento");
        dialog.show();

        FirebaseRest.get(url, null, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String text = new String (responseBody);
                    try {
                        listaCorrieri = JsonParse.getListCouriers(text);
                        gestore.setCorrieri(listaCorrieri);
                        mSwipeRefreshLayout.setRefreshing(false);
                        delegate.TaskCompletionResult("Corrieri caricati");
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
        courierAdapter = new CourierAdapter(listaCorrieri,getApplicationContext());
        recyclerCouriers.setAdapter(courierAdapter);
        InternalStorage.writeObject(getApplicationContext(),"ALLUSER",gestore);
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
    }
}
