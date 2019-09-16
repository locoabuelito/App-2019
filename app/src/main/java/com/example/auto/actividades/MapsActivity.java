package com.example.auto.actividades;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.example.auto.R;
import com.example.auto.POJO.MapsPojo;
import com.example.auto.POJO.MapsPojoAuto;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference databaseReference;
    private ArrayList<Marker> tmpRealTimeMarkers = new ArrayList<>();
    private ArrayList<Marker> RealTimeMarkers = new ArrayList<>();
    private ArrayList<Marker> tmpRealTimeMarkersAuto = new ArrayList<>();
    private ArrayList<Marker> RealTimeMarkersAuto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("usuario");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Ubicacion Usuario
        databaseReference.child("seccion_ubicacion_usuario").addValueEventListener(new ValueEventListener() {
            Circle circle;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (Marker marker: RealTimeMarkers){
                    marker.remove();
                }
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    MapsPojo mp = dataSnapshot.getValue(MapsPojo.class);
                    Double Latitud = mp.getLatitud();
                    Double Longitud = mp.getLongitud();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.draggable(true).position(new LatLng(Latitud, Longitud)).title("MI UBICACIÓN");
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitud, Longitud), 17f));
                    tmpRealTimeMarkers.add(mMap.addMarker(markerOptions));
                    //circle = drawCircle(new LatLng(Latitud,Longitud));
                }
                RealTimeMarkers.clear();
                RealTimeMarkers.addAll(tmpRealTimeMarkers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Ubicacion Auto
        databaseReference.child("seccion_ubicacion_auto").addValueEventListener(new ValueEventListener() {
            Circle circle;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (Marker marker: RealTimeMarkersAuto){
                    marker.remove();

                }
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    MapsPojoAuto mpa = dataSnapshot.getValue(MapsPojoAuto.class);
                    Double Latitud = mpa.getLatitud();
                    Double Longitud = mpa.getLongitud();
                    MarkerOptions markerOptionsAuto = new MarkerOptions();
                    markerOptionsAuto.draggable(true).position(new LatLng(Latitud,Longitud)).title("UBICACIÓN AUTO").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    tmpRealTimeMarkersAuto.add(mMap.addMarker(markerOptionsAuto));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitud,Longitud), 17f));
                }
                RealTimeMarkersAuto.clear();
                RealTimeMarkersAuto.addAll(tmpRealTimeMarkersAuto);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    /*
    private Circle drawCircle(LatLng latLng){
        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)
                .radius(100)
                .fillColor(0x33FF0000)
                .strokeColor(Color.BLUE)
                .strokeWidth(3);
        return mMap.addCircle(circleOptions);
    }*/
}
