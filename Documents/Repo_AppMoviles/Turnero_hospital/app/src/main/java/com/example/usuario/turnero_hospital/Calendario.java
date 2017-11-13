package com.example.usuario.turnero_hospital;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

public class Calendario extends AppCompatActivity {

    CalendarView calendarioTurnoView;
    Button btnSiguiente;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        showToolbar("Seleccione una fecha", true);

        calendarioTurnoView = (CalendarView) findViewById(R.id.calendarView);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);

        calendarioTurnoView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + (month+1) + "/" + year; //al mes se le suma una porque android toma los meses de 0 (Enero) al 11 (Diciembre).
            }
        });

        //Recuperar el id de especialidad que viene
        Intent incoming = getIntent();
        final int id = incoming.getIntExtra("idEspecialidad", 0);
        final String nombre = incoming.getStringExtra("nombreEspecialidad");

        //Pasar la fecha y el id especialidad a la confirmacion
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calendario.this, ConfirmarTurno.class);
                intent.putExtra("date", date);
                intent.putExtra("idEspecialidad", id);
                intent.putExtra("nombreEspecialidad", nombre);
                startActivity(intent);
            }
        });


    }


    public void showToolbar(String titulo, boolean atras)
    {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(atras);
    }



}
