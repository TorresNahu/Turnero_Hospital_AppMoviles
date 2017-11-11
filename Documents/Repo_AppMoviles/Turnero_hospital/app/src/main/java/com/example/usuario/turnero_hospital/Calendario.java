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
                date = dayOfMonth + "/" + (month+1) + "/" + year;

            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calendario.this, ConfirmarTurno.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        Intent incoming = getIntent();
        int id = incoming.getIntExtra("idEspecialidad", 0);
        Toast.makeText(this, "Id pasado: " + id, Toast.LENGTH_LONG).show();
    }

    public void showToolbar(String titulo, boolean atras)
    {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(atras);
    }



}
