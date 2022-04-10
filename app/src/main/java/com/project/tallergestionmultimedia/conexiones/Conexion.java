package com.project.tallergestionmultimedia.conexiones;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.tallergestionmultimedia.utilidades.UtilidadesMultimedia;
import com.project.tallergestionmultimedia.utilidades.UtilidadesReunion;

public class Conexion extends SQLiteOpenHelper {

    private static final String database = "archivosMultimedia.db";
    /*Para manipular el registro que retorna la DB*/
    private static final SQLiteDatabase.CursorFactory factory = null;
    private static final int version = 1;
    /*Instancia de la base de datos*/
    SQLiteDatabase bd;

    /*Constructor si uno quiere especificar otra DB*/
    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    /*Para usar la base de datos establecida*/
    public Conexion(Context context) {
        super(context, database,factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
            //db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UtilidadesReunion.crearTabla);
        db.execSQL(UtilidadesMultimedia.crearTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte,int versionNue) {
        db.execSQL("drop table if exists "+UtilidadesReunion.tabla);
        db.execSQL("drop table if exists "+ UtilidadesMultimedia.tabla);
        onCreate(db);
    }

    public void cerrarConexion() {
        bd.close();
    }

    public boolean ejecutarInsert(String tabla, ContentValues registro) {
        try {
            // Objeto para lectura y escritura en la base de datos
            bd = this.getWritableDatabase();

            /*null es los campos que no se van a registrar, y rertona -1 si hubo error*/
            int res = (int) bd.insert(tabla, null, registro);
            cerrarConexion();
            if (res != -1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public boolean ejecutarDelete(String tabla, String condicion) {
        bd = this.getWritableDatabase();

        /*Si la clausula del where - Condicion esta con ?, en este otro parametro
        se envian los datos,
        * por ejemplo:
        * db.delete("tablename","id=? and name=?",new String[]{"1","jack"});*/
        int cant = bd.delete(tabla, condicion, null);
        cerrarConexion();

        if (cant >= 1) {
            return true;
        } else {
            return false;
        }
    }


    public boolean ejecutarUpdate(String tabla, String condicion, ContentValues registro) {
        try {

            bd = this.getWritableDatabase();

            int cant = bd.update(tabla, registro, condicion, null);

            cerrarConexion();

            if (cant == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public Cursor ejecutarSearch(String consulta) {
        try {
            // Objeto para lectura y escritura en la base de datos
            bd = this.getWritableDatabase();
            /* Definimos un objeto de tipo cursor que almacena la info de la
             base de datos, ademas ejecutamos una consulta sql
            En el null se especifican los parametros, dado el caso que en
            el SQL no, como con */
            Cursor fila = bd.rawQuery(consulta, null);
            return fila;

        } catch (Exception e) {
            cerrarConexion();
            return null;
        }
    }
}
