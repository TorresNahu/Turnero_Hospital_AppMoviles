package com.example.usuario.turnero_hospital.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Juan on 09/11/2017.
 */

public class ConexionSQLiteOpenHelper extends SQLiteOpenHelper {

    final String CREATE_TABLE_ESPECIALIDADES = "CREATE TABLE Especialidades (id INTEGER PRIMARY KEY, nombre TEXT)";
    final String INSERT_ESPECIALIDADES = "INSERT INTO Especialidades (id, nombre) VALUES (1, 'Cardiologia'), (2, 'Neurologia'), (3, 'Traumatologia'), (4, 'Pediatria'), (5, 'Ginecologia'), (6, 'Dermatologia'), (7, 'Nutricion'), (8, 'Gastroenterologia')";

    public ConexionSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ESPECIALIDADES);
        db.execSQL(INSERT_ESPECIALIDADES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Especialidades");
        onCreate(db);
    }
}
