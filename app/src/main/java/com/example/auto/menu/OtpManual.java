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
import android.widget.EditText;
import android.widget.Toast;

import com.example.auto.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OtpManual extends AppCompatDialogFragment {
    private EditText codigo;
    private OtpListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_otp, null);
        codigo = view.findViewById(R.id.codigo);
        builder.setView(view)
                .setTitle("Codigo de Verificación - MANUAL")
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        VerificadorOtp();

                    }
                });
        return builder.create();
    }

    private void VerificadorOtp() {
        String code = codigo.getText().toString().trim();
        if (code.equals("12345")){
            Toast.makeText(getActivity(),"Codigo correcto",Toast.LENGTH_LONG).show();
            final DatabaseReference dataRefModo = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/modo");
            dataRefModo.child("activado").setValue(false);
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
