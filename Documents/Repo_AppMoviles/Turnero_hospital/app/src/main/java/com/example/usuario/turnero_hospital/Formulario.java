package com.example.usuario.turnero_hospital;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class Formulario extends AppCompatActivity {
    Button btnRegistrar;
    TextInputEditText nombre, apellido, dni, password;
    ProgressDialog progress;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        showToolbar(getResources().getString(R.string.toolbar_titulo_registrarse), true);

        request = Volley.newRequestQueue(this);

        btnRegistrar = (Button) findViewById(R.id.btn_crearCuenta);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = new ProgressDialog(Formulario.this);
                progress.setMessage("Registrando usuario...");
                progress.show();
                RegistrarUsuario();
            }
        });
    }


    public void showToolbar(String titulo, boolean atras) {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(atras);
    }

    public void RegistrarUsuario() {
        nombre = (TextInputEditText) findViewById(R.id.registro_nombre);
        apellido = (TextInputEditText) findViewById(R.id.registro_apellido);
        dni = (TextInputEditText) findViewById(R.id.registro_dni);
        password = (TextInputEditText) findViewById(R.id.registro_contraseña);
        TextInputEditText[] campos = new TextInputEditText[]{nombre, apellido, dni, password};
        boolean validado = validarCampos(campos);

        if (validado) {
            String url = "http://www.turnerorest.somee.com/Turnero/RegistrarUsuario";
            HashMap<String, String> body = new HashMap<>();
            body.put("nombre", nombre.getText().toString().trim());
            body.put("apellido", apellido.getText().toString().trim());
            body.put("dni", dni.getText().toString().trim());
            body.put("password", password.getText().toString().trim());
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(body), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(Formulario.this, "Usuario registrado correctamente! Ahora podes Loguearte", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                    Intent intent = new Intent(Formulario.this, Principal.class);
                    startActivity(intent);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Formulario.this, "Ocurrio un problema al registrar el usuario:" + error.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("Error al registrar ", error.getMessage());
                    progress.hide();
                }
            });
            request.add(jsonObjectRequest);
        }
        else
        {
            Toast.makeText(Formulario.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            progress.hide();
        }
    }


    public boolean validarCampos(TextInputEditText[] campos) {
        for (int i = 0; i < campos.length; i++) {
            if (!campos[i].getText().toString().matches("")) {
                continue;
            }
            return false;
        }
        return true;
    }


}
