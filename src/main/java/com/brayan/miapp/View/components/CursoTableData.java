package com.brayan.miapp.View.components;

public class CursoTableData {
    private String id;
    private String nombre;
    private String programa;
    private String activo;
    
    public CursoTableData(String id, String nombre, String programa, String activo) {
        this.id = id;
        this.nombre = nombre;
        this.programa = programa;
        this.activo = activo;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getPrograma() { return programa; }
    public void setPrograma(String programa) { this.programa = programa; }
    
    public String getActivo() { return activo; }
    public void setActivo(String activo) { this.activo = activo; }
}
