package com.example.usuario.turnero_hospital;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.usuario.turnero_hospital.Adapter.TurnoAdapter;
import com.example.usuario.turnero_hospital.Model.Turno;
import com.example.usuario.turnero_hospital.SQLite.ConexionSQLiteOpenHelper;

import java.util.ArrayList;

public class MisTurnos extends AppCompatActivity {

    ConexionSQLiteOpenHelper conn;
    ArrayList<Turno> listaTurnos;
    RecyclerView turnosRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_turnos);
        showToolbar("Mis Turnos", true);

        turnosRecycler = (RecyclerView) findViewById(R.id.turnosRecycler);

        //Acomodo el recycler view con un layout manager
        conn = new ConexionSQLiteOpenHelper(this);

        listaTurnos = new ArrayList<>();

        turnosRecycler.setLayoutManager(new LinearLayoutManager(this));
        consultarListaTurnos();
        final TurnoAdapter turnoAdapter = new TurnoAdapter(listaTurnos, R.layout.turnos_card_view, this);
        turnoAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        turnosRecycler.setAdapter(turnoAdapter);
    }

    public void showToolbar(String titulo, boolean atras)
    {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(atras);
    }

    //Carga los turnos
    public void consultarListaTurnos()
    {
        SQLiteDatabase db = conn.getReadableDatabase();

        Turno turno;
        Cursor cursor = db.rawQuery("SELECT * FROM datosTurno", null);

        while(cursor.moveToNext())
        {
            turno = new Turno();
            turno.setId(cursor.getInt(0));
            turno.setFecha(cursor.getString(1));
            turno.setEspecialidad(cursor.getString(2));

            listaTurnos.add(turno);
        }
    }
}
