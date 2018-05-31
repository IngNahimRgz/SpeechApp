package com.example.adrian.muleapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.muleapp.R;
import com.example.adrian.muleapp.activities.GraphicActivity;
import com.example.adrian.muleapp.activities.VerArchivo;
import com.example.adrian.muleapp.data.Class_Archivos;
import com.example.adrian.muleapp.model.Custom_Adapter;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultadosFragment extends Fragment {
    PieChart pieChart;
    TextView txtVw_no_files;
    ListView listView;
    List<String> items = new ArrayList<String>();
    ArrayList<Class_Archivos> dataModels;
    private static Custom_Adapter adapter;



    public ResultadosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


    View rootView = inflater.inflate(R.layout.fragment_resultados, container, false);
        listView = rootView.findViewById(R.id.listaArchivos_Result);
        txtVw_no_files = rootView.findViewById(R.id.txtVw_No_hay_archivos_Result);

        Class_Archivos archivos = new Class_Archivos(getContext());
        items = archivos.get_lista_de_archivos_results();
        if (items == null){
            Toast.makeText(getContext(), "No hay grabaciones aun", Toast.LENGTH_SHORT).show();
            txtVw_no_files.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }else{
            dataModels = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                dataModels.add(new Class_Archivos(items.get(i).toString()));
            }
            txtVw_no_files.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }

        //ArrayAdapter<String> filesList = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);
        adapter = new Custom_Adapter(getContext(), dataModels);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), GraphicActivity.class);
                intent.putExtra("nombre_del_archivo", items.get(position));
                startActivity(intent);
                // Toast.makeText(getContext(), ""+ items.get(2), Toast.LENGTH_SHORT).show();

            }
        });


    return rootView;
    }

}
