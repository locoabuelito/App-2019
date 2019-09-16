package com.example.auto.menu;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

import com.example.auto.R;
import com.example.auto.actividades.BloqueoGPS;
import com.example.auto.actividades.BloqueoManual;
import com.example.auto.actividades.BluetoothActivity;
import com.example.auto.utilidades.Utilidades;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OpcionesBloqueo extends AppCompatActivity  {
    private GridLayout gridLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_bloqueo);

        gridLayout = findViewById(R.id.grid_menu);
        if (Utilidades.validarAutomatico == true){
            setSingleeventAutomatic(gridLayout);
        }else {
            setSingleeventManual(gridLayout);
        }

    }
    private void setSingleeventAutomatic(GridLayout gridLayout) {
        for (int i=0; i<gridLayout.getChildCount(); i++){
            CardView cardView = (CardView)gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_bluetooth");
                    if (finalI == 0){
                        Intent i = new Intent(OpcionesBloqueo.this, BluetoothActivity.class);
                        startActivity(i);
                    }else if (finalI == 1){
                        Intent i = new Intent(OpcionesBloqueo.this, BloqueoGPS.class);
                        startActivity(i);
                    }else if (finalI == 2){
                        AlertaManual();
                    }
                }
            });
        }
    }


    private void setSingleeventManual(GridLayout gridLayout) {
        for (int i=0; i<gridLayout.getChildCount(); i++){
            CardView cardView = (CardView)gridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_bluetooth");
                    if (finalI == 0){
                        AlertaAutomatico();
                    }else if (finalI == 1){
                        AlertaAutomatico();
                    }else if (finalI == 2){
                        Intent i = new Intent(OpcionesBloqueo.this, BloqueoManual.class);
                        startActivity(i);
                    }
                }
            });
        }
    }

    private void AlertaManual() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OpcionesBloqueo.this);
        builder.setTitle(getString(R.string.titulo_opciones_bloqueo));
        builder.setMessage(R.string.mensaje_opciones_bloqueo_automatico)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void AlertaAutomatico() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OpcionesBloqueo.this);
        builder.setTitle(getResources().getString(R.string.titulo_opciones_bloqueo));
        builder.setMessage(getString(R.string.mensaje_opciones_bloqueo_manual))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }


}
