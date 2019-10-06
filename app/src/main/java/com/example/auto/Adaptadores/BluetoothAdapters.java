package com.example.auto.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auto.POJO.BluetoothCheckActivacionPojo;
import com.example.auto.R;

import java.util.ArrayList;
import java.util.List;

public class BluetoothAdapters extends RecyclerView.Adapter<BluetoothAdapters.BluetoothViewHolder> {

    List<BluetoothCheckActivacionPojo> GetBluetoothList;

    // Constructor
    public BluetoothAdapters(List<BluetoothCheckActivacionPojo> getBluetoothList) {
        this.GetBluetoothList = getBluetoothList;
    }

    @NonNull
    @Override
    public BluetoothViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_historial, parent, false);
        BluetoothViewHolder holder = new BluetoothViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothViewHolder holder, int position) {
        BluetoothCheckActivacionPojo bluetoothCheckActivacionPojo = GetBluetoothList.get(position);

        holder.cardview_1.setCardBackgroundColor(Color.rgb(18, 194, 53));
        holder.puertas.setTextColor(Color.rgb(255, 255, 255));
        holder.estado_puertas.setTextColor(Color.rgb(255, 255, 255));
        holder.fecha.setTextColor((Color.rgb(255, 255, 255)));
        holder.hora.setTextColor((Color.rgb(255, 255, 255)));
        holder.puertas.setText("ESTADO BLOQUEO DE PUERTAS");
        holder.estado_puertas.setText("ACTIVADO");
        holder.fecha_corriente.setText(bluetoothCheckActivacionPojo.getConfirmacion_activacion_rele_corriente());
        holder.hora_corriente.setText(bluetoothCheckActivacionPojo.getHora_confirmacion_activacion_rele_corriente());
    }

    @Override
    public int getItemCount() {
        return GetBluetoothList.size();
    }

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
}


