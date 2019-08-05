package com.example.auto.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.util.Log;

import com.example.auto.POJO.SensoresPojo;
import com.example.auto.R;
import com.example.auto.utilidades.AdapterEstadoSensores;
import com.example.auto.utilidades.RecyclerItemTouchHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static androidx.recyclerview.widget.ItemTouchHelper.*;

import java.util.ArrayList;

public class Historial extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private static final String TAG = "HistorialSensores";
    private final int ITEM_LOAD_COUNT = 21;
    int total_item = 0, last_visible_item;
    boolean isLoading = false, isMaxData = false;
    private String last_node="", last_key="";
    private ArrayList<SensoresPojo> SensoresPojoList = new ArrayList<>();
    AdapterEstadoSensores adapterEstadoSensores;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        recyclerView = (RecyclerView)findViewById(R.id.recycler);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("ESTADO DE ACTUADORES"); // set the top title

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapterEstadoSensores = new AdapterEstadoSensores(SensoresPojoList);
        recyclerView.setAdapter(adapterEstadoSensores);

        final DatabaseReference dataRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://app-2019-89860.firebaseio.com/usuario/");
        dataRef.keepSynced(true);

        ObtenerConfirmacion();


        ItemTouchHelper.SimpleCallback simpleCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT|RIGHT, Historial.this);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

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
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof AdapterEstadoSensores.EstadoHolder){
            adapterEstadoSensores.removeItem(viewHolder.getAdapterPosition());
        }
    }
}
