package com.project.tallergestionmultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.project.tallergestionmultimedia.controladores.CtlReunion;
import com.project.tallergestionmultimedia.model.Reunion;

import java.util.Calendar;

public class CrearReunionActivity extends AppCompatActivity {

    private EditText txtNombre, txtFecha, txtLugar;
    private CtlReunion ctlReunion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_reunion);

        txtNombre = (EditText) findViewById(R.id.txtNombreReunion);
        txtFecha = (EditText) findViewById(R.id.txtFechaReunion);
        txtLugar = (EditText) findViewById(R.id.txtLugarReunion);

        ctlReunion = new CtlReunion(this);

        txtFecha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                DatePickerDialog mDatePicker = new DatePickerDialog(CrearReunionActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear,
                                                  int selectedmonth, int selectedday) {
                                txtFecha.setText(selectedyear + "/" + selectedmonth + "/" + selectedday);
                            }
                        }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH),
                        mcurrentDate.get(Calendar.DAY_OF_MONTH));
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
    }

    private void imprimirMensaje (String mensaje){
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    private void limpiar() {
        txtNombre.setText("");
        txtFecha.setText("");
        txtLugar.setText("");
    }

    public void guardarReunion(View view) {
        String nombre = txtNombre.getText().toString();
        String fecha = txtFecha.getText().toString();
        String lugar = txtLugar.getText().toString();

        if(nombre.isEmpty() || fecha.isEmpty() || lugar.isEmpty()) {
            imprimirMensaje("debe ingresar los datos correctamente para guardar");
        }else{
            Reunion reunion = new Reunion();
            reunion.setNombre(nombre);
            reunion.setFecha(fecha);
            reunion.setLugar(lugar);



            try {
                if (ctlReunion.registrar(reunion)) {
                    imprimirMensaje("se registro con exito la reunion");
                    limpiar();
                }else{
                    imprimirMensaje("ya existe una reunion con este nombre debe cambiarlo");
                    txtNombre.setText("");
                }
            } catch (Exception e) {
                imprimirMensaje(e.getMessage());
                limpiar();
            }
        }
    }

    public void vistaRegresar(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
