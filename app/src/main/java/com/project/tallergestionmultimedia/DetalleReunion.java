package com.project.tallergestionmultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.project.tallergestionmultimedia.model.Reunion;

public class DetalleReunion extends AppCompatActivity {

    TextView lblNombre, lblFecha, lblLugar;
    private Reunion reunion;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reunion);
        lblNombre = findViewById(R.id.lblNombre);
        lblFecha = findViewById(R.id.lblFecha);
        lblLugar = findViewById(R.id.lblLugar);
        iniciar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        iniciar();
    }

    private void iniciar() {
        bundle = getIntent().getExtras();
        reunion = (Reunion) bundle.getSerializable("reunion");
        lblNombre.setText(reunion.getNombre());
        lblFecha.setText(reunion.getFecha());
        lblLugar.setText(reunion.getLugar());
    }

    public void vistaGestionFoto(View view) {
        Intent intent = new Intent(this, GestionFotoActivity.class);
        intent.putExtra("nombreReunion", reunion.getNombre());
        startActivity(intent);
    }

    public void vistaGestionVideo(View view) {
        Intent intent = new Intent(this, GestionVideoActivity.class);
        intent.putExtra("nombreReunion", reunion.getNombre());
        startActivity(intent);
    }

    public void vistaGestionAudio(View view) {
        Intent intent = new Intent(this, GestionAudioActivity.class);
        intent.putExtra("nombreReunion", reunion.getNombre());
        startActivity(intent);
    }
}
