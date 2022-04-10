package com.project.tallergestionmultimedia.model;

import java.io.Serializable;

public class Reunion implements Serializable {
    private String nombre;
    private String fecha;
    private String lugar;

    public Reunion() {
    }

    public Reunion(String nombre, String fecha, String lugar) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @Override
    public String toString() {
        return  nombre;
    }
}
