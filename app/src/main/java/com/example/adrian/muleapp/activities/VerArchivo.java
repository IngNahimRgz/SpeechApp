package com.example.adrian.muleapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adrian.muleapp.R;
import com.example.adrian.muleapp.data.Class_Archivos;
import com.example.adrian.muleapp.data.Class_Palabras;
import com.example.adrian.muleapp.data.Class_Procesar_Datos;

import java.util.ArrayList;

public class VerArchivo extends AppCompatActivity {

    ProgressBar progressBar;
    Intent intent;
    TextView txtVwTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_archivo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtVwTexto = findViewById(R.id.textView_archivo_leido);
        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.INVISIBLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                Activity activity = getParent();
                Class_Procesar_Datos procesar_datos = new Class_Procesar_Datos(getApplicationContext(), txtVwTexto, progressBar);
                 procesar_datos.execute(txtVwTexto.getText().toString(),intent.getStringExtra("nombre_del_archivo"));
            }
        });

        intent = getIntent();
        Class_Archivos archivos = new Class_Archivos(getApplicationContext());
        archivos.setNombre_del_archivo(intent.getStringExtra("nombre_del_archivo"));
        txtVwTexto.setText(archivos.leer_Archivo());
    }
}
