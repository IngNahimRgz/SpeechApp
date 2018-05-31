package com.example.adrian.muleapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.muleapp.R;
import com.example.adrian.muleapp.data.Class_Archivos;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphicActivity extends AppCompatActivity {
    PieChart pieChart;
    Intent intent;
    TextView txtVwTexto;
    String[] palabras, texto, filtro;
    int[] contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);

        txtVwTexto = findViewById(R.id.textView_archivo_leido_results);
        txtVwTexto.setVisibility(View.INVISIBLE);

        intent = getIntent();
        Class_Archivos archivos = new Class_Archivos(getApplicationContext());
        archivos.setNombre_del_archivo(intent.getStringExtra("nombre_del_archivo"));
        txtVwTexto.setText(archivos.leer_Archivo_results());

        texto = txtVwTexto.getText().toString().trim().split("\\s+");
        palabras = new String[texto.length/3];
        contador = new int[texto.length/3];


        pieChart = (PieChart) findViewById(R.id.pieChart);

        /*definimos algunos atributos*/
        pieChart.setHoleRadius(40f);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);

        int j = 0;
        // OBTENEMOS LAS PALABRAS LEIDAS
        for (int i = 0; i < texto.length - 1; i = i + 3) {
            if (i == 0) {
                palabras[j] = texto[0];
                j++;
            } else {
                palabras[j] = texto[i];
                j++;
            }
        }

       // Toast.makeText(this, ""+palabras[0]+palabras[1]+palabras[2]+palabras[3]+palabras[4], Toast.LENGTH_SHORT).show();
        for (int i = 0; i < palabras.length ; i++) {
            Log.i("Palabras", "hola");
        }


        // OBTENEMOS EL CONTADOR DE CADA PALABRA
        int k = 0;
        for (int i = 2; i < texto.length - 1; i = i + 3) {
            if (i == 0) {
                contador[k] = Integer.parseInt(texto[2]);
                k++;
            } else {
                contador[k] = Integer.parseInt(texto[i]);
                k++;
            }
        }

        /*creamos una lista para los valores Y*/
        ArrayList<PieEntry> valsY = new ArrayList<PieEntry>();
        for (int i = 0; i < contador.length; i++) {
            valsY.add(new PieEntry(contador[i], palabras[i]));
        }

        /*creamos una lista para los valores X*/
        ArrayList<String> valsX = new ArrayList<String>();
        for (int i = 0; i < palabras.length; i++) {
            valsX.add(palabras[i]);
        }

        /*creamos una lista de colores*/
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < palabras.length; i++) {
            colors.add(getRandomColor()); // color aleatorio
        }

        /*seteamos los valores de Y y los colores*/
        PieDataSet set1 = new PieDataSet(valsY,null);
        set1.setSliceSpace(3f);
        set1.setColors(colors);

        /*seteamos los valores de X*/
        PieData data = new PieData(set1);
        //PieData data = new PieData(valsX, set1);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();

    //    Toast.makeText(this, valsX.get(0)+"      " + valsY.get(0)+"    "+ palabras.length, Toast.LENGTH_SHORT).show();

    }

    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
