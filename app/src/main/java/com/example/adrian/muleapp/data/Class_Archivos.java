package com.example.adrian.muleapp.data;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Class_Archivos {
    Context context;
    String nombre_del_archivo, texto;
    public static final String APP_PATH_EXTERNAL_STORAGE = "/MuleApp";
    public static final String APP_PATH_EXTERNAL_STORAGE_RESULTS = "/MuleApp/Results";
    public ArrayList<String> lista_de_archivos = new ArrayList<String>();
    String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_EXTERNAL_STORAGE;
    String fullPathResults = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_EXTERNAL_STORAGE_RESULTS;
    String texto_leido = "";



    public void setNombre_del_archivo(String _nombre_del_archivo){
        this.nombre_del_archivo = _nombre_del_archivo;
    }

    public Class_Archivos(String nombre_del_archivo) {
        this.nombre_del_archivo = nombre_del_archivo;
    }

    public String getNombre_del_archivo() {
        return nombre_del_archivo;
    }

    public Class_Archivos(Context _context) {
        this.context = _context;

        isExternalStorageWritable();
        isExternalStorageReadble();
    }

    private boolean isExternalStorageWritable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.i("State", "si se puede escribir en el almacenamiento");
            return true;
        } else {
            return false;
        }
    }

    private boolean isExternalStorageReadble() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
            Log.i("State", "si se puede leer  el almacenamiento");
            return true;
        }
        return false;
    }

    public void Crear_Nuevo_Archivo(String _nombre, String _texto) {
        nombre_del_archivo = _nombre;
        texto = _texto;


        if (isExternalStorageWritable()) {
            File carpeta = new File(fullPath);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File dir = new File(fullPath, nombre_del_archivo + ".txt");




            try {
                FileOutputStream fos = new FileOutputStream(dir);
                fos.write(texto.getBytes());
                fos.close();

                Toast.makeText(context, "Archivo guardado", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "No se puede guardar", Toast.LENGTH_SHORT).show();
        }
    }

    public void Crear_Nuevo_Archivo_Resultados(String _nombre, String _texto) {
        nombre_del_archivo = _nombre;
        texto = _texto;

        if (isExternalStorageWritable()) {
            File carpeta = new File(fullPathResults);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File dir = new File(fullPathResults, nombre_del_archivo + ".txt");

            try {
                FileOutputStream fos = new FileOutputStream(dir);
                fos.write(texto.getBytes());
                fos.close();

                Toast.makeText(context, "Archivo guardado", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "No se puede guardar", Toast.LENGTH_SHORT).show();
        }
    }

    public List<String> get_lista_de_archivos() {
        //Seleccionamos la carpeta donde listaremos los archivos, obviamente es la misma donde guardamos las grabaciones
        File file = new File(fullPath);

        //Hacemos nuestro arreglo;
        File[] files = file.listFiles();

        //Hacemos un FOR para sacar el nombre de cada archivo
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File f = files[i];

                //Si es una carpeta
                if (f.isDirectory()) {
                    lista_de_archivos.add(f.getName() + "/");
                } else {
                    lista_de_archivos.add(f.getName());
                }
            }
        }

        return lista_de_archivos;
    }

    public List<String> get_lista_de_archivos_results() {
        //Seleccionamos la carpeta donde listaremos los archivos, obviamente es la misma donde guardamos las grabaciones
        File file = new File(fullPathResults);

        //Hacemos nuestro arreglo;
        File[] files = file.listFiles();

        //Hacemos un FOR para sacar el nombre de cada archivo
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File f = files[i];

                //Si es una carpeta
                if (f.isDirectory()) {
                    lista_de_archivos.add(f.getName() + "/");
                } else {
                    lista_de_archivos.add(f.getName());
                }
            }
        }

        return lista_de_archivos;
    }

    public String leer_Archivo(){
        try{
            File file = new File(fullPath, nombre_del_archivo);

            BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

          do{
          texto_leido = texto_leido.concat("\n".concat(fin.readLine()));
          }while (fin.readLine() != null);

            fin.close();
        }catch (Exception ex){
            Log.e("Archivos", "Error al leer el archivo");
        }

        return  texto_leido;
    }

    public String leer_Archivo_results(){
        try{
            File file = new File(fullPathResults, nombre_del_archivo);

            BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            do{
                texto_leido = texto_leido.concat("\n".concat(fin.readLine()));
            }while (fin.readLine() != null);

            fin.close();
        }catch (Exception ex){
            Log.e("Archivos", "Error al leer el archivo de resultados");
        }

        return  texto_leido;
    }
}
