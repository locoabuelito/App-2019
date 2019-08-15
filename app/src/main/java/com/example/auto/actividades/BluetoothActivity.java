package com.example.auto.actividades;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auto.POJO.Rpi;
import com.example.auto.R;
import com.example.auto.Retrofit.Bluetooth;
import com.example.auto.Retrofit.Registro;
import com.example.auto.Retrofit.RetrofitClient;
import com.example.auto.utilidades.DeviceListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class BluetoothActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private String TAG;
    int i, j = 0;
    private TextView name_rpi, name_bluetooth;
    private Button btn_activar, btn_desactivar;
    private BluetoothAdapter bluetoothAdapter; // Representa el adaptador local de Bluetooth
    public DeviceListAdapter mDeviceListAdapter;
    private boolean isActive = true;

    // Bluetooth Mysql
    Bluetooth bluetooth;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

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


    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver HabilitarBTReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "HabilitarBTReceiver: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "HabilitarBTReceiver: ESTADO APAGANDO OFF");
                        name_bluetooth.setText("");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "HabilitarBTReceiver: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "HabilitarBTReceiver: ESTADO ENCENDIDO ON");
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().hide(); //
        // Bluetooth Mysql
        Retrofit retrofit = RetrofitClient.getInstance();
        bluetooth = retrofit.create(Bluetooth.class);

        name_bluetooth = findViewById(R.id.txt_bluetooth_name);
        name_rpi = findViewById(R.id.txt_bluetooth_name_rpi);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btn_activar = findViewById(R.id.btn_bluetooth);
        btn_desactivar = findViewById(R.id.btn_blue);

        BotonActivar();
        BotonDesactivar();

    }

    private void BotonDesactivar() {
        btn_desactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DesHabilitarBT();
            }
        });
    }

    private void BotonActivar() {
        btn_activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HabilitarBT();
            }
        });
    }

    private void ObtenerPlaca() {
        final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/");
        dataRef.child("seccion_bluetooth/rpi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Rpi rpi = ds.getValue(Rpi.class);
                    Log.d(TAG, "onDataChange: "+rpi.getNombre_placa());
                    name_rpi.setText(rpi.getNombre_placa());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void HabilitarBT(){
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            Log.d(TAG, "HabilitarBT: Does not have BT capabilities.");
        }
        if(!bluetoothAdapter.isEnabled()){
            Log.d(TAG, "HabilitarBT: enabling BT.");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            IntentFilter HabilitarBTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(HabilitarBTReceiver, HabilitarBTIntent);
        }else {
            String nombre_bt_cel = bluetoothAdapter.getName();
            name_bluetooth.setText(nombre_bt_cel);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK){
                    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    name_bluetooth.setText(bluetoothAdapter.getName());
                    String nombre_bt_cel = name_bluetooth.getText().toString().trim();
                    Activar(nombre_bt_cel);

                }else {
                    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    String nombre_bt_cel = bluetoothAdapter.getName();
                    name_bluetooth.setText("");
                    btn_activar.setText("ACTIVAR BLUETOOTH");
                }
        }
    }

    private void Activar(String nombre_bt_cel) {
        Toast.makeText(BluetoothActivity.this, "BT " + nombre_bt_cel, Toast.LENGTH_LONG).show();
        compositeDisposable.add(bluetooth.activar_bluetooth(nombre_bt_cel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(BluetoothActivity.this, "" + s, Toast.LENGTH_LONG).show();
                    }
                }));

    }

    private void DesHabilitarBT(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            Log.d(TAG, "HabilitarBT: Does not have BT capabilities.");
        }
        if(bluetoothAdapter.isEnabled()){
            bluetoothAdapter.disable();
            final DatabaseReference dataRefBT = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_bluetooth/");
            dataRefBT.child("nombre").setValue("");
            name_bluetooth.setText("");
            name_rpi.setText("");
        }
    }


}
