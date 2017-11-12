package com.example.usuario.turnero_hospital;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.usuario.turnero_hospital.SQLite.ConexionSQLiteOpenHelper;

public class MisTurnos extends AppCompatActivity {

    ListView listViewDeTurnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_turnos);

        listViewDeTurnos = (ListView) findViewById(R.id.listView_TurnoGuardados);

        //Instancia que nos permitira acceder a la BD
        final ConexionSQLiteOpenHelper mDbHelper = new ConexionSQLiteOpenHelper(MisTurnos.this);


        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
//        String[] projection = {
//                EstructuraBD.NOMBRE_COLUMNA_FECHA,
//                EstructuraBD.NOMBRE_COLUMNA_ESPECIALIDAD
//        };
//
//        // Filter results WHERE "title" = 'My Title'
//        String selection = null; // this will select all rows
////        String[] selectionArgs = { "My Title" };
//
//        // How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                EstructuraBD.NOMBRE_COLUMNA_FECHA + " DESC";
//
//        Cursor c = db.query(
//                EstructuraBD.TABLE_NAME,                     // The table to query
//                projection,                               // The columns to return
//                selection,                                // The columns for the WHERE clause
//                null,                            // The values for the WHERE clause
//                null,                                     // don't group the rows
//                null,                                     // don't filter by row groups
//                sortOrder                                 // The sort order
//        );
//
//        c.moveToFirst(); //Con esto tenemos lo que traemos de la consulta
    }
}
