package com.example.auto.Retrofit.Interface;

import io.reactivex.Observable;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegistroAPI {
    @POST("registrar")
    @FormUrlEncoded
    Observable<String> registrar(@Field("correo") String correo, @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Observable<String> login(@Field("correo") String correo, @Field("password") String password);
}
