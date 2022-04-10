package com.project.tallergestionmultimedia;

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

import java.io.File;
import java.util.List;

public class GestionVideoActivity extends AppCompatActivity {

    private CtlMultimedia ctlMultimedia;
    private ListView list;
    private Bundle bundle;
    private final String tipo = "video";
    private String nombreReunion;
    private EditText txtNombreVideo;
    private List<Multimedia> listaVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_video);
        ctlMultimedia = new CtlMultimedia(this);
        list = findViewById(R.id.listVideos);
        txtNombreVideo = findViewById(R.id.txtNombreVideo);
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
        txtNombreVideo.setText("");
    }

    private void configurarLista() {
        listaVideo = ctlMultimedia.listar( nombreReunion, tipo);
        ArrayAdapter<Multimedia> adapter = new ArrayAdapter<Multimedia>(this, android.R.layout.simple_list_item_1, listaVideo);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GestionVideoActivity.this, MostrarVideoActivity.class);
                intent.putExtra("multimedia", listaVideo.get(position));
                startActivity(intent);
            }
        }) ;
    }



    public void grabarVideo(View view) {
        String nombreVideo = txtNombreVideo.getText().toString();
        String nombreUrl = (System.currentTimeMillis()/1000)+".mp4";
        if (nombreVideo.isEmpty()) {
            imprimirMensaje("debe ingresar el nombre del video para grabar");
        }else {
            Multimedia multimedia = new Multimedia();
            multimedia.setNombre(nombreVideo);
            multimedia.setTipo(tipo);
            multimedia.setReunion(nombreReunion);
            multimedia.setUrl(nombreUrl);
            try {
                if (ctlMultimedia.registrar(multimedia)) {
                    File carpetaRaiz = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "archivosMultimedia");
                    carpetaRaiz.mkdir();
                    File videos = new File(carpetaRaiz, "videos");
                    videos.mkdir();
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    File video = new File(videos ,  nombreUrl);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(GestionVideoActivity.this, BuildConfig.APPLICATION_ID + ".provider", video));
                    startActivity(intent);
                } else{
                    imprimirMensaje("ya existe un video con ese nombre debe cambiar el nombre");
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
