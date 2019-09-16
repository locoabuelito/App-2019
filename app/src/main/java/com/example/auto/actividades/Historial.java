package com.example.auto.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.util.Log;

import com.example.auto.Adaptadores.BluetoothAdapter;
import com.example.auto.POJO.BluetoothCheckActivacionPojo;
import com.example.auto.POJO.SensoresPojo;
import com.example.auto.R;
import com.example.auto.Adaptadores.AdapterEstadoSensores;
import com.example.auto.Retrofit.Interface.BluetoothAPI;
import com.example.auto.Retrofit.RetrofitClientBluetooth;
import com.example.auto.Adaptadores.RecyclerItemTouchHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static androidx.recyclerview.widget.ItemTouchHelper.*;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Historial extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private static final String TAG = "HistorialSensores";
    private final int ITEM_LOAD_COUNT = 21;
    int total_item = 0, last_visible_item;
    boolean isLoading = false, isMaxData = false;
    private String last_node="", last_key="";
    private ArrayList<SensoresPojo> SensoresPojoList = new ArrayList<>();
    private ArrayList<BluetoothCheckActivacionPojo> bluetoothCheckActivacionPojos = new ArrayList<>();

    AdapterEstadoSensores adapterEstadoSensores;
    BluetoothAdapter bluetoothAdapter;
    BluetoothAPI bluetooth;
    private RecyclerView recyclerView;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        recyclerView = findViewById(R.id.recycler);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("ESTADO DE ACTUADORES"); // set the top title

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // Firebase
        //adapterEstadoSensores = new AdapterEstadoSensores(SensoresPojoList);
        //recyclerView.setAdapter(adapterEstadoSensores);
        // Mysql
        bluetoothAdapter = new BluetoothAdapter(this, bluetoothCheckActivacionPojos);
        recyclerView.setAdapter(bluetoothAdapter);

        final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/");
        dataRef.keepSynced(true);

        //ObtenerConfirmacion();

        ItemTouchHelper.SimpleCallback simpleCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT|RIGHT, Historial.this);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        // Inicializar API
        Retrofit retrofit = RetrofitClientBluetooth.getInstance();
        bluetooth = retrofit.create(BluetoothAPI.class);

        // Rellenar recycler
        fetchData();


    }

    private void fetchData() {
        compositeDisposable.add(bluetooth.getBluetooth()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<BluetoothCheckActivacionPojo>>() {
                    @Override
                    public void accept(ArrayList<BluetoothCheckActivacionPojo> getBluetooths) throws Exception {
                        BluetoothAdapter bluetoothAdapter = new BluetoothAdapter(Historial.this, getBluetooths);
                        Log.d(TAG, "Mysql: " + getBluetooths);
                        recyclerView.setAdapter(bluetoothAdapter);
                    }
                }));
    }

    private void ObtenerConfirmacion() {
        final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/");


        dataRef.child("seccion_horarios").limitToLast(ITEM_LOAD_COUNT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    dataRef.child("seccion_horarios").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //SensoresPojoList.clear();
                            for (DataSnapshot snapshot1: dataSnapshot.getChildren()){
                                SensoresPojo sensoresPojo = dataSnapshot.getValue(SensoresPojo.class);
                                SensoresPojoList.add(sensoresPojo);

                                Log.d(TAG, "onDataChange: getConfirmacion_activacion_rele_puerta: "+sensoresPojo.getConfirmacion_activacion_rele_puerta());
                                Log.d(TAG, "onDataChange: getConfirmacion_desbloqueo_rele_puertas: "+sensoresPojo.getConfirmacion_desbloqueo_rele_puertas());
                            }
                            adapterEstadoSensores.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof AdapterEstadoSensores.EstadoHolder){
            adapterEstadoSensores.removeItem(viewHolder.getAdapterPosition());
        }
    }
}
