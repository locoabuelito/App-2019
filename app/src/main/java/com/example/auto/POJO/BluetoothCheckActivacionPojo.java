package com.example.auto.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BluetoothCheckActivacionPojo {

    //@SerializedName("confirmacion_activacion_rele_corriente")
    //@Expose
    private String confirmacion_activacion_rele_corriente;

    //@SerializedName("confirmacion_activacion_rele_puerta")
    //@Expose
    private String confirmacion_activacion_rele_puerta;

    //@SerializedName("confirmacion_desbloqueo_rele_corriente")
    //@Expose
    private String confirmacion_desbloqueo_rele_corriente;

    //@SerializedName("confirmacion_desbloqueo_rele_puertas")
    //@Expose
    private String confirmacion_desbloqueo_rele_puertas;

    //@SerializedName("hora_confirmacion_activacion_rele_corriente")
    //@Expose
    private String hora_confirmacion_activacion_rele_corriente;

    //@SerializedName("hora_confirmacion_activacion_rele_puerta")
    //@Expose
    private String hora_confirmacion_activacion_rele_puerta;

    //@SerializedName("hora_confirmacion_desbloqueo_rele_corriente")
    //@Expose
    private String hora_confirmacion_desbloqueo_rele_corriente;

    //@SerializedName("hora_confirmacion_desbloqueo_rele_puertas")
    //@Expose
    private String hora_confirmacion_desbloqueo_rele_puertas;


    public BluetoothCheckActivacionPojo() {
    }


    public BluetoothCheckActivacionPojo(String hora_confirmacion_activacion_rele_corriente, String hora_confirmacion_activacion_rele_puerta, String hora_confirmacion_desbloqueo_rele_corriente, String hora_confirmacion_desbloqueo_rele_puertas,
                                        String confirmacion_activacion_rele_corriente, String confirmacion_activacion_rele_puerta, String confirmacion_desbloqueo_rele_corriente, String confirmacion_desbloqueo_rele_puertas) {
        this.confirmacion_activacion_rele_corriente = confirmacion_activacion_rele_corriente;
        this.confirmacion_activacion_rele_puerta = confirmacion_activacion_rele_puerta;
        this.confirmacion_desbloqueo_rele_puertas = confirmacion_desbloqueo_rele_puertas;
        this.confirmacion_desbloqueo_rele_corriente = confirmacion_desbloqueo_rele_corriente;
        this.hora_confirmacion_activacion_rele_corriente = hora_confirmacion_activacion_rele_corriente;
        this.hora_confirmacion_activacion_rele_puerta = hora_confirmacion_activacion_rele_puerta;
        this.hora_confirmacion_desbloqueo_rele_corriente = hora_confirmacion_desbloqueo_rele_corriente;
        this.hora_confirmacion_desbloqueo_rele_puertas = hora_confirmacion_desbloqueo_rele_puertas;
    }

    public String getHora_confirmacion_activacion_rele_corriente() {
        return hora_confirmacion_activacion_rele_corriente;
    }

    public void setHora_confirmacion_activacion_rele_corriente(String hora_confirmacion_activacion_rele_corriente) {
        this.hora_confirmacion_activacion_rele_corriente = hora_confirmacion_activacion_rele_corriente;
    }

    public String getHora_confirmacion_activacion_rele_puerta() {
        return hora_confirmacion_activacion_rele_puerta;
    }

    public void setHora_confirmacion_activacion_rele_puerta(String hora_confirmacion_activacion_rele_puerta) {
        this.hora_confirmacion_activacion_rele_puerta = hora_confirmacion_activacion_rele_puerta;
    }

    public String getHora_confirmacion_desbloqueo_rele_corriente() {
        return hora_confirmacion_desbloqueo_rele_corriente;
    }

    public void setHora_confirmacion_desbloqueo_rele_corriente(String hora_confirmacion_desbloqueo_rele_corriente) {
        this.hora_confirmacion_desbloqueo_rele_corriente = hora_confirmacion_desbloqueo_rele_corriente;
    }

    public String getHora_confirmacion_desbloqueo_rele_puertas() {
        return hora_confirmacion_desbloqueo_rele_puertas;
    }

    public void setHora_confirmacion_desbloqueo_rele_puertas(String hora_confirmacion_desbloqueo_rele_puertas) {
        this.hora_confirmacion_desbloqueo_rele_puertas = hora_confirmacion_desbloqueo_rele_puertas;
    }

    public String getConfirmacion_activacion_rele_corriente() {
        return confirmacion_activacion_rele_corriente;
    }

    public void setConfirmacion_activacion_rele_corriente(String confirmacion_activacion_rele_corriente) {
        this.confirmacion_activacion_rele_corriente = confirmacion_activacion_rele_corriente;
    }

    public String getConfirmacion_activacion_rele_puerta() {
        return confirmacion_activacion_rele_puerta;
    }

    public void setConfirmacion_activacion_rele_puerta(String confirmacion_activacion_rele_puerta) {
        this.confirmacion_activacion_rele_puerta = confirmacion_activacion_rele_puerta;
    }

    public String getConfirmacion_desbloqueo_rele_corriente() {
        return confirmacion_desbloqueo_rele_corriente;
    }

    public void setConfirmacion_desbloqueo_rele_corriente(String confirmacion_desbloqueo_rele_corriente) {
        this.confirmacion_desbloqueo_rele_corriente = confirmacion_desbloqueo_rele_corriente;
    }

    public String getConfirmacion_desbloqueo_rele_puertas() {
        return confirmacion_desbloqueo_rele_puertas;
    }

    public void setConfirmacion_desbloqueo_rele_puertas(String confirmacion_desbloqueo_rele_puertas) {
        this.confirmacion_desbloqueo_rele_puertas = confirmacion_desbloqueo_rele_puertas;
    }
}