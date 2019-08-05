package com.example.auto.actividades;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.auto.R;
import com.example.auto.menu.Menu_v2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BloqueoManual extends AppCompatActivity {
    private Button btn_Puertas, btn_Corriente, btn_Puertasd, btn_Corriented;
    private boolean conexion = true;
    private String timeActivacion, timeDesActivacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueo_manual);

        btn_Puertas = (Button) findViewById(R.id.btn_puertas);
        btn_Corriente = (Button) findViewById(R.id.btn_corriente);
        btn_Puertasd = (Button) findViewById(R.id.btn_puertasd);
        btn_Corriented = (Button) findViewById(R.id.btn_corriented);
        estado_conexion();


    }

    private void estado_conexion (){
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean connected = dataSnapshot.getValue(Boolean.class);
                if (connected){
                    EjecutarConInternet();
                    //Toast.makeText(getApplicationContext(), "Conectado!", Toast.LENGTH_LONG).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(BloqueoManual.this);
                    builder.setTitle("Estado de Conexión");
                    builder.setMessage("El dispositivo se encuentra sin conexión a Internet.")
                            .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(BloqueoManual.this, Menu_v2.class);
                                    startActivity(i);
                                }
                            }).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Listener was cancelled");
            }
        });
    }

    private void EjecutarConInternet() {
        final DatabaseReference dataRefPuertas = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_rele/rele_puertas");
        final DatabaseReference dataRefPuertasTimeActivacion = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_rele/envio_activacion_rele_puertas");
        final DatabaseReference dataRefPuertasTimeDesActivacion = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_rele/envio_desactivacion_rele_puertas");

        final DatabaseReference dataRefCorriente = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_rele/rele_corriente");
        final DatabaseReference dataRefCorrienteTimeActivacion = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_rele/envio_activacion_rele_corriente");
        final DatabaseReference dataRefCorrienteTimeDesActivacion = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_rele/envio_desactivacion_rele_corriente");


        btn_Puertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoraBloqueo();
                dataRefPuertas.setValue(true);
                dataRefPuertasTimeActivacion.setValue(timeActivacion);
                Toast.makeText(BloqueoManual.this, "Se ha activado el bloqueo de puertas a las "+timeActivacion,Toast.LENGTH_LONG).show();
                }
            });
        btn_Puertasd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoraBloqueo();
                dataRefPuertas.setValue(false);
                dataRefPuertasTimeDesActivacion.setValue(timeDesActivacion);
                Toast.makeText(BloqueoManual.this, "Se ha desactivado el bloqueo de puertas a las "+timeDesActivacion,Toast.LENGTH_LONG).show();
            }
        });

        btn_Corriente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoraBloqueo();
                dataRefCorriente.setValue(true);
                dataRefCorrienteTimeActivacion.setValue(timeActivacion);
                Toast.makeText(BloqueoManual.this, "Se ha activado el bloqueo de corriente a las "+timeActivacion,Toast.LENGTH_LONG).show();
            }
        });

        btn_Corriented.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoraBloqueo();
                dataRefCorriente.setValue(false);
                dataRefCorrienteTimeDesActivacion.setValue(timeDesActivacion);
                Toast.makeText(BloqueoManual.this, "Se ha desactivado el bloqueo de corriente a las "+timeDesActivacion,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void HoraBloqueo() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        timeActivacion = format.format(calendar.getTime());
        timeDesActivacion = format.format(calendar.getTime());
    }

}


