package com.brayan.miapp.Model;

public class Persona {
    private static long nextId = 1;
    private double id;
    private String nombres;
    private String apellidos;
    private String email;
    public Persona(Double id,String nombre, String apellidos,String email) {
        this.id = id;
        this.nombres = nombre;
        this.apellidos=apellidos;
        this.email = email;
    }
    @Override
    public String toString() {
        return "Persona{id=" + id + ", nombres='" + nombres + "', apellidos='" + apellidos + "', email='" + email + "'}";
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    }