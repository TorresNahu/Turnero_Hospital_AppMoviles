package com.example.usuario.turnero_hospital;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.usuario.turnero_hospital.Model.Usuario;

/**
 * Created by Juan on 06/11/2017.
 */

public class HomeUsuario extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    TextView user;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeusuario);
        user = (TextView) findViewById(R.id.usernameProfile);

        session = new SessionManager(getApplicationContext());
        Usuario usuario = session.getUserDetails();
        user.setText(usuario.getNombre());

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

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        session.logoutUser();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        session.checkLogin();
    }


}
