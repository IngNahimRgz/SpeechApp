package com.example.adrian.muleapp.model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.adrian.muleapp.R;
import com.example.adrian.muleapp.data.Class_Archivos;

import java.util.ArrayList;
import java.util.List;

public class Custom_Adapter extends ArrayAdapter<Class_Archivos> implements View.OnClickListener {
    private ArrayList<Class_Archivos> dataSet;
    String nombre;
    Context context;

    private static class ViewHolder {
        TextView nombre;
    }

    public Custom_Adapter(Context context, ArrayList<Class_Archivos> data) {
        super(context, R.layout.row_item, data);

        this.context = context;
        this.dataSet = data;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Class_Archivos dataModel = (Class_Archivos) object;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        Class_Archivos dataModel = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;

        final View result;

        if(convertView == null){
           viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.nombre = (TextView) convertView.findViewById(R.id.txtVw_nombre_archivos);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.nombre.setText(dataModel.getNombre_del_archivo());
        return convertView;
                //super.getView(position, convertView, parent);
    }
}
