package com.brayan.miapp.View.components;

public class InscripcionTableData {
    private String curso;
    private String estudiante;
    private String año;
    private String semestre;
    
    public InscripcionTableData(String curso, String estudiante, String año, String semestre) {
        this.curso = curso;
        this.estudiante = estudiante;
        this.año = año;
        this.semestre = semestre;
    }
    
    public String getCurso() { 
        return curso; 
    }
    
    public void setCurso(String curso) { 
        this.curso = curso; 
    }
    
    public String getEstudiante() { 
        return estudiante; 
    }
    
    public void setEstudiante(String estudiante) { 
        this.estudiante = estudiante; 
    }
    
    public String getAño() { 
        return año; 
    }
    
    public void setAño(String año) { 
        this.año = año; 
    }
    
    public String getSemestre() { 
        return semestre; 
    }
    
    public void setSemestre(String semestre) { 
        this.semestre = semestre; 
    }
}
