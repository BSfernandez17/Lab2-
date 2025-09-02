package com.brayan.miapp.Model;

public class Inscripcion {
    private Curso curso;
    private Integer año;
    private Integer semestre;
    private Estudiante estudiante;

    public Inscripcion(Curso curso, Integer año, Integer semestre, Estudiante estudiante) {
        this.curso = curso;
        this.año = año;
        this.semestre = semestre;
        this.estudiante = estudiante;
    }

    @Override
    public String toString() {
        return "Inscripcion{curso=" + curso + ", año=" + año + ", semestre=" + semestre + ", estudiante=" + estudiante + "}";
    }

    // Getters and Setters
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}