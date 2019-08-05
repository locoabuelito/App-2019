package com.example.auto.utilidades;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocalizacionServicio extends Service {

    private static final String TAG = "LocalizacionServicio";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Servicio Creado!");
        final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Servicio en progreso!");
        final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario");
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "Service onBind");
        return null;
    }
}
