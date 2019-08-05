package com.example.auto.utilidades;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.auto.actividades.BloqueoGPS;
import com.google.android.gms.location.LocationResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UbicacionService extends BroadcastReceiver {
    public static final String ACTION_PROCESS_UPDATE = "com.example.auto.utilidades.UPDATE_LOCATION";
    final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario");
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATE.equals(action)){
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null){
                    Location location = result.getLastLocation();
                    Map<String,Object> latlong = new HashMap<>();
                    latlong.put("Latitud", location.getLatitude());
                    latlong.put("Longitud", location.getLongitude());
                    Log.e("Latitud: ", +location.getLatitude()+" Longitud: "+location.getLongitude());
                    try{
                        dataRef.child("seccion_ubicacion_usuario").setValue(latlong);
                    }catch (Exception e){
                        Toast.makeText(context, "Actualizando ubicacion!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
