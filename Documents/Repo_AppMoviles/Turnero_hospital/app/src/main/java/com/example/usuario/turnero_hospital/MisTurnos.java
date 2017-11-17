package com.example.usuario.turnero_hospital;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.turnero_hospital.Adapter.TurnoAdapter;
import com.example.usuario.turnero_hospital.Model.Especialidad;
import com.example.usuario.turnero_hospital.Model.Turno;
import com.example.usuario.turnero_hospital.Model.Usuario;
import com.example.usuario.turnero_hospital.SQLite.ConexionSQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MisTurnos extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    ConexionSQLiteOpenHelper conn;
    ArrayList<Turno> listaTurnos;
    RecyclerView turnosRecycler;

    Turno turno;

    int idTurno;
    String fecha;
    String espec;

    SessionManager session;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_turnos);
        showToolbar("Mis Turnos", true);

        turnosRecycler = (RecyclerView) findViewById(R.id.turnosRecycler);
        conn = new ConexionSQLiteOpenHelper(this);

        request = Volley.newRequestQueue(this);

        listaTurnos = new ArrayList<>();

        turnosRecycler.setLayoutManager(new LinearLayoutManager(this));
        consultarListaTurnos();
        final TurnoAdapter turnoAdapter = new TurnoAdapter(listaTurnos, R.layout.turnos_card_view, this);
        turnoAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                idTurno = listaTurnos.get(turnosRecycler.getChildAdapterPosition(v)).getId();
                fecha = listaTurnos.get(turnosRecycler.getChildAdapterPosition(v)).getFecha();
                String idEspec = listaTurnos.get(turnosRecycler.getChildAdapterPosition(v)).getEspecialidad();

                consultarEspecialidad(idEspec);


            }
        });

        turnosRecycler.setAdapter(turnoAdapter);
    }

    public void showToolbar(String titulo, boolean atras) {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(atras);
    }

    //Carga los turnos
    public void consultarListaTurnos() {
        SQLiteDatabase db = conn.getReadableDatabase();

        //Obtiene los datos del usuario logueado
        session = new SessionManager(getApplicationContext());
        Usuario usuario = session.getUserDetails();

        String[] columnas = {"idTurno", "fecha", "id_Especialidad", "id_Usuario"};
        String[] condicion = {String.valueOf(usuario.getId())};

        Cursor cursor = db.query("datosTurno", columnas, "id_Usuario=?", condicion, null, null, null);

        while (cursor.moveToNext()) {
            turno = new Turno();
            turno.setId(cursor.getInt(0));
            turno.setFecha(cursor.getString(1));
            turno.setEspecialidad(cursor.getString(2));
            listaTurnos.add(turno);

            Collections.sort(listaTurnos, new Comparator<Turno>() {
                @Override
                public int compare(Turno o1, Turno o2) {
                    return o1.getFecha().compareTo(o2.getFecha());
                }
            });
        }
    }


    public void consultarEspecialidad(String id) {
        String url = "http://www.turnerorest.somee.com/Turnero/ObtenerEspecialidad/" + id;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error al consultar la especialidad: " + error.getMessage(), Toast.LENGTH_LONG).show();
        Log.d("Error especialidad", error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        Especialidad especialidad = new Especialidad();
        JSONArray json;
        json = response.optJSONArray("lista");
        try {
            JSONObject jsonObject = json.getJSONObject(0);
            especialidad.setId(jsonObject.optInt("idEspecialidad"));
            especialidad.setNombre(jsonObject.optString("nombre"));

            espec = especialidad.getNombre();
            abrirDialogo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //(final int idTurno, String fechaTurno, String especialidad)
    public void abrirDialogo() {
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(MisTurnos.this);
        alertOpciones.setTitle("Información del Turno");

        alertOpciones.setMessage("Fecha: " + fecha + "\nEspecialidad: " + espec);
        alertOpciones.setCancelable(true);

        alertOpciones.setNegativeButton(
                "Borrar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        abrirDialogoConfirmarBorrado(idTurno);
                        dialog.cancel();
                    }
                });

        alertOpciones.setPositiveButton(
                "Cerrar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = alertOpciones.create();
        alert11.show();
    }

    public void abrirDialogoConfirmarBorrado(final int idTurno) {
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(MisTurnos.this);
        alertOpciones.setTitle("Confirmar eliminación del Turno");

        alertOpciones.setMessage("¿Esta seguro que desea eliminar el turno seleccionado?");
        alertOpciones.setCancelable(true);

        alertOpciones.setPositiveButton(
                "Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        eliminarTurno(idTurno);
                        refrescarActivity();
                        dialog.cancel();
                    }
                });

        alertOpciones.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = alertOpciones.create();
        alert11.show();
    }

    public void eliminarTurno(int idTurno) {
        SQLiteDatabase db = conn.getReadableDatabase();

        String table = "datosTurno";
        String whereClause = "idTurno=?";
        String[] whereArgs = new String[]{String.valueOf(idTurno)};

        db.delete(table, whereClause, whereArgs);

        Toast.makeText(this, "Turno eliminado correctamente", Toast.LENGTH_SHORT).show();
    }

    public void refrescarActivity() {
        Intent i = getIntent();
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();

    }
}