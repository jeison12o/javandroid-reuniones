package com.project.tallergestionmultimedia.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.project.tallergestionmultimedia.conexiones.Conexion;
import com.project.tallergestionmultimedia.model.Reunion;
import com.project.tallergestionmultimedia.utilidades.UtilidadesReunion;

import java.util.ArrayList;
import java.util.List;

public class DaoReunion {
    Conexion conex;

    public DaoReunion(Activity activity){
        conex = new Conexion(activity);
    }

    public boolean guardar(Reunion reunion) {
        ContentValues registro = new ContentValues();
        registro.put(UtilidadesReunion.campoNombre, reunion.getNombre());
        registro.put(UtilidadesReunion.campoFecha, reunion.getFecha());
        registro.put(UtilidadesReunion.campoLugar, reunion.getLugar());
        return conex.ejecutarInsert(UtilidadesReunion.tabla, registro);
    }

    public Reunion buscar(String nombre) {
        Reunion reunion = null;
        String consulta = "select "+UtilidadesReunion.campoNombre+", "+UtilidadesReunion.campoFecha+", " +
                ""+UtilidadesReunion.campoLugar+" from "+UtilidadesReunion.tabla+" where "+UtilidadesReunion.campoNombre+"='" + nombre+ "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            reunion = new Reunion(temp.getString(0),temp.getString(1), temp.getString(2));
        }
        conex.cerrarConexion();
        return reunion;
    }

    public List<Reunion> listar() {
        List<Reunion> lista = new ArrayList();
        String consulta = "select "+UtilidadesReunion.campoNombre+", "+UtilidadesReunion.campoFecha+", "+UtilidadesReunion.campoLugar+" from "+UtilidadesReunion.tabla+";";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.moveToFirst()) {
            do {
                Reunion reu = new Reunion(temp.getString(0),temp.getString(1), temp.getString(2));
                lista.add(reu);
            } while (temp.moveToNext());
        }
        return lista;
    }
}
