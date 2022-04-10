package com.project.tallergestionmultimedia.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.project.tallergestionmultimedia.conexiones.Conexion;
import com.project.tallergestionmultimedia.model.Multimedia;
import com.project.tallergestionmultimedia.model.Reunion;
import com.project.tallergestionmultimedia.utilidades.UtilidadesMultimedia;
import com.project.tallergestionmultimedia.utilidades.UtilidadesReunion;

import java.util.ArrayList;
import java.util.List;

public class DaoMultimedia {
    Conexion conex;

    public DaoMultimedia(Activity activity){
        conex = new Conexion(activity);
    }

    public boolean guardar(Multimedia multimedia) {
        ContentValues registro = new ContentValues();
        registro.put(UtilidadesMultimedia.campoNombre, multimedia.getNombre());
        registro.put(UtilidadesMultimedia.campoUrl, multimedia.getUrl());
        registro.put(UtilidadesMultimedia.campoReunion, multimedia.getReunion());
        registro.put(UtilidadesMultimedia.campoTipo, multimedia.getTipo());
        return conex.ejecutarInsert(UtilidadesMultimedia.tabla, registro);
    }

    public Multimedia buscar(Multimedia multimedia) {
        Multimedia multimedia2 = null;
        String consulta = "select "+UtilidadesMultimedia.campoUrl+" from "+UtilidadesMultimedia.tabla+" where "+UtilidadesMultimedia.campoReunion+"='" + multimedia.getReunion()+ "'  and  "+UtilidadesMultimedia.campoTipo+"= '"+multimedia.getTipo()+"'" +
                " and "+UtilidadesMultimedia.campoNombre+"='"+multimedia.getNombre()+"';";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            multimedia2 = new Multimedia(multimedia.getNombre(),temp.getString(0), multimedia.getReunion(), multimedia.getTipo());
        }
        conex.cerrarConexion();
        return multimedia2;
    }

    public List<Multimedia> listar(String nombreReunion, String tipo) {
        List<Multimedia> lista = new ArrayList();
        String consulta = "select "+UtilidadesMultimedia.campoNombre+", "+UtilidadesMultimedia.campoUrl+" from "+UtilidadesMultimedia.tabla+" where "+UtilidadesMultimedia.campoReunion+"='" + nombreReunion+ "' and "+UtilidadesMultimedia.campoTipo+"='"+tipo+"';";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.moveToFirst()) {
            do {
                Multimedia multimedia = new Multimedia(temp.getString(0),temp.getString(1), nombreReunion, tipo);
                lista.add(multimedia);
            } while (temp.moveToNext());
        }
        return lista;
    }

    public boolean eliminar(Multimedia multimedia) {
        String condicion = UtilidadesMultimedia.campoNombre+"='" + multimedia.getNombre()+"' and "+UtilidadesMultimedia.campoTipo +" = '"+
                multimedia.getTipo() + "' and "+UtilidadesMultimedia.campoReunion+"= '"+multimedia.getReunion()+"';";
        return conex.ejecutarDelete(UtilidadesMultimedia.tabla, condicion);
    }
}
