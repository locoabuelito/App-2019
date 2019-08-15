package com.example.auto.menu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.auto.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OtpAutomatico extends AppCompatDialogFragment {
    private EditText codigo;
    private OtpListener listener;
    private Button verificar;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_otp, null);
        codigo = view.findViewById(R.id.codigo);
        builder.setView(view)
                .setTitle("Codigo de Verificación - AUTOMÁTICO");
        verificar = view.findViewById(R.id.btn_verificar);
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
