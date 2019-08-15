package com.example.auto.menu;

import android.content.DialogInterface;
import android.content.Intent;

import com.example.auto.POJO.otpPojo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.auto.R;
import com.example.auto.actividades.Historial;
import com.example.auto.actividades.MapsActivity;
import com.example.auto.utilidades.Utilidades;
import com.example.auto.login.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.auto.utilidades.Utilidades.otpB;

public class Menu_v2 extends AppCompatActivity implements OtpManual.OtpListener, OtpAutomatico.OtpListener{
    private static final String TAG = "MenuClass";
    private GridLayout gridLayout;
    private FloatingActionButton floatingActionButton;
    private  int MY_PERMISSIONS_REQUEST;
    private FusedLocationProviderClient fusedLocationClient;
    final DatabaseReference dataRefModo = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/modo/codigo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_v2);

        gridLayout = findViewById(R.id.grid_menu);
        setSingleevent(gridLayout);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        CerrarSesion();


    }


    private void CerrarSesion() {
        floatingActionButton = findViewById(R.id.id_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Menu_v2.this);
                builder.setTitle("SALIR");
                builder.setMessage("Los datos no estaran disponibles."+"\nEstas seguro que deseas cerrar sesión?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent i = new Intent(Menu_v2.this, MainActivity.class);
                                startActivity(i);
                                finish();


                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
    }

    private void Opciones(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Menu_v2.this);
        builder.setTitle(getResources().getString(R.string.opciones));
        builder.setMessage(getResources().getString(R.string.mensaje_opciones))
                .setPositiveButton("Manual", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utilidades.validarAutomatico = false;
                        Toast.makeText(Menu_v2.this, "Aguarde un momento. Se le estará enviando un codigo de verificación.", Toast.LENGTH_LONG).show();
                        final DatabaseReference dataRefModo = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/modo");
                        dataRefModo.child("activado").setValue(false);
                        openDialogManual();
                    }
                })
                .setNegativeButton("Automático", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utilidades.validarAutomatico = true;
                        Toast.makeText(Menu_v2.this, "Aguarde un momento. Se le estará enviando un codigo de verificación.", Toast.LENGTH_LONG).show();
                        final DatabaseReference dataRefModo = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/modo");
                        dataRefModo.child("activado").setValue(true);
                        openDialogAutomatico();
                    }
                })
                .show();
    }
    private void openDialogManual() {
        OtpManual otpManual = new OtpManual();
        otpManual.show(getSupportFragmentManager(), getResources().getString(R.string.titulo_manual));
    }
    private void openDialogAutomatico() {
        OtpAutomatico otpAutomatico = new OtpAutomatico();
        otpAutomatico.show(getSupportFragmentManager(), getResources().getString(R.string.titulo_automatico));
    }


    @Override
    public void applyTexts(String code) {
        Toast.makeText(Menu_v2.this, "Código ingresado ! " + code, Toast.LENGTH_LONG).show();
        dataRefModo.child("manual").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    otpPojo otpManual = dataSnapshot.getValue(otpPojo.class);
                    if (/*!code.isEmpty() && */code.equals(otpManual.getManual())) {
                        Intent i = new Intent(Menu_v2.this, OpcionesBloqueo.class);
                        startActivity(i);
                        Log.d(TAG, "onDataChange: " + otpManual.getManual());
                    } else {
                        Toast.makeText(Menu_v2.this, "Código ingresado incorrectamente! " + code, Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onDataChange: " + code);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setSingleevent(GridLayout gridLayout) {
        for (int i=0; i<gridLayout.getChildCount(); i++){
            CardView cardView = (CardView)gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_bluetooth");
                    if (finalI == 0){
                        Intent i = new Intent(Menu_v2.this, Historial.class);
                        startActivity(i);
                    }else if (finalI == 1){
                        Opciones();
                    }else if (finalI == 2){
                        Intent i = new Intent(Menu_v2.this, MapsActivity.class);
                        startActivity(i);
                    }
                }
            });
        }
    }
}
