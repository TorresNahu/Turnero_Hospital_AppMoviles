package com.example.usuario.turnero_hospital;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.usuario.turnero_hospital.Model.Usuario;

/**
 * Created by Juan on 16/11/2017.
 */

public class SessionManager {


    SharedPreferences pref;

    Editor editor;

    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Nombre del preferences
    private static final String PREF_NAME = "Credenciales";

    // Bandera para saber si esta logeado
    private static final String IS_LOGIN = "IsLoggedIn";

    // User ID
    public static final String KEY_ID = "idUsuario";

    // User Name
    public static final String KEY_NAME = "nombre";

    // User Surname
    public static final String KEY_SURNAME = "apellido";

    // User DNI
    public static final String KEY_DNI = "dni";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Crear la sesion
    public void createLoginSession(int id, String name, String surname, int dni) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_SURNAME, surname);
        editor.putInt(KEY_DNI, dni);
        editor.commit();
    }

    //Obtener los datos de la sesion
    public Usuario getUserDetails() {
        Usuario user = new Usuario();

        user.setId(pref.getInt(KEY_ID, 0));
        user.setNombre(pref.getString(KEY_NAME, ""));
        user.setApellido(pref.getString(KEY_SURNAME, ""));
        user.setDni(pref.getInt(KEY_DNI, 0));

        return user;
    }

    //Verifica el boolean LoggedIn
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    //Checkea si el usuario esta logueado
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // Redirecciona al usuario a la pantalla de login en caso de no estar logueado
            Intent i = new Intent(_context, Principal.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // Redirecciona a la pantalla de login
        Intent i = new Intent(_context, Principal.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }
}
