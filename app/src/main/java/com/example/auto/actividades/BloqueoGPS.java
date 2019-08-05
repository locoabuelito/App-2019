package com.example.auto.actividades;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auto.R;
import com.example.auto.menu.OpcionesBloqueo;
import com.example.auto.utilidades.UbicacionService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BloqueoGPS extends AppCompatActivity {
    private final int MY_PERMISSIONS_REQUEST = 0;
    private FusedLocationProviderClient fusedLocationClient;
    static BloqueoGPS instance;
    LocationRequest locationRequest;
    final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario");
    private SeekBar distancia;
    private TextView distancia_bloqueo;
    private Button enviar;
    private int d = 0;
    public static BloqueoGPS getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueo_gps);
        instance = this;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        ObtenerPermisoUsuario();
        distancia = (SeekBar)findViewById(R.id.seekBar);
        distancia_bloqueo = (TextView)findViewById(R.id.txt_gps_bloqueo_distancia);
        enviar = (Button)findViewById(R.id.btn_Enviar);

        distancia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("SeekBar", "onProgressChanged: PROGRESO");
                d = progress;
                distancia_bloqueo.setText(progress +" metros");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("SeekBar", "onStartTrackingTouch: INICIADO");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("SeekBar", "onStopTrackingTouch: PARADO");
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (d == 0){
                    Toast.makeText(BloqueoGPS.this,"Distancia no configurada. Verifique nuevamente!",Toast.LENGTH_LONG).show();
                }else {
                    dataRef.child("seccion_distancias").child("bloqueo").setValue(d);
                    Toast.makeText(BloqueoGPS.this, "Configurando distancia para el bloqueo de encendido!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public boolean ObtenerPermisoUsuario() {
        buildLocationRequest();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(BloqueoGPS.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
            return true;
        } /*
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario");
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Map<String,Object> latlong = new HashMap<>();
                            latlong.put("Latitud", location.getLatitude());
                            latlong.put("Longitud", location.getLongitude());
                            Log.e("Latitud: ", +location.getLatitude()+" Longitud: "+location.getLongitude());
                            dataRef.child("seccion_ubicacion_usuario").setValue(latlong);

                        }
                    }
                });*/
        fusedLocationClient.requestLocationUpdates(locationRequest, getPendingIntent());
        return false;

    }
    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setSmallestDisplacement(10f);
    }
    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, UbicacionService.class);
        intent.setAction(UbicacionService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(BloqueoGPS.this,"Se ha activado el GPS. Habilite la función de UBICACIÓN de su dispositivo móvil!",Toast.LENGTH_LONG).show();
            }else {
                Intent i = new Intent(BloqueoGPS.this, OpcionesBloqueo.class);
                startActivity(i);

            }
        }
    }

}
