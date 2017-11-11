package com.example.usuario.turnero_hospital.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Juan on 09/11/2017.
 */

public class ConexionSQLiteOpenHelper extends SQLiteOpenHelper {

    final String CREATE_TABLE_ESPECIALIDADES = "CREATE TABLE Especialidades (id INTEGER PRIMARY KEY, nombre TEXT)";
    final String INSERT_ESPECIALIDADES = "INSERT INTO Especialidades (id, nombre) VALUES (1, 'Cardiologia'), (2, 'Neurologia'), (3, 'Traumatologia'), (4, 'Pediatria'), (5, 'Ginecologia'), (6, 'Dermatologia'), (7, 'Nutricion'), (8, 'Gastroenterologia'), (9, 'Urologia'), (10, 'Oncologia')";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DB_Turnero.db";
    Context mContext;

    public ConexionSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        InputStream is = null;
        try {
            is = mContext.getAssets().open("BD_Turnero.sql");
            if (is != null) {
                db.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();
                while (!TextUtils.isEmpty(line)) {
                    db.execSQL(line);
                    line = reader.readLine();
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception ex) {
            Log.e("Turnero", "Error en BD: " + ex.getMessage());
        } finally {
            db.endTransaction();
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {
                    Log.e("Turnero", "Error al cerrar InputStream: " + ex.getMessage());
                }
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Especialidades");
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        onCreate(db);
    }
}
