package com.example.auto.menu;

import android.content.DialogInterface;
import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.GridLayout;

import com.example.auto.R;
import com.example.auto.actividades.Historial;
import com.example.auto.actividades.MapsActivity;
import com.example.auto.utilidades.Utilidades;
import com.example.auto.login.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Menu_v2 extends AppCompatActivity implements OtpManual.OtpListener, OtpAutomatico.OtpListener{
    private GridLayout gridLayout;
    private FloatingActionButton floatingActionButton;
    private  int MY_PERMISSIONS_REQUEST;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_v2);

        gridLayout = (GridLayout) findViewById(R.id.grid_menu);
        setSingleevent(gridLayout);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        CerrarSesion();


    }


    private void CerrarSesion() {
        floatingActionButton = (FloatingActionButton)findViewById(R.id.id_fab);
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
                                finish();
                                Intent i = new Intent(Menu_v2.this, MainActivity.class);
                                startActivity(i);


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
        builder.setTitle("Opciones de MODALIDAD DE BLOQUEO-DESBLOQUEO");
        builder.setMessage("Eliga una opción para el BLOQUEO-DESBLOQUEO de su vehículo")
                .setPositiveButton("Manual", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utilidades.validarAutomatico = false;
                        /*Intent i = new Intent(Menu_v2.this, OpcionesBloqueo.class);
                        startActivity(i);*/
                        openDialogManual();
                    }
                })
                .setNegativeButton("Automático", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utilidades.validarAutomatico = true;
                        /*Intent i = new Intent(Menu_v2.this, OpcionesBloqueo.class);
                        startActivity(i);*/
                        openDialogAutomatico();
                    }
                })
                .show();
    }
    private void openDialogManual() {
        OtpManual otpManual = new OtpManual();
        otpManual.show(getSupportFragmentManager(), "Codigo de Verificación - MANUAL");
    }

    private void openDialogAutomatico() {
        OtpAutomatico otpAutomatico = new OtpAutomatico();
        otpAutomatico.show(getSupportFragmentManager(), "Codigo de Verificación - AUTOMÁTICO");
    }

    @Override
    public void applyTexts(String codigo) {

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
