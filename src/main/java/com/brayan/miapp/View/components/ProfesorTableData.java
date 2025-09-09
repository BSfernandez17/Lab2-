package com.brayan.miapp.View.components;

public class ProfesorTableData {
    private String id;
    private String nombres;
    private String apellidos;
    private String email;
    private String tipoContrato;
    
    public ProfesorTableData(String id, String nombres, String apellidos, String email, String tipoContrato) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.tipoContrato = tipoContrato;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }
}
