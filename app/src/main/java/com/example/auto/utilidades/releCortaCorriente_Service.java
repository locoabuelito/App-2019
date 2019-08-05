package com.example.auto.utilidades;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.auto.R;
import com.google.firebase.database.*;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class releCortaCorriente_Service extends Service {

    private static final String TAG = "FireService";
    protected DatabaseReference databaseReference;
    private PendingIntent pendingIntent;
    private NotificationManagerCompat notificationManagerCompat;
    private NotificationCompat.Builder notificacionBuilder;
    private int icono;
    private static final String CHANNEL_ID = "NOTIFICACION";
    private static final int NOTIFICACION_ID = 1;
    private String correo = "brunosotocde@gmail.com";
    private String pass = "12345678";

    private javax.mail.Session session;



    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Servicio Creado!");
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_rele/rele_corriente");
       // Intent i = new Intent(this, MenuActivity.class); // Para la notificacion
        //pendingIntent = PendingIntent.getActivity(this, 0,i,0);
        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Servicio en progreso!");
        final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_rele/rele_corriente");
        new Thread(new Runnable() {
            @Override
            public void run() {
                dataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Boolean estado = (Boolean) dataSnapshot.getValue();
                        String msjNotificacion;
                        if (dataSnapshot.exists()) {
                            if (estado) {
                                msjNotificacion = "USO DEL AUTO DESACTIVADO";
                                icono = R.drawable.ic_notifications_black_24dp;
                            } else {
                                msjNotificacion = "USO DEL AUTO ACTIVADO";
                                icono = R.drawable.ic_notifications_black_24dp;
                            }
                            Log.v("Cambio de: ", msjNotificacion);
                            createNotificationChannel();
                            notification(msjNotificacion, icono);
                        }else{
                            Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }).start();
        return Service.START_STICKY;

    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationChannel.enableVibration(true);
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void notification(String msg, int icono){
        NotificationCompat.Builder notificacionBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        notificacionBuilder.setContentTitle("Notificación de Sección de Rele");
        notificacionBuilder.setSmallIcon(icono).setContentIntent(pendingIntent);
        notificacionBuilder.setContentText(msg);
        notificacionBuilder.setPriority(Notification.PRIORITY_MAX);
        long[] v = {1000, 1000, 1000};
        notificacionBuilder.setVibrate(v);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificacionBuilder.setSound(uri);

        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID,notificacionBuilder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Servicio destruido!");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return null;
    }
}
