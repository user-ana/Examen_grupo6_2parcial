package com.example.pm2examengrupo6.Config;

public class Persona {

    public int id;
    public String nombre;
    public int telefono;
    public String latitud;
    public String longitud;
    public String foto;

    public Persona(int id, String nombre, int telefono, String latitud, String longitud, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.foto = foto;
    }

    public Persona(){

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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    //para pasar a string
    @Override
    public String toString() {
        return "Persona{" +
                "foto='" + foto + '\'' +
                '}';
    }
}
