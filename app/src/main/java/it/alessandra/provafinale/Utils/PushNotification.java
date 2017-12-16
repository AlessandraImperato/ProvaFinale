package it.alessandra.provafinale.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.alessandra.provafinale.MainActivity;
import it.alessandra.provafinale.PackActivity;
import it.alessandra.provafinale.R;

/**
 * Created by utente7.academy on 15/12/2017.
 */

public class PushNotification extends Service {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String url = "https://provafinale-72a38.firebaseio.com/Users/";
    private ChildEventListener handler;
    private DatabaseReference usersReference = database.getReferenceFromUrl(url);


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = sharedPreferences.getString("USERNAME","");
        final String type = sharedPreferences.getString("LOGIN","");
        final String userType = changeType(type);

        url = url +userType+"/"+ username +"/Pacchi" ;
        usersReference = database.getReferenceFromUrl(url);

        handler = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (type.equals("Corriere") && dataSnapshot.exists()) {
                    activePushValidation(dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (type.equals("Utente") &&dataSnapshot.exists()) {
                    activePushValidation(dataSnapshot.getKey());
                }
            }


            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("FIREBASE_SERVICE", "REMOVE: " + dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.i("FIREBASE_SERVICE", "MOVED: " + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        usersReference.addChildEventListener(handler); //associo il listener
    }

    public void activePushValidation(String commListener) {
        Intent intent = new Intent(this, MainActivity.class);
        sendNotification(intent, "Notifica", commListener);

    }

    public void sendNotification(Intent intent, String title, String body) {
        /*1*/Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

       /*2*/ PendingIntent activity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri dsUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setAutoCancel(true);
        builder.setSound(dsUri); //è il default
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(bitmap);
        builder.setShowWhen(true);
        builder.setContentIntent(activity);

        /*3*/NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public String changeType(String type){
        String newType ="";
        if (type.equals("Corriere")){
            newType = "Corrieri";
        }else if(type.equals("Utente")){
            newType = "Utenti";
        }
        return newType;
    }
}
