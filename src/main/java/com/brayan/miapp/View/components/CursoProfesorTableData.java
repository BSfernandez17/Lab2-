package com.brayan.miapp.View.components;

public class CursoProfesorTableData {
    private String curso;
    private String profesor;
    private String nivel;
    private String contrato;
    
    public CursoProfesorTableData(String curso, String profesor, String nivel, String contrato) {
        this.curso = curso;
        this.profesor = profesor;
        this.nivel = nivel;
        this.contrato = contrato;
    }
    
    public String getCurso() { 
        return curso; 
    }
    
    public void setCurso(String curso) { 
        this.curso = curso; 
    }
    
    public String getProfesor() { 
        return profesor; 
    }
    
    public void setProfesor(String profesor) { 
        this.profesor = profesor; 
    }
    
    public String getNivel() { 
        return nivel; 
    }
    
    public void setNivel(String nivel) { 
        this.nivel = nivel; 
    }
    
    public String getContrato() { 
        return contrato; 
    }
    
    public void setContrato(String contrato) { 
        this.contrato = contrato; 
    }
}
