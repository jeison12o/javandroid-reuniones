package com.project.tallergestionmultimedia.utilidades;

public class UtilidadesMultimedia {
    public static final String tabla = "multimedia";
    public static final String campoNombre = "nombre";
    public static final String campoUrl = "url";
    public static final String campoReunion = "nombreReunion";
    public static final String campoTipo = "tipo";
    public static final String crearTabla = "create table "+tabla+"( "+campoNombre+"  text , "+campoUrl+" text , "+campoReunion+" text, "+campoTipo+" text, " +
            "FOREIGN KEY("+campoReunion+") REFERENCES "+UtilidadesReunion.tabla+"("+UtilidadesReunion.campoNombre+") ON DELETE CASCADE);";
}
