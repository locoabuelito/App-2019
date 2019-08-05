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
import android.widget.ListView;
import android.widget.TextView;

import com.example.auto.POJO.Rpi;
import com.example.auto.R;
import com.example.auto.utilidades.DeviceListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BluetoothActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private String TAG;
    private String name ,rpiw;
    int i, j = 0;
    private TextView name_bluetooth, name_rpi;
    private Button btn_activar;
    private BluetoothAdapter bluetoothAdapter; // Representa el adaptador local de Bluetooth
    private boolean activar;
    public DeviceListAdapter mDeviceListAdapter;
    ListView lvNewDevices;

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver HabilitarBTReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(bluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, bluetoothAdapter.ERROR);

                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "HabilitarBTReceiver: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "HabilitarBTReceiver: ESTADO APAGANDO OFF");
                        final DatabaseReference dataRefBT = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_bluetooth");
                        dataRefBT.child("nombre").setValue("");
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
        getSupportActionBar().setTitle("BLOQUEO POR BLUETOOTH"); // set the top title
        name_bluetooth = (TextView) findViewById(R.id.txt_bluetooth_name);
        name_rpi = (TextView)findViewById(R.id.txt_bluetooth_name_rpi);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btn_activar = (Button)findViewById(R.id.btn_bluetooth);
        BotonPresionado();
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

    private void BotonPresionado() {
        btn_activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                j++;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (j == 1){
                            HabilitarBT();
                            btn_activar.setText("DESACTIVAR BLUETOOTH");
                        }else if (j ==2){
                            DesHabilitarBT();
                            btn_activar.setText("ACTIVAR BLUETOOTH");
                            j =0;
                        }
                    }
                },1);
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
            name = bluetoothAdapter.getName();
            name_bluetooth.setText(name);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK){
                    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    name = bluetoothAdapter.getName();
                    final DatabaseReference dataRefBT = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_bluetooth");
                    dataRefBT.child("nombre").setValue(name);
                    name_bluetooth.setText(name);
                    ObtenerPlaca();
                }else {
                    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    name = bluetoothAdapter.getName();
                    final DatabaseReference dataRefBT = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/seccion_bluetooth");
                    dataRefBT.child("nombre").setValue("");
                    name_bluetooth.setText("");
                }
        }
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
