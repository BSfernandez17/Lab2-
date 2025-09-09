package com.brayan.miapp.View.components;

public class EstudianteTableData {
    private String id;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String email;
    private String programa;
    private String activo;
    private String promedio;
    
    public EstudianteTableData(String id, String codigo, String nombres, String apellidos, 
                              String email, String programa, String activo, String promedio) {
        this.id = id;
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.programa = programa;
        this.activo = activo;
        this.promedio = promedio;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPrograma() { return programa; }
    public void setPrograma(String programa) { this.programa = programa; }
    
    public String getActivo() { return activo; }
    public void setActivo(String activo) { this.activo = activo; }
    
    public String getPromedio() { return promedio; }
    public void setPromedio(String promedio) { this.promedio = promedio; }
}
