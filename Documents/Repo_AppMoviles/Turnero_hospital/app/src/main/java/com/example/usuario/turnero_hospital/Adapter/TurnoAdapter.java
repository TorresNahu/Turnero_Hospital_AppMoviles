package com.example.usuario.turnero_hospital.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.usuario.turnero_hospital.Model.Turno;
import com.example.usuario.turnero_hospital.R;

import java.util.ArrayList;

/**
 * Created by Nahu on 13/11/2017.
 */

public class TurnoAdapter extends RecyclerView.Adapter<TurnoAdapter.TurnoViewHolder> implements View.OnClickListener{

    private ArrayList<Turno> turnos;
    private int resource;
    private Activity activity;
    private View.OnClickListener listener;

    public TurnoAdapter(ArrayList<Turno> turnos, int resource, Activity activity){
        this.turnos = turnos;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public TurnoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        view.setOnClickListener(this);
        return new TurnoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TurnoViewHolder holder, int position) {
        Turno turno = turnos.get(position);
        holder.nombre.setText(turno.getFecha());
    }

    @Override
    public int getItemCount() {
        return turnos.size();
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }

    }

    public class TurnoViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre;

        public TurnoViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txt_cardItem);
        }

    }
}
