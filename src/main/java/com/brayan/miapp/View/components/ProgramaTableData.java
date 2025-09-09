package com.brayan.miapp.View.components;

public class ProgramaTableData {
    private String id;
    private String nombre;
    private String duracion;
    private String registro;
    private String facultad;
    
    public ProgramaTableData(String id, String nombre, String duracion, String registro, String facultad) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.registro = registro;
        this.facultad = facultad;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
    
    public String getRegistro() { return registro; }
    public void setRegistro(String registro) { this.registro = registro; }
    
    public String getFacultad() { return facultad; }
    public void setFacultad(String facultad) { this.facultad = facultad; }
}
