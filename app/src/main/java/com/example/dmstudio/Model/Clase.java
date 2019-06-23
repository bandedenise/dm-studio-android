package com.example.dmstudio.Model;

public class Clase {

    private String horaInicio;
    private String horaFin;
    private String nombre;
    private String profesor;
    private Boolean enBlanco;

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEnBlanco() {
        return enBlanco;
    }

    public void setEnBlanco(Boolean enBlanco) {
        this.enBlanco = enBlanco;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
}
