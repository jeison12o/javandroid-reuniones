package com.project.tallergestionmultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.tallergestionmultimedia.controladores.CtlReunion;
import com.project.tallergestionmultimedia.model.Reunion;

import java.util.List;

public class ListarReunionActivity extends AppCompatActivity {

    private ListView list;
    private CtlReunion ctlReunion;
    private List<Reunion> listaReuniones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_reunion);

        list = (ListView) findViewById(R.id.listReunion);
        ctlReunion = new CtlReunion(this);
        configurarLista();
    }

    private void configurarLista() {
        listaReuniones = ctlReunion.listar();
        ArrayAdapter<Reunion> adapter = new ArrayAdapter<Reunion>(this, android.R.layout.simple_list_item_1, listaReuniones);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListarReunionActivity.this, DetalleReunion.class);
                intent.putExtra("reunion", listaReuniones.get(position));
                startActivity(intent);
            }
        }) ;
    }

    public void vistaRegresar(View view){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}
