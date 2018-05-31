package com.example.adrian.muleapp.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.muleapp.R;

import java.util.ArrayList;

public class Class_Procesar_Datos extends AsyncTask<String, Void, ArrayList<Class_Palabras>> {
    ProgressBar progressBar;
    TextView textView;
    String nombre_archivo_leido;
    Context context;
    String[] texto;
    ArrayList<Class_Palabras> arrayList = new ArrayList<>();
    ArrayList<String> palabras_leidas = new ArrayList<String>();
    int contador;


    public Class_Procesar_Datos(Context context, TextView txtVwTexto, ProgressBar progressBar) {
        this.progressBar = progressBar;
        this.textView = txtVwTexto;
        this.context = context;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);


    }


    @Override
    protected ArrayList<Class_Palabras> doInBackground(String... strings) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        nombre_archivo_leido = strings[1];
        texto = strings[0].trim().split("\\s+");

        // CONVERTIMOS EL TEXTO A MINUSCULAS
        for (int i = 0; i < texto.length; i++) {
            texto[i] = texto[i].toLowerCase();
        }

        palabras_leidas.add(texto[0]);
        // HACEMOS UN ARRAY_LIST DE PALABRAS, NO SE REPETIRAN
        for (int i = 0; i < texto.length; i++) {
            if (!isPalabraLeida(texto[i])) {
                palabras_leidas.add(texto[i]);
            }
        }

        // CONTAMOS EL NUMERO DE VECES QUE APARACE LA PALABRA
        for (int i = 0; i < palabras_leidas.size(); i++) {
            contador = 0;
            for (int j = 0; j < texto.length; j++) {
                if (texto[j].equals(palabras_leidas.get(i))) {
                    contador++;
                }
            }
            arrayList.add(new Class_Palabras(palabras_leidas.get(i), contador));
        }
        return arrayList;
    }

    private boolean isPalabraLeida(String s) {
        for (int i = 0; i < palabras_leidas.size(); i++) {
            if (s.equals(palabras_leidas.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(ArrayList<Class_Palabras> class_palabras) {
        super.onPostExecute(class_palabras);

        // VACIAMOS EL TEXTVIEW PARA REMPLAZARLO CON LOS RESULTADOS;
        textView.setText("");

        for (int i = 0; i < arrayList.size(); i++) {
            textView.append("\n" + arrayList.get(i).palabra + " = " + arrayList.get(i).veces_repetida);
        }

        //  GUARDAMOS EL ARRAY_LIST EN UN ARCHIVO DE TEXTO, PARA DESPUES ANALIZARLOS EN UN GRAFICO
        Class_Archivos archivo = new Class_Archivos(context);
        archivo.Crear_Nuevo_Archivo_Resultados(nombre_archivo_leido,textView.getText().toString() );

        progressBar.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
    }
}

