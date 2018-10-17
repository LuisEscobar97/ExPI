package com.example.sitol.appexpi;

import java.util.ArrayList;

/**
 * Created by sitol on 20/11/2017.
 */

public class DatosLista {
    private String titulo;
    private  String subtitulo;
    private int imagen;
    private ObjHistorial lista;

    public DatosLista(String titulo, String subtitulo, int imagen, ObjHistorial lista) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.imagen = imagen;
        this.lista=lista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public ObjHistorial getLista() {
        return lista;
    }

    public void setLista(ObjHistorial lista) {
        this.lista = lista;
    }
}
