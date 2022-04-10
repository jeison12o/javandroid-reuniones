package com.project.tallergestionmultimedia.controladores;

import android.app.Activity;

import com.project.tallergestionmultimedia.dao.DaoReunion;
import com.project.tallergestionmultimedia.model.Reunion;

import java.util.List;

public class CtlReunion {
    DaoReunion dao;

    public CtlReunion(Activity activity) {
        dao = new DaoReunion(activity);
    }

    public boolean registrar(Reunion reunion) throws Exception {
            Reunion existe = null;
            try {
                existe = dao.buscar(reunion.getNombre());
            }catch (Exception e) {
            }
            if (existe == null){
                dao.guardar(reunion);
                return true;
            }else {
                return false;
            }
    }

    public List<Reunion> listar() {
        return dao.listar();
    }
}
