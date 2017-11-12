package com.example.usuario.turnero_hospital;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.turnero_hospital.SQLite.ConexionSQLiteOpenHelper;

import static java.security.AccessController.getContext;

public class ConfirmarTurno extends AppCompatActivity {

    TextView txt_fechaSeleccionada, txt_especialidadSeleccionada, txt_Nombre, txt_apellido;

    Button btn_confirmarTurno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_turno);

        txt_fechaSeleccionada = (TextView) findViewById(R.id.txt_fechaSeleccionada);

        btn_confirmarTurno = (Button) findViewById(R.id.btn_confirmarTurnoApp);

        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        txt_fechaSeleccionada.setText(date);

        //Instancia que nos permitira acceder a la BD
        final ConexionSQLiteOpenHelper mDbHelper = new ConexionSQLiteOpenHelper(ConfirmarTurno.this);


//        btn_confirmarTurno.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Gets the data repository in write mode
//                SQLiteDatabase db = mDbHelper.getWritableDatabase();
//
//                // Create a new map of values, where column names are the keys
//                ContentValues values = new ContentValues();
//                values.put(EstructuraBD.NOMBRE_COLUMNA_FECHA,txt_fechaSeleccionada.getText().toString());
//                values.put(EstructuraBD.NOMBRE_COLUMNA_ESPECIALIDAD,txt_especialidadSeleccionada.getText().toString());
//                values.put(EstructuraBD.NOMBRE_COLUMNA_NOMBRE,txt_Nombre.getText().toString());
//                values.put(EstructuraBD.NOMBRE_COLUMNA_APELLIDO,txt_apellido.getText().toString());
//
//                // Insert the new row, returning the primary key value of the new row
//                long newRowId = db.insert(EstructuraBD.TABLE_NAME, null, values);
//
//                Toast.makeText(getApplicationContext(), R.string.mensajeTurnoGuardado,Toast.LENGTH_SHORT);
//            }
//        });


    }
}
