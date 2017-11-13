package com.example.usuario.turnero_hospital.Model;

/**
 * Created by Nahu on 13/11/2017.
 */

public class Turno {

    private int id;
    private String fecha;
    private String especialidad;

    public Turno(int id, String fecha, String especialidad) {
        this.id = id;
        this.fecha = fecha;
        this.especialidad = especialidad;
    }

    public Turno() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
