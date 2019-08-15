package com.example.auto.login;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auto.Retrofit.Registro;
import com.example.auto.Retrofit.RetrofitClient;
import com.example.auto.menu.Menu_v2;
import com.example.auto.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_ENABLE_BT = 1; // Valor de la funcion que identifica a la actividad, para pasar a onActivityResult()
    private EditText email, password;
    private TextView registrar, restablecer;
    private ProgressBar progressBar;
    private Button Iniciar, Bluetooth;
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabase;
    boolean cor = false, pas = false;
    private String error_correo = "Correo invalido!";
    private BluetoothAdapter bluetoothAdapter;

    Registro registro;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxJavaPlugins.setErrorHandler(throwable -> {});
        getSupportActionBar().hide();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registrar = findViewById(R.id.nueva_cuenta);
        Iniciar = findViewById(R.id.btn_iniciar_sesion);
        //restablecer = (TextView)findViewById(R.id.restablecer);
        progressBar = findViewById(R.id.progreso_restablecer);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mAuth = FirebaseAuth.getInstance();

        Retrofit retrofit = RetrofitClient.getInstance();
        registro = retrofit.create(Registro.class);
        estado_conexion();/*
        restablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RestablecerActivity.class));
            }
        });*/


    }

    private void IniciarConInternet() {
        Iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (internet()) {
                    validar();
                } else {
                    estado_conexion();
                }
            }
        });
    }

    private void BotonRegistrarConInternet() {
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                progressBar.setVisibility(View.VISIBLE);
                if (internet()){
                    Intent i = new Intent(MainActivity.this, RegistrarActivity.class);
                    startActivity(i);
                    finish();
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(MainActivity.this,"No se ha podido establecer la conexión a Internet. Verifique su red!",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private  void BotonRegistrarSinInternet(){
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrarActivity.class);
                startActivity(i);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /////////////////////////////// firebase_inicio ///////////////////////////////
    private void firebase_inicio(){
        String email_val = email.getText().toString().trim();
        String password_val = password.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email_val, password_val)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null ) {
            if (currentUser.isEmailVerified()) {
                mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("usuario");
                mFirebaseDatabase.child(currentUser.getProviderId()).setValue(currentUser.getEmail());
                progressBar.setVisibility(View.VISIBLE);
                Intent i = new Intent(MainActivity.this, Menu_v2.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Email no verificado!", Toast.LENGTH_LONG).show();
            }
        }
    }

    /////////////////////////////// mensaje_email ///////////////////////////////
    private void mensaje_email (){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Mensaje de Error");
        builder.setMessage("Campos vacios. Verifique nuevamente!")
                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
    /////////////////////////////// validar  con internet/////////////////////////
    private void validar() {
        String email_val = email.getText().toString().trim();
        String password_val = password.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if ((Patterns.EMAIL_ADDRESS.matcher(email_val).matches() == false) && (email_val.isEmpty())) {
            mensaje_email();
            progressBar.setVisibility(View.GONE);
        } else if ((password_val.isEmpty())) {
            mensaje_tamaño();
            progressBar.setVisibility(View.GONE);
        } else {
            firebase_inicio();
        }
    }
    /////////////////////////////// validar  sin internet/////////////////////////
    private void validarSinInternet() {
        String email_val = email.getText().toString().trim();
        String password_val = password.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if ((Patterns.EMAIL_ADDRESS.matcher(email_val).matches() == false) && (email_val.isEmpty())) {
            mensaje_email();
            progressBar.setVisibility(View.GONE);
        } else if ((password_val.isEmpty())) {
            mensaje_tamaño();
            progressBar.setVisibility(View.GONE);
        } else {
            LoginUser(email_val,  password_val);
            progressBar.setVisibility(View.GONE);
        }
    }

    private boolean internet(){
        boolean wifi = false;
        boolean movil = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo [] networkInfos = cm.getAllNetworkInfo();

        for (NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI")){
                if (info.isConnected() )
                    wifi = true;
            }
            if (info.getTypeName().equalsIgnoreCase("MOBILE")){
                if (info.isConnected())
                    movil = true;
            }
        }
        return movil || wifi;

    }


    private void IniciarSinInternet() {
        Iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarSinInternet();
            }
        });
    }

    private void estado_conexion(){
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean connected = dataSnapshot.getValue(Boolean.class);
                if (connected){
                    IniciarConInternet();
                    BotonRegistrarConInternet();
                }else{
                    IniciarSinInternet();
                    BotonRegistrarSinInternet();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Listener was cancelled");
            }
        });
    }

    private void LoginUser(String correo, String password){
        compositeDisposable.add(registro.login(correo, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("password")){
                            Toast.makeText(MainActivity.this, "Inicio de sesion con exitos!",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Usuario no registrado!",Toast.LENGTH_LONG).show();
                        }
                    }
                }));
    }
}
