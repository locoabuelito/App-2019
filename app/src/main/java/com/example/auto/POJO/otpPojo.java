package com.example.auto.POJO;

public class otpPojo {
    private String manual, automatico;

    public otpPojo() {
    }

    public otpPojo(String manual, String automatico) {
        this.manual = manual;
        this.automatico = automatico;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public String getAutomatico() {
        return automatico;
    }

    public void setAutomatico(String automatico) {
        this.automatico = automatico;
    }
}
