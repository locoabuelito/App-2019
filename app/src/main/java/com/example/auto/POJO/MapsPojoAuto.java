package com.example.auto.POJO;

public class MapsPojoAuto {
    private double Latitud;
    private double Longitud;

    public MapsPojoAuto(){}
    public double getLatitud() {
        return (double) Latitud;
    }

    public void setLatitud(double latitud) {
        Latitud = latitud;
    }

    public double getLongitud() {
        return (double) Longitud;
    }

    public void setLongitud(float longitud) {
        Longitud = longitud;
    }
}
