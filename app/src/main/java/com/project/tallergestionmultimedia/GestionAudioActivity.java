package com.project.tallergestionmultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.tallergestionmultimedia.controladores.CtlMultimedia;
import com.project.tallergestionmultimedia.model.Multimedia;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GestionAudioActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private MediaRecorder recorder;
    private MediaPlayer player;
    private CtlMultimedia ctlMultimedia;
    private ListView list;
    private Bundle bundle;
    private final String tipo = "audio";
    private String nombreReunion;
    private EditText txtNombreAudio;
    private List<Multimedia> listaAudio;
    private boolean estado = false;
    private Button btnAudio;
    private TextView lblEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_audio);
        ctlMultimedia = new CtlMultimedia(this);
        list = findViewById(R.id.listAudio);
        txtNombreAudio = findViewById(R.id.txtNombreAudio);
        lblEstado = findViewById(R.id.lblEstado);
        iniciar();
    }

    protected void onStart() {
        super.onStart();
        iniciar();
    }

    private void iniciar() {
        bundle = getIntent().getExtras();
        nombreReunion =  bundle.getString("nombreReunion");
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        configurarLista();
        txtNombreAudio.setText("");
        btnAudio = findViewById(R.id.btnGrabarAudio);
        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estado==false) {
                    grabarAudio();
                }else{
                    detenerAudio();
                }
            }
        });
    }

    private void configurarLista() {
        listaAudio = ctlMultimedia.listar( nombreReunion, tipo);
        ArrayAdapter<Multimedia> adapter = new ArrayAdapter<Multimedia>(this, android.R.layout.simple_list_item_1, listaAudio);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reproducirAudio(position);
            }
        }) ;
    }

    private void reproducirAudio(int i) {
        try {
            File carpetaRaiz = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "archivosMultimedia");
            File audios = new File(carpetaRaiz, "audios");
            File archivo = new File(audios, listaAudio.get(i).getUrl());
            player.setDataSource(archivo.getAbsolutePath());
            player.prepare();
            player.start();
            lblEstado.setText("Reproduciendo ......");
        }catch (IOException e) {
            imprimirMensaje("se borro el archivo del cell");
        }
    }

    private void imprimirMensaje(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    private void grabarAudio() {
        String nombreAudio = txtNombreAudio.getText().toString();
        String nombreUrl = (System.currentTimeMillis()/1000)+".3gp";
        if (nombreAudio.isEmpty()) {
            imprimirMensaje("debe ingresar el nombre del audio para grabar");
        }else {
            Multimedia multimedia = new Multimedia();
            multimedia.setNombre(nombreAudio);
            multimedia.setTipo(tipo);
            multimedia.setReunion(nombreReunion);
            multimedia.setUrl(nombreUrl);
            try {
                if (ctlMultimedia.registrar(multimedia)) {
                    File carpetaRaiz = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "archivosMultimedia");
                    carpetaRaiz.mkdir();
                    File audios = new File(carpetaRaiz, "audios");
                    audios.mkdir();
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    File archivo = new File(audios, nombreUrl);
                    recorder.setOutputFile(archivo.getAbsolutePath());
                    recorder.prepare();
                    recorder.start();
                    lblEstado.setText("Grabando ....");
                    estado = true;
                    btnAudio.setText("Detener de grabar");
                } else{
                    imprimirMensaje("ya existe un audio con ese nombre debe cambiar el nombre");
                    txtNombreAudio.setText("");
                }
            } catch (Exception e) {
                imprimirMensaje(e.getMessage());
            }
        }
    }

    private void detenerAudio() {
        estado = false;
        recorder.stop();
        recorder.release();
        btnAudio.setText("Grabar audio");
        lblEstado.setText("listo se guardo");
        txtNombreAudio.setText("");
        configurarLista();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        lblEstado.setText("termino de reproducir el audio");
    }

    public void eliminarAudio(View view) {
        String nombreAudio = txtNombreAudio.getText().toString();
        if (nombreAudio.isEmpty()) {
            imprimirMensaje("debe ingresar el nombre del audio para grabar");
        }else {
            Multimedia multimedia = new Multimedia();
            multimedia.setNombre(nombreAudio);
            multimedia.setTipo(tipo);
            multimedia.setReunion(nombreReunion);

            Multimedia mt = ctlMultimedia.buscar(multimedia);
            try {
            ctlMultimedia.eliminar(multimedia);
            File carpetaRaiz = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "archivosMultimedia");
            File audios = new File(carpetaRaiz, "audios");
            File archivo = new File(audios, mt.getUrl());
            archivo.delete();
            configurarLista();
            } catch (Exception e) {
            }
        }
    }

}
