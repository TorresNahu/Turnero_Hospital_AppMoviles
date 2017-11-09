package com.example.usuario.turnero_hospital.Model;

/**
 * Created by Juan on 08/11/2017.
 */

public class Especialidad {

    private int id;
    private String nombre;

    public Especialidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Especialidad() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
