package com.example.usuario.turnero_hospital.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usuario.turnero_hospital.Calendario;
import com.example.usuario.turnero_hospital.Model.Especialidad;
import com.example.usuario.turnero_hospital.R;

import java.util.ArrayList;

/**
 * Created by Juan on 08/11/2017.
 */

public class EspecialidadesAdapter extends RecyclerView.Adapter<EspecialidadesAdapter.EspecialidadesViewHolder> implements View.OnClickListener {

    private ArrayList<Especialidad> especialidades;
    private int resource;
    private Activity activity;
    private View.OnClickListener listener;


    public EspecialidadesAdapter(ArrayList<Especialidad> especialidades, int resource, Activity activity) {
        this.especialidades = especialidades;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public EspecialidadesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        view.setOnClickListener(this);
        return new EspecialidadesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EspecialidadesViewHolder holder, int position) {
        Especialidad especialidad = especialidades.get(position);
        holder.nombre.setText(especialidad.getNombre());
       }


    @Override
    public int getItemCount() {
        return especialidades.size();
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

        if(listener!= null)
        {
            listener.onClick(view);
        }
    }


    public class EspecialidadesViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre;

        public EspecialidadesViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txt_cardItem);
        }

    }
}
