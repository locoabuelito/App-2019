package com.example.auto.actividades;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auto.R;
import com.example.auto.Retrofit.Interface.BluetoothAPI;
import com.example.auto.Retrofit.RetrofitClient;
import com.example.auto.Adaptadores.DeviceListAdapter;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class BluetoothActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_ENABLE_VISIBILIDAD = -1;
    private String TAG = "Actividad de BluetoothAPI";
    int i, j = 0;
    private TextView name_rpi, name_bluetooth;
    private Button btn_activar, btn_desactivar, btn_conectar;
    private BluetoothAdapter bluetoothAdapter; // Representa el adaptador local de BluetoothAPI
    public ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    public DeviceListAdapter mDeviceListAdapter;
    ListView listView;


    // BluetoothAPI Mysql
    BluetoothAPI bluetooth;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        compositeDisposable.clear();
        super.onDestroy();
        unregisterReceiver(HabilitarBTReceiver);
        unregisterReceiver(VisibilidadReceiver);
        unregisterReceiver(DescubrirBTReceiver);
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
                        Log.d(TAG, "HabilitarBTReceiver: ESTADO BLUETOOTH - APAGADO ");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "HabilitarBTReceiver: ESTADO BLUETOOTH - APAGANDO ");
                        name_bluetooth.setText("");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "HabilitarBTReceiver: ESTADO BLUETOOTH - ENCENDIDO");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "HabilitarBTReceiver: ESTADO BLUETOOTH - ENCENDIENDO ");
                        break;
                }
            }
        }
    };

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver VisibilidadReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) { // Pueden escuchar para recibir notificaciones globales cada vez que cambie el modo de escaneo
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE: // Permite a los dispositivos remotos ver este adaptador BluetoothAPI cuando realizan un descubrimiento.
                        Log.d(TAG, "VisibilidadReceiver: VISIBILIDAD ACTIVADA");
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE: // no se puede detectar desde dispositivos BluetoothAPI remotos,
                        // pero se puede conectar desde dispositivos remotos que han descubierto previamente este dispositivo.
                        Log.d(TAG, "VisibilidadReceiver: VISIBILIDAD DESCONECTADA. DISPONIBLE PARA RECIBIR CONEXIONES");
                        name_bluetooth.setText("");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE: // Indica que tanto el escaneo de consultas como el escaneo de páginas están deshabilitados en el adaptador BluetoothAPI local.
                        // Por lo tanto, este dispositivo no es reconocible ni conectable desde dispositivos BluetoothAPI remotos.
                        Log.d(TAG, "VisibilidadReceiver: VISIBILIDAD DESCONECTADA. NO DISPONIBLE PARA RECIBIR CONEXIONES.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "VisibilidadReceiver: CONECTANDO");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "VisibilidadReceiver: CONECTADO");
                        break;
                }
            }
        }
    };


    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver DescubrirBTReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACCION ENCONTRADA");
            // When discovery finds a device
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                bluetoothDevices.add(device);
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                name_rpi.setText(device.getName());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().hide(); //
        // BluetoothAPI Mysql
        Retrofit retrofit = RetrofitClient.getInstance();
        bluetooth = retrofit.create(BluetoothAPI.class);


        name_bluetooth = findViewById(R.id.txt_bluetooth_name);
        name_rpi = findViewById(R.id.txt_bluetooth_name_rpi);

        btn_activar = findViewById(R.id.btn_bluetooth);
        btn_desactivar = findViewById(R.id.btn_blue);
        btn_conectar = findViewById(R.id.btn_buscar);

        listView = findViewById(R.id.lvNewDevices);
        bluetoothDevices = new ArrayList<>();

        BotonActivar();
        //BotonConectar();
        BotonDesactivar();
    }
    /*
    private void BotonConectar(){
        btn_conectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DescubrirBT();
            }
        });
    }*/

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

    private void HabilitarBT(){
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            Log.d(TAG, "HabilitarBT: Does not have BT capabilities.");
        }
        if(!bluetoothAdapter.isEnabled()){
            Log.d(TAG, "HabilitarBT: HABILITANDO BT.");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            IntentFilter HabilitarBTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(HabilitarBTReceiver, HabilitarBTIntent);
        }else {
            String nombre_bt_cel = bluetoothAdapter.getName();
            name_bluetooth.setText(nombre_bt_cel);
            VisibilidadBT();
        }

    }

    private void VisibilidadBT() {
        Log.d(TAG, "VisibilidadBT: Visible para dispositivos por 6 minutos");
        // Habilitacion de visibilidad
        Intent visibilidadBT = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        visibilidadBT.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivityForResult(visibilidadBT, REQUEST_ENABLE_VISIBILIDAD);
        // Broadcast para visibilidad
        IntentFilter VisibilidadFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(VisibilidadReceiver, VisibilidadFilter);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        name_bluetooth.setText(bluetoothAdapter.getName());
    }

    private void Activar(String nombre_bt_cel) {
        Toast.makeText(BluetoothActivity.this, "BT " + nombre_bt_cel, Toast.LENGTH_LONG).show();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        name_bluetooth.setText(bluetoothAdapter.getName());
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
            name_bluetooth.setText("");
            name_rpi.setText("");
        }
    }

    private void DescubrirBT() {
        Log.d(TAG, "DescubrirBT: Buscando dispositivos");

        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // Cancelar la busqueda
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "DescubrirBT: Cancelando busqueda");

            checkBTPermiso();

            bluetoothAdapter.startDiscovery();
            IntentFilter DescubrirBTIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(DescubrirBTReceiver, DescubrirBTIntent);
        }

        if (!bluetoothAdapter.isDiscovering()) {

            checkBTPermiso();

            bluetoothAdapter.startDiscovery();
            IntentFilter DescubrirBTIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(DescubrirBTReceiver, DescubrirBTIntent);
        }

    }

    private void checkBTPermiso() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    name_bluetooth.setText(bluetoothAdapter.getName());
                    String nombre_bt_cel = name_bluetooth.getText().toString().trim();
                    Activar(nombre_bt_cel);
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    name_bluetooth.setText("");
                    btn_activar.setText("ACTIVAR BLUETOOTH");
                    DesHabilitarBT();
                }

            case REQUEST_ENABLE_VISIBILIDAD:
                if (resultCode == Activity.RESULT_OK) {
                    DescubrirBT();
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(BluetoothActivity.this, "PELIGRO: La alarma no podra activar!", Toast.LENGTH_LONG).show();
                }
        }
    }

}
