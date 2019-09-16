package com.example.auto.POJO;

public class BluetoothCheckActivacionPojo {
    private String envio_activacion_rele_corriente;
    private String envio_activacion_rele_puertas;
    private String envio_desactivacion_rele_corriente;
    private String envio_desactivacion_rele_puertas;

    public BluetoothCheckActivacionPojo() {
    }

    public String getEnvio_desactivacion_rele_corriente() {
        return envio_desactivacion_rele_corriente;
    }

    public void setEnvio_desactivacion_rele_corriente(String envio_desactivacion_rele_corriente) {
        this.envio_desactivacion_rele_corriente = envio_desactivacion_rele_corriente;
    }

    public String getEnvio_desactivacion_rele_puertas() {
        return envio_desactivacion_rele_puertas;
    }

    public void setEnvio_desactivacion_rele_puertas(String envio_desactivacion_rele_puertas) {
        this.envio_desactivacion_rele_puertas = envio_desactivacion_rele_puertas;
    }

    public BluetoothCheckActivacionPojo(String envio_activacion_rele_corriente, String envio_activacion_rele_puertas) {
        this.envio_activacion_rele_corriente = envio_activacion_rele_corriente;
        this.envio_activacion_rele_puertas = envio_activacion_rele_puertas;
        this.envio_desactivacion_rele_corriente = envio_desactivacion_rele_corriente;
        this.envio_desactivacion_rele_puertas = envio_desactivacion_rele_puertas;
    }

    public String getEnvio_activacion_rele_corriente() {
        return envio_activacion_rele_corriente;
    }

    public void setEnvio_activacion_rele_corriente(String envio_activacion_rele_corriente) {
        this.envio_activacion_rele_corriente = envio_activacion_rele_corriente;
    }

    public String getEnvio_activacion_rele_puertas() {
        return envio_activacion_rele_puertas;
    }

    public void setEnvio_activacion_rele_puertas(String envio_activacion_rele_puertas) {
        this.envio_activacion_rele_puertas = envio_activacion_rele_puertas;
    }
}