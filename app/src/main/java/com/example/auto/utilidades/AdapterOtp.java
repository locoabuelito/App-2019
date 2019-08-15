package com.example.auto.utilidades;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.auto.POJO.otpPojo;
import com.example.auto.R;

import java.util.ArrayList;

public class AdapterOtp extends RecyclerView.Adapter<AdapterOtp.AlertHolder> {

    ArrayList<otpPojo> otpPojoList;

    public AdapterOtp(ArrayList<otpPojo> otpPojoList) {
        this.otpPojoList = otpPojoList;
    }

    @NonNull
    @Override
    public AlertHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_otp, null, false);
        AlertHolder holder = new AlertHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlertHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class AlertHolder extends RecyclerView.ViewHolder {
        EditText codigo;

        public AlertHolder(@NonNull View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.codigo);
        }
    }
}
