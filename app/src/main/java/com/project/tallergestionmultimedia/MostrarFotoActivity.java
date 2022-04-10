package com.project.tallergestionmultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.tallergestionmultimedia.controladores.CtlMultimedia;
import com.project.tallergestionmultimedia.model.Multimedia;

import java.io.File;
import java.io.FileNotFoundException;

public class MostrarFotoActivity extends AppCompatActivity {

    private CtlMultimedia ctlMultimedia;
    private ImageView foto;
    private Multimedia multimedia;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_foto);
        ctlMultimedia = new CtlMultimedia(this);
        foto = findViewById(R.id.image);
        bundle = getIntent().getExtras();
        multimedia = (Multimedia) bundle.getSerializable("multimedia");
        try {
            File carpetaRaiz = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "archivosMultimedia");
            File images = new File(carpetaRaiz, "images");
            Bitmap bitmap1 = BitmapFactory.decodeFile( images.getPath()+File.separator+multimedia.getUrl());
            foto.setImageBitmap(bitmap1);
        }catch (Exception e){
            imprimirMensaje("la imagen fue borrada del dispositivo");
        }
    }

    public void eliminarImagen(View view) {
        try {
            ctlMultimedia.eliminar(multimedia);
            File carpetaRaiz = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "archivosMultimedia");
            File images = new File(carpetaRaiz, "images");
            File foto = new File(images ,  multimedia.getUrl());
            foto.delete();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void imprimirMensaje(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }
}
