package com.example.auto.utilidades;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.auto.POJO.SensoresPojo;
import com.example.auto.R;

import java.util.ArrayList;

public class AdapterEstadoSensores extends RecyclerView.Adapter<AdapterEstadoSensores.EstadoHolder>{


    ArrayList<SensoresPojo> SensoresPojoList;


    public AdapterEstadoSensores(ArrayList<SensoresPojo> sensoresPojoList) {
        this.SensoresPojoList = sensoresPojoList;
    }

    @NonNull
    @Override
    public EstadoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_historial, viewGroup,false);
        EstadoHolder holder = new EstadoHolder(v);
        return holder;
    }

    public void removeItem(int i){
        SensoresPojoList.remove(i);
        notifyDataSetChanged();
    }

    public void restoreItem (SensoresPojo sensoresPojo,int i){
        SensoresPojoList.add(i, sensoresPojo);
        notifyItemInserted(i);
    }


    @Override
    public void onBindViewHolder(@NonNull EstadoHolder estadoHolder, int i) {
        SensoresPojo sensoresPojo = SensoresPojoList.get(i);
        String fehca = "";
        // BLOQUEO DE PUERTAS
        if (sensoresPojo.getConfirmacion_activacion_rele_puerta() == null){// ROJO
            estadoHolder.cardview_1.setCardBackgroundColor(Color.rgb(235, 51, 5));
            estadoHolder.puertas.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.estado_puertas.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.fecha.setTextColor((Color.rgb(255, 255, 255)));
            estadoHolder.hora.setTextColor((Color.rgb(255, 255, 255)));
            estadoHolder.fecha_puertas.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.hora_puertas.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.puertas.setText("ESTADO DE BLOQUEO DE PUERTAS");
            estadoHolder.estado_puertas.setText("DESACTIVADO");
            estadoHolder.fecha_puertas.setText("00000000");
            estadoHolder.hora_puertas.setText("00000000");
        }else {// VERDE
            estadoHolder.cardview_1.setCardBackgroundColor(Color.rgb(18, 194, 53));
            estadoHolder.puertas.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.estado_puertas.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.fecha.setTextColor((Color.rgb(255, 255, 255)));
            estadoHolder.hora.setTextColor((Color.rgb(255, 255, 255)));
            estadoHolder.fecha_puertas.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.hora_puertas.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.puertas.setText("ESTADO BLOQUEO DE PUERTAS");
            estadoHolder.estado_puertas.setText("ACTIVADO");
            estadoHolder.fecha_puertas.setText(sensoresPojo.getConfirmacion_activacion_rele_puerta());
            estadoHolder.hora_puertas.setText(sensoresPojo.getHora_confirmacion_activacion_rele_puerta());
        }
        if (sensoresPojo.getConfirmacion_desbloqueo_rele_puertas() != null){// AMARILLO
            estadoHolder.cardview_1.setCardBackgroundColor(Color.rgb(221, 210, 0));
            estadoHolder.puertas.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.estado_puertas.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.fecha.setTextColor((Color.rgb(255, 255, 255)));
            estadoHolder.hora.setTextColor((Color.rgb(255, 255, 255)));
            estadoHolder.fecha_puertas.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.hora_puertas.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.puertas.setText("ESTADO DESBLOQUEO DE PUERTAS");
            estadoHolder.estado_puertas.setText("ACTIVADO");
            estadoHolder.fecha_puertas.setText(sensoresPojo.getConfirmacion_desbloqueo_rele_puertas());
            estadoHolder.hora_puertas.setText(sensoresPojo.getHora_confirmacion_desbloqueo_rele_puertas());
        }

        // BLOQUEO DE CORRIENTE
        if (sensoresPojo.getConfirmacion_activacion_rele_corriente() == null){// ROJO
            estadoHolder.cardview_2.setCardBackgroundColor(Color.rgb(235, 51, 5));
            estadoHolder.corriente.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.estado_corriente.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.fechac.setTextColor((Color.rgb(255, 255, 255)));
            estadoHolder.horac.setTextColor((Color.rgb(255, 255, 255)));
            estadoHolder.fecha_corriente.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.hora_corriente.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.corriente.setText("ESTADO BLOQUEO DE CORRIENTE");
            estadoHolder.estado_corriente.setText("DESACTIVADO");
            estadoHolder.fecha_corriente.setText("00000000");
            estadoHolder.hora_corriente.setText("00000000");
        }else {// VERDE
            estadoHolder.cardview_2.setCardBackgroundColor(Color.rgb(18, 194, 53));
            estadoHolder.corriente.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.estado_corriente.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.fechac.setTextColor((Color.rgb(255, 255, 255)));
            estadoHolder.horac.setTextColor((Color.rgb(255, 255, 255)));
            estadoHolder.fecha_corriente.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.hora_corriente.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.corriente.setText("ESTADO BLOQUEO DE CORRIENTE");
            estadoHolder.estado_corriente.setText("ACTIVADO");
            estadoHolder.fecha_corriente.setText(sensoresPojo.getConfirmacion_activacion_rele_corriente());
            estadoHolder.hora_corriente.setText(sensoresPojo.getHora_confirmacion_activacion_rele_corriente());
        }
        if (sensoresPojo.getConfirmacion_desbloqueo_rele_corriente() != null){// AMARILLO
            estadoHolder.cardview_2.setCardBackgroundColor(Color.rgb(221, 210, 0));
            estadoHolder.corriente.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.estado_corriente.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.fechac.setTextColor((Color.rgb(255, 255, 255)));
            estadoHolder.horac.setTextColor((Color.rgb(255, 255, 255)));
            estadoHolder.fecha_corriente.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.hora_corriente.setTextColor(Color.rgb(255, 255, 255));
            estadoHolder.corriente.setText("ESTADO DESBLOQUEO DE CORRIENTE");
            estadoHolder.estado_corriente.setText("ACTIVADO");
            estadoHolder.fecha_corriente.setText(sensoresPojo.getConfirmacion_desbloqueo_rele_corriente());
            estadoHolder.hora_corriente.setText(sensoresPojo.getHora_confirmacion_desbloqueo_rele_corriente());
        }
    }

    @Override
    public int getItemCount() {
        return SensoresPojoList.size();
    }


    public static class EstadoHolder extends RecyclerView.ViewHolder{
        TextView fecha_corriente, hora_corriente, estado_corriente, fechac, horac, corriente;
        TextView estado_puertas, fecha_puertas, hora_puertas, fecha, hora, puertas;
        RecyclerView recyclerView;
        CardView cardview_1, cardview_2;
        LinearLayout layoutABorrar;
        public EstadoHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.recycler);
            cardview_1 = (CardView)itemView.findViewById(R.id.cardview_1);
            cardview_2 = (CardView)itemView.findViewById(R.id.cardview_2);
            layoutABorrar = (LinearLayout)itemView.findViewById(R.id.layoutABorrar);

            // CORRIENTE
            corriente = (TextView)itemView.findViewById(R.id.corriente);
            horac = (TextView)itemView.findViewById(R.id.horac);
            fechac = (TextView)itemView.findViewById(R.id.fechac);
            estado_corriente = (TextView)itemView.findViewById(R.id.estado_corriente);
            fecha_corriente = (TextView)itemView.findViewById(R.id.fecha_corriente);
            hora_corriente = (TextView)itemView.findViewById(R.id.hora_corriente);

            // PUERTAS
            puertas = (TextView)itemView.findViewById(R.id.puertas);
            fecha = (TextView)itemView.findViewById(R.id.fecha);
            hora = (TextView)itemView.findViewById(R.id.hora);
            estado_puertas = (TextView)itemView.findViewById(R.id.estado_puertas);
            fecha_puertas = (TextView)itemView.findViewById(R.id.fecha_puertas);
            hora_puertas = (TextView)itemView.findViewById(R.id.hora_puertas);
        }
    }
}
