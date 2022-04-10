package com.project.tallergestionmultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.project.tallergestionmultimedia.controladores.CtlMultimedia;
import com.project.tallergestionmultimedia.model.Multimedia;

import java.io.File;

public class MostrarVideoActivity extends AppCompatActivity {

    private CtlMultimedia ctlMultimedia;
    private VideoView video;
    private Multimedia multimedia;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_video);
        ctlMultimedia = new CtlMultimedia(this);
        video = findViewById(R.id.video);
        bundle = getIntent().getExtras();
        multimedia = (Multimedia) bundle.getSerializable("multimedia");
        try {
            File carpetaRaiz = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "archivosMultimedia");
            File videos = new File(carpetaRaiz, "videos");
            video.setVideoURI(Uri.parse(videos.getPath()+File.separator+multimedia.getUrl()));
            video.start();
        }catch (Exception e){
            imprimirMensaje("la imagen fue borrada del dispositivo");
        }
    }

    private void imprimirMensaje(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    public void eliminarVideo(View view) {
        try {
            ctlMultimedia.eliminar(multimedia);
            File carpetaRaiz = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "archivosMultimedia");
            File videos = new File(carpetaRaiz, "videos");
            File video = new File(videos ,  multimedia.getUrl());
            video.delete();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
