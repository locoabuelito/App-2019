package com.example.auto.Retrofit.Interface;

import com.example.auto.POJO.BluetoothCheckActivacionPojo;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BluetoothAPI {
    @POST("activar_bluetooth")
    @FormUrlEncoded
    Observable<String> activar_bluetooth(@Field("nombre_bt_cel") String nombre_bt_cel);

    @GET("confirmacion_activacion_rele")
    Observable<ArrayList<BluetoothCheckActivacionPojo>> getBluetooth();
}
