package com.project.tallergestionmultimedia.model;

import java.io.Serializable;

public class Multimedia implements Serializable {
    private String nombre;
    private String url;
    private String reunion;
    private String tipo;

    public Multimedia() {
    }

    public Multimedia(String nombre, String url, String reunion, String tipo) {
        this.nombre = nombre;
        this.url = url;
        this.reunion = reunion;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReunion() {
        return reunion;
    }

    public void setReunion(String reunion) {
        this.reunion = reunion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "nombre=" + nombre +", tipo=" + tipo;
    }
}
