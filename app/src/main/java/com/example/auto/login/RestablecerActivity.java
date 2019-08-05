package com.example.auto.login;

import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.auto.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestablecerActivity extends AppCompatActivity {
    private EditText email;
    private Button btn_restablecer;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restablecer);

        email = (EditText)findViewById(R.id.restablecer_email);
        btn_restablecer = (Button)findViewById(R.id.btn_restablecer);
        progressBar = (ProgressBar)findViewById(R.id.progreso_restablecer);
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("es-US");

        btn_restablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email_res = email.getText().toString().trim();
                if ((Patterns.EMAIL_ADDRESS.matcher(email_res).matches() == false) && (email_res.isEmpty())){
                    mensaje_email();
                    progressBar.setVisibility(View.GONE);
                }else {
                    mAuth.sendPasswordResetEmail(email_res).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mAuth.setLanguageCode("es-US");
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Se ha enviado un email a su cuenta para restablecer la contraseña!",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    mAuth.setLanguageCode("es-US");
                }
            }
        });
    }

    /////////////////////////////// mensaje_email ///////////////////////////////
    private void mensaje_email (){
        AlertDialog.Builder builder = new AlertDialog.Builder(RestablecerActivity.this);
        builder.setTitle("Mensaje de Error");
        builder.setMessage("Formato de email inválido. Verifique nuevamente!")
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
