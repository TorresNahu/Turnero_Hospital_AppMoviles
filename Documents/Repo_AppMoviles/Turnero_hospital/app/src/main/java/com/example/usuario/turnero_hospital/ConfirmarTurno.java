package com.example.usuario.turnero_hospital;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.turnero_hospital.Model.Usuario;
import com.example.usuario.turnero_hospital.SQLite.ConexionSQLiteOpenHelper;

import static java.security.AccessController.getContext;

public class ConfirmarTurno extends AppCompatActivity {

    TextView txt_fechaSeleccionada, txt_especialidadSeleccionada, txt_Nombre, txt_apellido;
    int idUsuario; //Guardado asi para obtener el ID de las Preferences
    Button btn_confirmarTurno;
    SessionManager session;

    //Instancia que nos permitira acceder a la BD
    final ConexionSQLiteOpenHelper mDbHelper = new ConexionSQLiteOpenHelper(ConfirmarTurno.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_turno);
        showToolbar("Confirmar turno", true);

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
                values.put("id_Usuario", idUsuario);

                // Insert the new row, returning the primary key value of the new row
                try {
                    long newRowId = db.insertOrThrow("datosTurno", null, values);
                    Toast.makeText(ConfirmarTurno.this, R.string.mensajeTurnoGuardado, Toast.LENGTH_SHORT).show();
                } catch (SQLiteException ex) {
                    Log.e("SQLiteERROR", "Error al insertar el turno: " + ex.getMessage());
                }

                //Volver al Home
                Intent goToHome = new Intent(ConfirmarTurno.this, HomeUsuario.class);
                goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(goToHome);

            }
        });
    }

    private void ObtenerUsuario() {
        //Leo de las preferencias los datos del usuario
        session = new SessionManager(getApplicationContext());
        Usuario usuario = session.getUserDetails();
        idUsuario = usuario.getId();
        txt_Nombre.setText(usuario.getNombre());
        txt_apellido.setText(usuario.getApellido());

    }

    public void showToolbar(String titulo, boolean atras) {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(atras);
    }
}
