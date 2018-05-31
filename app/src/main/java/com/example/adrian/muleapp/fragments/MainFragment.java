package com.example.adrian.muleapp.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.muleapp.R;
import com.example.adrian.muleapp.data.Class_Archivos;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    Context context;
    View rootView;
    FloatingActionButton fab;
    TextView textView_Texto;
    ImageButton imageButton_cancel, imageButton_okey;

    public static final int RECOGNIZE_SPEECH_ACTIVITY = 100;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        vincularVista();
        if (!runtime_permissions()) {
            enable_buttons();
        }
        context = getContext();
        return rootView;
    }


    private boolean runtime_permissions() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE}, 100);

            return true;
        }
        return false;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            if (requestCode == 100) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    enable_buttons();
                } else {
                    runtime_permissions();
                }
            }
        }
    }

    private void enable_buttons() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentActionRecognizeSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                //Configura el Lenguaje (Espanol Mx)
                intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");

                try {
                    startActivityForResult(intentActionRecognizeSpeech, RECOGNIZE_SPEECH_ACTIVITY);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getContext(), R.string.device_not_support_recognize_speech, Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageButton_okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearAlertDialogNombreArchivo();

            }
        });
        imageButton_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearAlertDialogCancelGrabacion();
            }
        });
    }

    private void crearAlertDialogCancelGrabacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("¿Estás seguro que quieres borrar la grabacion?")
                .setCancelable(true)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView_Texto.setText("");
                        imageButton_cancel.setVisibility(View.INVISIBLE);
                        imageButton_okey.setVisibility(View.INVISIBLE);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setTitle(R.string.titulo_AlertDialog_Borrar_Texto)
                .setIcon(R.drawable.ic_warning_white_24dp);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void crearAlertDialogNombreArchivo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final EditText txtTituloDelArchivo = new EditText(getContext());
        builder.setTitle(R.string.titulo_AlertDialogGuardar);   // Título
        builder.setView(txtTituloDelArchivo);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //TODO Aqui se guardara el texto que tenga el TextView en aun archivo de texto
                guardarArchivoTexto(txtTituloDelArchivo.getText().toString());

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void guardarArchivoTexto(String nombre_archivo) {
        //  Toast.makeText(getContext(), ""+ text, Toast.LENGTH_SHORT).show();
        Class_Archivos archivo = new Class_Archivos(context);
        archivo.Crear_Nuevo_Archivo(nombre_archivo, textView_Texto.getText().toString());


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RECOGNIZE_SPEECH_ACTIVITY) {
            // textView_Texto.setText("primer if");
            if (null != data) {
                imageButton_cancel.setVisibility(View.VISIBLE);
                imageButton_okey.setVisibility(View.VISIBLE);

                ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                String strSpeech2text = speech.get(0);
                textView_Texto.append(" " + strSpeech2text);
            }
        }
    }

    private void vincularVista() {

        fab = rootView.findViewById(R.id.fab);
        textView_Texto = rootView.findViewById(R.id.textView_texto);
        imageButton_cancel = rootView.findViewById(R.id.img_button_cancel);
        imageButton_okey = rootView.findViewById(R.id.img_button_okey);
    }

}
