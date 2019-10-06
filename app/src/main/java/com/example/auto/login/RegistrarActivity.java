package com.example.auto.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.auto.Retrofit.Interface.RegistroAPI;
import com.example.auto.Retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegistrarActivity extends AppCompatActivity {
    RegistroAPI registro;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/telefono/");

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    private static final int VERIFICAR_EMAIL = 1;
    private EditText email_registrar, password_registrar, password_registrar_2, telf;
    private Button Btn_registrar;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    boolean cor = false, pas = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        Retrofit retrofit = RetrofitClient.getInstance();
        registro = retrofit.create(RegistroAPI.class);

        email_registrar = findViewById(R.id.email_registrar);
        password_registrar = findViewById(R.id.password_registrar);
        password_registrar_2 = findViewById(R.id.password_registrar_2);
        telf = findViewById(R.id.txt_telefono);
        progressBar = findViewById(R.id.progreso_restablecer);
        Btn_registrar = findViewById(R.id.btn_registrar);

        mAuth = FirebaseAuth.getInstance();
        estado_conexion ();

    }

    private void estado_conexion (){
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean connected = dataSnapshot.getValue(Boolean.class);
                if (connected){
                    RegistrarConInternet();
                }else{
                    RegistrarSinInternet();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Listener was cancelled");
            }
        });
    }
    /////////////////////////////// Registrar con internet/////////////////////////
    private void RegistrarConInternet() {
        Btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarConInternet();
            }
        });
    }
    /////////////////////////////// Registrar  sin internet/////////////////////////
    private void RegistrarSinInternet() {
        Btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarSinInternet();
            }
        });
    }
    /////////////////////////////// firebase_registro ///////////////////////////////
    private void firebase_registro(){
        String email = email_registrar.getText().toString().trim();
        String pass = password_registrar.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.VISIBLE);
                if (task.isSuccessful()){
                    final FirebaseUser user = mAuth.getCurrentUser();
                    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Se ha enviado un email de verificación a su cuenta para tener acceso a la aplicación!",Toast.LENGTH_LONG).show();
                                firebaseDatabase.push().child(user.getProviderId()).setValue(user.getEmail());
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(RegistrarActivity.this, MainActivity.class));
                                finish();
                            }else {
                                Toast.makeText(RegistrarActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(RegistrarActivity.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
    /////////////////////////////// validar con internet///////////////////////////////
    private void validarConInternet(){
        String email = email_registrar.getText().toString().trim();
        String pass = password_registrar.getText().toString().trim();
        String pass_conf = password_registrar_2.getText().toString().trim();
        int telefono = Integer.parseInt(telf.getText().toString().trim());

        dataRef.setValue(telefono);
        if ((Patterns.EMAIL_ADDRESS.matcher(email).matches() == false) && (email.isEmpty())){
            mensaje_email();
        }else if ((pass.length() <7 && pass_conf.length() <7) || (pass.isEmpty() && pass_conf.isEmpty())){
            mensaje_tamaño();
        }else if (!pass.equals(pass_conf)){
            mensaje_veri();
        }else {
            firebase_registro();
        }

    }
    /////////////////////////////// validar sin internet///////////////////////////////
    private void validarSinInternet() {
        String email_val = email_registrar.getText().toString().trim();
        String password_val = password_registrar.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if ((Patterns.EMAIL_ADDRESS.matcher(email_val).matches() == false) && (email_val.isEmpty())) {
            mensaje_email();
            progressBar.setVisibility(View.GONE);
        } else if ((password_val.isEmpty())) {
            mensaje_tamaño();
            progressBar.setVisibility(View.GONE);
        } else {
            registerUser(email_val,  password_val);
        }
    }

    private void registerUser(String email, String password) {
        compositeDisposable.add(registro.registrar(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    Intent i = new Intent(RegistrarActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }));
    }

    /////////////////////////////// mensaje_email ///////////////////////////////
    private void mensaje_email (){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarActivity.this);
        builder.setTitle("Mensaje de Error");
        builder.setMessage("Formato de email inválido. Verifique nuevamente!")
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
    /////////////////////////////// mensaje_tamaño ///////////////////////////////
    private void mensaje_tamaño (){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarActivity.this);
        builder.setTitle("Mensaje de Error");
        builder.setMessage("Caracteres mínimos inválidos o campos vacios. Verifique nuevamente!")
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
    /////////////////////////////// mensaje_veri ///////////////////////////////
    private void mensaje_veri (){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarActivity.this);
        builder.setTitle("Mensaje de Error");
        builder.setMessage("Las contraseñas ingresadas no coinciden. Verifique nuevamente!")
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
    /////////////////////////////// mensaje_sin_internet ///////////////////////////////
    private void mensaje_sin_internet (){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarActivity.this);
        builder.setTitle("Error de conexión");
        builder.setMessage("No se ha podido establecer la conexión a Internet. Verifique nuevamente!")
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
    /////////////////////////////// conectividad ///////////////////////////////
    private boolean isOnline(){
        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean internet(){
        boolean wifi = false;
        boolean movil = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();

        for (NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI") && isOnline()){
                if (info.isConnected())
                    wifi = true;
            }
            if (info.getTypeName().equalsIgnoreCase("MOBILE") && isOnline()){
                if (info.isConnected())
                    movil = true;
            }
        }
        return movil || wifi;
    }

}
