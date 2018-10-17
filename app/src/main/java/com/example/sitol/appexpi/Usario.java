package com.example.sitol.appexpi;

import android.database.sqlite.SQLiteDatabase;

public class Usario {

    private int idU;
    private int tipoU;
    private int estadoUsuario;
    private String  nombre;
    private String correo;
    private String ApMaterno;
    private String ApPaterno;
    private long noTarjeta;
    private long codigoBarras;
    private long telefono;

    public Usario(int idU, int tipoU, int estadoUsuario, String nombre, String correo, String apMaterno,
                  String apPaterno, long noTarjeta, long codigoBarras, long telefono) {
        this.idU = idU;
        this.tipoU = tipoU;
        this.estadoUsuario = estadoUsuario;
        this.nombre = nombre;
        this.correo = correo;
        ApMaterno = apMaterno;
        setApPaterno(apPaterno);
        this.noTarjeta = noTarjeta;
        this.codigoBarras = codigoBarras;
        this.telefono = telefono;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public int getTipoU() {
        return tipoU;
    }

    public void setTipoU(int tipoU) {
        this.tipoU = tipoU;
    }

    public int getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(int estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApMaterno() {
        return ApMaterno;
    }

    public void setApMaterno(String apMaterno) {
        ApMaterno = apMaterno;
    }

    public long getNoTarjeta() {
        return noTarjeta;
    }

    public void setNoTarjeta(long noTarjeta) {
        this.noTarjeta = noTarjeta;
    }

    public long getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(long codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getApPaterno() {
        return ApPaterno;
    }

    public void setApPaterno(String apPaterno) {
        ApPaterno = apPaterno;
    }


}
