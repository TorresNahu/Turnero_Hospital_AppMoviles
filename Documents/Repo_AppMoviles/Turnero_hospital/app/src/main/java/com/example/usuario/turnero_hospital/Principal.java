package com.example.usuario.turnero_hospital;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
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
import com.example.usuario.turnero_hospital.Model.Usuario;

import org.json.JSONObject;

public class Principal extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    TextInputEditText dni, password;
    RequestQueue request;
    ProgressDialog progress;
    JsonObjectRequest jsonObjectRequest;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_principal);

        Button btLogin = (Button) findViewById(R.id.btn_login);
        btLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Login();

            }
        });

        Button btRegistrar = (Button) findViewById(R.id.btn_registro);
        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, Formulario.class);
                startActivity(intent);

            }
        });

    }

    protected void Login() {
        dni = (TextInputEditText) findViewById(R.id.dni);
        password = (TextInputEditText) findViewById(R.id.password);
        if (dni.getText().toString().matches("") || password.getText().toString().matches("")) {
            Toast.makeText(this, "Ingrese datos de inicio de sesion", Toast.LENGTH_LONG).show();
        } else {
            String url = "http://www.turnerorest.somee.com/Turnero/ObtenerUsuario/" + dni.getText().toString() + "/" + password.getText().toString();

            progress = new ProgressDialog(this);
            progress.setMessage("Iniciando Sesion...");
            progress.show();

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            request.add(jsonObjectRequest);
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Datos incorrectos. Por favor, corrija la informacion", Toast.LENGTH_LONG).show();
        Log.d("ERROR al consultar: ", error.toString());
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        Usuario usuario = null;
        JSONObject json;
        usuario = new Usuario();

        json = response.optJSONObject("usuario");
        usuario.setId(json.optInt("idUsuario"));
        usuario.setNombre(json.optString("nombre"));
        usuario.setApellido(json.optString("apellido"));
        usuario.setDni(json.optInt("dni"));

        //Guardo el inicio de sesion
        session = new SessionManager(getApplicationContext());
        session.createLoginSession(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getDni());

        progress.dismiss();

        Intent intent = new Intent(this, HomeUsuario.class);
        startActivity(intent);
        //Para cerrar la activity principal.
        finish();

    }


}
