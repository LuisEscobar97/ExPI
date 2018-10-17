package com.example.sitol.appexpi;

public class ObjHistorial {

    String tiempo,fentrada,fsalida,numtarjeta,tibase,tiagregado,estacionamiento,usuario;
    double total,tabase,taagregado;
    int estados;

    public ObjHistorial(String tiempo, String fentrada, String fsalida, String numtarjeta, String tibase, String tiagregado, String estacionamiento, String usuario, double total, double tabase, double taagregado, int estados) {
        this.tiempo = tiempo;
        this.fentrada = fentrada;
        this.fsalida = fsalida;
        this.numtarjeta = numtarjeta;
        this.tibase = tibase;
        this.tiagregado = tiagregado;
        this.estacionamiento = estacionamiento;
        this.usuario = usuario;
        this.total = total;
        this.tabase = tabase;
        this.taagregado = taagregado;
        this.estados = estados;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getFentrada() {
        return fentrada;
    }

    public void setFentrada(String fentrada) {
        this.fentrada = fentrada;
    }

    public String getFsalida() {
        return fsalida;
    }

    public void setFsalida(String fsalida) {
        this.fsalida = fsalida;
    }

    public String getNumtarjeta() {
        return numtarjeta;
    }

    public void setNumtarjeta(String numtarjeta) {
        this.numtarjeta = numtarjeta;
    }

    public String getTibase() {
        return tibase;
    }

    public void setTibase(String tibase) {
        this.tibase = tibase;
    }

    public String getTiagregado() {
        return tiagregado;
    }

    public void setTiagregado(String tiagregado) {
        this.tiagregado = tiagregado;
    }

    public String getEstacionamiento() {
        return estacionamiento;
    }

    public void setEstacionamiento(String estacionamiento) {
        this.estacionamiento = estacionamiento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTabase() {
        return tabase;
    }

    public void setTabase(double tabase) {
        this.tabase = tabase;
    }

    public double getTaagregado() {
        return taagregado;
    }

    public void setTaagregado(double taagregado) {
        this.taagregado = taagregado;
    }

    public int getEstados() {
        return estados;
    }

    public void setEstados(int estados) {
        this.estados = estados;
    }
}
