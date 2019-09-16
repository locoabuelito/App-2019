package com.example.auto.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auto.POJO.BluetoothCheckActivacionPojo;
import com.example.auto.R;

import java.util.List;

public class BluetoothAdapter extends RecyclerView.Adapter<BluetoothViewHolder> {
    Context context;
    List<BluetoothCheckActivacionPojo> getBluetoothList;

    // Constructor
    public BluetoothAdapter(Context context, List<BluetoothCheckActivacionPojo> getBluetoothList) {
        this.context = context;
        this.getBluetoothList = getBluetoothList;
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
        holder.cardview_1.setCardBackgroundColor(Color.rgb(18, 194, 53));
        holder.puertas.setTextColor(Color.rgb(255, 255, 255));
        holder.estado_puertas.setTextColor(Color.rgb(255, 255, 255));
        holder.fecha.setTextColor((Color.rgb(255, 255, 255)));
        holder.hora.setTextColor((Color.rgb(255, 255, 255)));
        holder.fecha_corriente.setText(getBluetoothList.get(position).getEnvio_activacion_rele_corriente().substring(0, 10));
        holder.hora_corriente.setText(getBluetoothList.get(position).getEnvio_activacion_rele_corriente().substring(11, 20));
    }

    @Override
    public int getItemCount() {
        return getBluetoothList.size();
    }
}