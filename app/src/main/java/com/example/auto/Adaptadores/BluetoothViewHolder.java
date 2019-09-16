package com.example.auto.Adaptadores;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auto.R;

public class BluetoothViewHolder extends RecyclerView.ViewHolder {

    TextView fecha_corriente, hora_corriente, estado_corriente, fechac, horac, corriente;
    TextView estado_puertas, fecha_puertas, hora_puertas, fecha, hora, puertas;
    CardView cardview_1, cardview_2;
    RecyclerView recyclerView;

    // Constructor
    public BluetoothViewHolder(@NonNull View itemView) {
        super(itemView);

        // Layout
        recyclerView = itemView.findViewById(R.id.recycler);
        cardview_1 = itemView.findViewById(R.id.cardview_1);
        cardview_2 = itemView.findViewById(R.id.cardview_2);
        // CORRIENTE
        corriente = itemView.findViewById(R.id.corriente);
        horac = itemView.findViewById(R.id.horac);
        fechac = itemView.findViewById(R.id.fechac);
        estado_corriente = itemView.findViewById(R.id.estado_corriente);
        fecha_corriente = itemView.findViewById(R.id.fecha_corriente);
        hora_corriente = itemView.findViewById(R.id.hora_corriente);
        // PUERTAS
        puertas = itemView.findViewById(R.id.puertas);
        fecha = itemView.findViewById(R.id.fecha);
        hora = itemView.findViewById(R.id.hora);
        estado_puertas = itemView.findViewById(R.id.estado_puertas);
        fecha_puertas = itemView.findViewById(R.id.fecha_puertas);
        hora_puertas = itemView.findViewById(R.id.hora_puertas);
    }

}


