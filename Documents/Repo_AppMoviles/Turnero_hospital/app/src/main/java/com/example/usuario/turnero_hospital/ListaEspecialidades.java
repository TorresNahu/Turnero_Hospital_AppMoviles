package com.example.usuario.turnero_hospital;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.example.usuario.turnero_hospital.Adapter.EspecialidadesAdapter;
import com.example.usuario.turnero_hospital.Model.Especialidad;
import com.example.usuario.turnero_hospital.SQLite.ConexionSQLiteOpenHelper;

import java.util.ArrayList;

public class ListaEspecialidades  extends AppCompatActivity{

    ConexionSQLiteOpenHelper conn;
    ArrayList<Especialidad> lista;
    RecyclerView especialidadesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_especialidades);
        showToolbar("Especialidades", true);
        especialidadesRecycler = (RecyclerView) findViewById(R.id.especialidadesRecycler);

        //Acomodo el recycler view con un layout manager

        conn = new ConexionSQLiteOpenHelper(this);


        lista = new ArrayList<>();

        especialidadesRecycler.setLayoutManager(new LinearLayoutManager(this));
        consultarListaEspecialidades();
        final EspecialidadesAdapter especialidadesAdapter = new EspecialidadesAdapter(lista, R.layout.activity_lista_especialidades_item, this);
        especialidadesAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaEspecialidades.this, Calendario.class);
                intent.putExtra("idEspecialidad", lista.get(especialidadesRecycler.getChildAdapterPosition(view)).getId());
                startActivity(intent);
            }
        });
        especialidadesRecycler.setAdapter(especialidadesAdapter);
    }


    public void showToolbar(String titulo, boolean atras)
    {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(atras);
    }

    //Carga las especialidades
    public void consultarListaEspecialidades()
    {
        SQLiteDatabase db = conn.getReadableDatabase();

        Especialidad especialidad;
        Cursor cursor = db.rawQuery("SELECT * FROM Especialidades", null);

        while(cursor.moveToNext())
        {
            especialidad = new Especialidad();
            especialidad.setId(cursor.getInt(0));
            especialidad.setNombre(cursor.getString(1));

            lista.add(especialidad);
        }
    }
}
