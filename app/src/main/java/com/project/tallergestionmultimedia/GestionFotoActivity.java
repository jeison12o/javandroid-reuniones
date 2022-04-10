package com.project.tallergestionmultimedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.project.tallergestionmultimedia.controladores.CtlMultimedia;
import com.project.tallergestionmultimedia.model.Multimedia;
import com.project.tallergestionmultimedia.model.Reunion;

import java.io.File;
import java.util.List;

public class GestionFotoActivity extends AppCompatActivity {

    private CtlMultimedia ctlMultimedia;
    private ListView list;
    private Bundle bundle;
    private final String tipo = "foto";
    private String nombreReunion;
    private EditText txtNombreFoto;
    private List<Multimedia> listaImages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_foto);
        ctlMultimedia = new CtlMultimedia(this);
        txtNombreFoto = findViewById(R.id.txtNombreFoto);
        list = findViewById(R.id.listFoto);
        iniciar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        iniciar();
    }

    private void iniciar() {
        bundle = getIntent().getExtras();
        nombreReunion =  bundle.getString("nombreReunion");
        configurarLista();
        txtNombreFoto.setText("");
    }

    private void configurarLista() {
        listaImages = ctlMultimedia.listar( nombreReunion, tipo);
        ArrayAdapter<Multimedia> adapter = new ArrayAdapter<Multimedia>(this, android.R.layout.simple_list_item_1, listaImages);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GestionFotoActivity.this, MostrarFotoActivity.class);
                intent.putExtra("multimedia", listaImages.get(position));
                startActivity(intent);
            }
        }) ;
    }



    public void tomarFoto(View view) {
        String nombreFoto = txtNombreFoto.getText().toString();
        String nombreImagen = (System.currentTimeMillis()/1000)+".jpg";
        if (nombreFoto.isEmpty()) {
            imprimirMensaje("debe ingresar el nombre de la imagen para tomarla");
        }else {
            Multimedia multimedia = new Multimedia();
            multimedia.setNombre(nombreFoto);
            multimedia.setTipo(tipo);
            multimedia.setReunion(nombreReunion);
            multimedia.setUrl(nombreImagen);
            try {
                if (ctlMultimedia.registrar(multimedia)) {
                    File carpetaRaiz = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "archivosMultimedia");
                    carpetaRaiz.mkdir();
                    File images = new File(carpetaRaiz, "images");
                    images.mkdir();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File foto = new File(images ,  nombreImagen);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(GestionFotoActivity.this, BuildConfig.APPLICATION_ID + ".provider", foto));
                    startActivity(intent);
                } else{
                    imprimirMensaje("ya existe una foto con ese nombre debe cambiar el nombre");
                }
            } catch (Exception e) {
                imprimirMensaje(e.getMessage());
            }
            configurarLista();
        }
    }

    private void imprimirMensaje(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }



}
