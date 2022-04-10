package com.project.tallergestionmultimedia.utilidades;

public class UtilidadesReunion {
    public static final String tabla = "reunion";
    public static final String campoNombre = "nombre";
    public static final String campoFecha = "fecha";
    public static final String campoLugar = "lugar";
    public static final String crearTabla = "create table "+tabla+"( "+campoNombre+"  text primary key, "+campoFecha+" text , "+campoLugar+" text);";
}
