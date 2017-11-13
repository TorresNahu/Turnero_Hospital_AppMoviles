package com.example.usuario.turnero_hospital;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Juan on 06/11/2017.
 */

public class HomeUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeusuario);

        Button btn_nuevoTurno = (Button) findViewById(R.id.btn_sacarTurno);
        Button btn_verTurnos = (Button) findViewById(R.id.btn_verTurnos);

        btn_nuevoTurno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeUsuario.this, ListaEspecialidades.class);
                startActivity(intent);
            }
        });

        btn_verTurnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeUsuario.this, MisTurnos.class);
                startActivity(i);
            }
        });
    }



}
