package it.alessandra.provafinale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NotifyCouriersActivity extends AppCompatActivity {
    private String idPaccoUtente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_couriers);
        // codice per notifiche
        Intent i = getIntent();
        idPaccoUtente = i.getStringExtra("IDPACCOUTENTE");

        finish();
    }
}
