package com.example.auto.utilidades;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.auto.POJO.otpPojo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Utilidades {
    public static int rotacion = 0;
    public static boolean validarPantalla = true;
    public static boolean validarAutomatico = true;
    public static boolean validarPress = true;
    public static boolean validarBoton = true;
    public static boolean otpB = true;
    public static String code;
}
/*
    private final BroadcastReceiver DiscoverableReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothViewHolder.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothViewHolder.EXTRA_SCAN_MODE, BluetoothViewHolder.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothViewHolder.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "DiscoverableReceiver: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothViewHolder.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "DiscoverableReceiver: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothViewHolder.SCAN_MODE_NONE:
                        Log.d(TAG, "DiscoverableReceiver: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothViewHolder.STATE_CONNECTING:
                        Log.d(TAG, "DiscoverableReceiver: Connecting....");
                        break;
                    case BluetoothViewHolder.STATE_CONNECTED:
                        Log.d(TAG, "DiscoverableReceiver: Connected.");
                        break;
                }

            }
        }
    };

    private final BroadcastReceiver PairReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d(TAG, "PairReceiver: BOND_BONDED.");
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "PairReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "PairReceiver: BOND_NONE.");
                }
            }
        }
    };

    private BroadcastReceiver DiscoverReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
        }
    };
    */