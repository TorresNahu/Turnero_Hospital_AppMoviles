package com.example.usuario.turnero_hospital;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.turnero_hospital.SQLite.ConexionSQLiteOpenHelper;

import static java.security.AccessController.getContext;

public class ConfirmarTurno extends AppCompatActivity {

    TextView txt_fechaSeleccionada, txt_especialidadSeleccionada, txt_Nombre, txt_apellido;

    Button btn_confirmarTurno;

    //Instancia que nos permitira acceder a la BD
    final ConexionSQLiteOpenHelper mDbHelper = new ConexionSQLiteOpenHelper(ConfirmarTurno.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_turno);

        txt_fechaSeleccionada = (TextView) findViewById(R.id.txt_fechaSeleccionada);
        txt_especialidadSeleccionada = (TextView) findViewById(R.id.txt_especialidadSeleccionada);
        txt_Nombre = (TextView) findViewById(R.id.txt_nombrePersona);
        txt_apellido = (TextView) findViewById(R.id.txt_apellidoPersona);
        btn_confirmarTurno = (Button) findViewById(R.id.btn_confirmarTurnoApp);

        Intent incomingIntent = getIntent();
        final String date = incomingIntent.getStringExtra("date");
        final int id = incomingIntent.getIntExtra("idEspecialidad", 0);
        final String nombre = incomingIntent.getStringExtra("nombreEspecialidad");
        txt_fechaSeleccionada.setText(date);
        txt_especialidadSeleccionada.setText(nombre);
        ObtenerUsuario();


        btn_confirmarTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gets the data repository in write mode
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                // Create a new map of values, where column names are the keys
                ContentValues values = new ContentValues();
                values.put("fecha", date);
                values.put("id_Especialidad", id);
                values.put("id_Usuario", 1);

                // Insert the new row, returning the primary key value of the new row
                try {
                    long newRowId = db.insertOrThrow("datosTurno", null, values);
                    Toast.makeText(ConfirmarTurno.this, R.string.mensajeTurnoGuardado, Toast.LENGTH_SHORT).show();
                }
                catch (SQLiteException ex)
                {Log.e("SQLiteERROR", "Error al insertar el turno: "+ ex.getMessage());}

            }
        });
    }

    private void ObtenerUsuario() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] condicion = {"1"};
        String[] columnas = {"nombreUsuario", "apellidoUsuario"};
        try {
            Cursor cursor = db.query("Usuarios", columnas, "idUsuario=?", condicion, null, null, null);
            cursor.moveToFirst();
            txt_Nombre.setText(cursor.getString(0));
            txt_apellido.setText(cursor.getString(1));
            cursor.close();
        } catch (Exception ex) {
            Log.e("ConfirmarTurno", ex.getMessage());
        }

    }
}
