package com.example.usuario.turnero_hospital;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.usuario.turnero_hospital.Adapter.EspecialidadesAdapter;
import com.example.usuario.turnero_hospital.Model.Especialidad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaEspecialidades extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    ArrayList<Especialidad> lista;
    RecyclerView especialidadesRecycler;
    ProgressDialog progress;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_especialidades);
        showToolbar("Especialidades", true);
        especialidadesRecycler = (RecyclerView) findViewById(R.id.especialidadesRecycler);
        especialidadesRecycler.setLayoutManager(new LinearLayoutManager(this));
        request = Volley.newRequestQueue(this);
        lista = new ArrayList<>();
        consultarListaEspecialidades();

    }

    @Override
    protected void onPause() {
        super.onPause();
        progress.dismiss();
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
        String url = "http://www.turnerorest.somee.com/Turnero/ObtenerEspecialidades";

        progress = new ProgressDialog(this);
        progress.setMessage("Cargando...");
        progress.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "No se puede conectar: " + error.toString(), Toast.LENGTH_LONG).show();
        Log.d("ERROR al consultar: ", error.toString());
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        Especialidad especialidad = null;
        JSONArray json;
        try {
            json = response.optJSONArray("lista");
            for (int i=0;i<json.length();i++)
            {
                especialidad = new Especialidad();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                especialidad.setId(jsonObject.optInt("idEspecialidad"));
                especialidad.setNombre(jsonObject.optString("nombre"));

                lista.add(especialidad);
            }
            progress.hide();
            EspecialidadesAdapter especialidadesAdapter = new EspecialidadesAdapter(lista, R.layout.activity_lista_especialidades_item, this);
            especialidadesAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListaEspecialidades.this, Calendario.class);
                    intent.putExtra("idEspecialidad", lista.get(especialidadesRecycler.getChildAdapterPosition(view)).getId());
                    intent.putExtra("nombreEspecialidad", lista.get(especialidadesRecycler.getChildAdapterPosition(view)).getNombre());
                    startActivity(intent);
                }
            });
            especialidadesRecycler.setAdapter(especialidadesAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "No se pudo establecer la conexion: " + e.getMessage(), Toast.LENGTH_LONG).show();
            progress.hide();
        }

    }
}
