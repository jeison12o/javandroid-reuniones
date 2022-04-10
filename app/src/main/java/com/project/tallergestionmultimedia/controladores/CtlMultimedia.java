package com.project.tallergestionmultimedia.controladores;

import android.app.Activity;

import com.project.tallergestionmultimedia.dao.DaoMultimedia;
import com.project.tallergestionmultimedia.model.Multimedia;

import java.util.List;

public class CtlMultimedia {
    DaoMultimedia dao;

    public CtlMultimedia(Activity activity) {
        dao = new DaoMultimedia(activity);
    }

    public boolean registrar(Multimedia multimedia) throws Exception {
        Multimedia existe = null;
        try {
            existe = dao.buscar(multimedia);
        }catch (Exception e) {
        }
        if (existe == null){
            dao.guardar(multimedia);
            return true;
        }else {
            return false;
        }
    }

    public List<Multimedia> listar(String reunion, String tipo) {
        return dao.listar(reunion, tipo);
    }

    public boolean eliminar(Multimedia multimedia) throws Exception {
        return dao.eliminar(multimedia);
    }

    public Multimedia buscar(Multimedia multimedia) {
        return dao.buscar(multimedia);
    }

}
