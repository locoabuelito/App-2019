package com.example.auto.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.auto.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class OtpManual extends AppCompatDialogFragment {

    private static final String TAG = "OtpManual";
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 0;
    private TextInputEditText codigo;
    private Button verificar;
    private OtpListener listener;
    private final DatabaseReference dataRefModo = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/modo/codigo");

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Se crea el cuadro
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Se rellena el cuadro
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_otp, null);

        verificar = view.findViewById(R.id.btn_verificar);

        builder.setView(view)
                .setTitle(getResources().getString(R.string.titulo_manual));
        codigo = view.findViewById(R.id.codigo);
        Verificar();
        return builder.create();


    }

    private void Verificar() {
        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code_otp = codigo.getText().toString().trim();
                listener.applyTexts(code_otp);
                VerificadorOtp(code_otp);
            }
        });
    }

    private void VerificadorOtp(String code) {

        if (code.equals("12345")){
            Toast.makeText(getActivity(),"Codigo correcto",Toast.LENGTH_LONG).show();
            Intent i = new Intent(getActivity(), OpcionesBloqueo.class);
            startActivity(i);
        }else {
            Toast.makeText(getActivity(),"Codigo incorrecto",Toast.LENGTH_LONG).show();
        }
    }

    /*
    private void VerificadorOtp(String code) {

        dataRefModo.child("manual").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    otpPojo otpManual = dataSnapshot.getValue(otpPojo.class);
                    String otp = dataSnapshot.child("manual").getValue().toString();
                    if (!code.isEmpty() && code.equals(otpManual.getManual())){
                        Log.d(TAG, "onDataChange: "+otp);
                        Intent i = new Intent(getContext(), OpcionesBloqueo.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(getContext(), "Código ingresado incorrectamente!"+code, Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }*/

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

        void applyTexts(String codigo);
    }

}
