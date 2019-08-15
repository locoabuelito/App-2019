package com.example.auto.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Bluetooth {
    @POST("activar_bluetooth")
    @FormUrlEncoded
    Observable<String> activar_bluetooth(@Field("nombre_bt_cel") String nombre_bt_cel);
}
