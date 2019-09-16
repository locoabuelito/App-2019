package com.example.auto.menu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.auto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OtpAutomatico extends AppCompatDialogFragment {
    private EditText codigo;
    private OtpListener listener;
    private Button verificar;
    private final DatabaseReference dataRefModo = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/modo/codigo");
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_otp_automatico, null);
        codigo = view.findViewById(R.id.codigo_automatico);
        builder.setView(view)
                .setTitle("Codigo de Verificación - AUTOMÁTICO");
        verificar = view.findViewById(R.id.btn_verificar_automatico);
        Verificar();
        return builder.create();
    }

    private void Verificar() {
        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code_otp = codigo.getText().toString().trim();
                listener.AapplyTexts(code_otp);
                //VerificadorOtp(code_otp);
                dismiss();
            }
        });
    }

    private void VerificadorOtp(String code) {

        dataRefModo.child("automatico").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String codes = dataSnapshot.getValue().toString().trim();
                if (code.equals(codes)) {
                    Intent i = new Intent(getActivity(), OpcionesBloqueo.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Código incorrecto. Verifique nuevamente!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OtpListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "Debe implementar OtpListener");
        }
    }

    public interface OtpListener{
        void AapplyTexts(String code);
    }
}
