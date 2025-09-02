package com.brayan.miapp.View.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.brayan.miapp.View.controllers.InscripcionController;

public class ListaInscripciones extends VBox {
    
    private TableView<InscripcionTableData> tablaInscripciones;
    private InscripcionController controller;
    
    public ListaInscripciones(InscripcionController controller) {
        this.controller = controller;
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        tablaInscripciones = new TableView<>();
        tablaInscripciones.setPrefHeight(400);
        tablaInscripciones.setStyle("-fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        // Crear columnas
        TableColumn<InscripcionTableData, String> colCurso = new TableColumn<>("Curso");
        colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        colCurso.setPrefWidth(200);
        
        TableColumn<InscripcionTableData, String> colEstudiante = new TableColumn<>("Estudiante");
        colEstudiante.setCellValueFactory(new PropertyValueFactory<>("estudiante"));
        colEstudiante.setPrefWidth(200);
        
        TableColumn<InscripcionTableData, String> colAño = new TableColumn<>("Año");
        colAño.setCellValueFactory(new PropertyValueFactory<>("año"));
        colAño.setPrefWidth(80);
        
        TableColumn<InscripcionTableData, String> colSemestre = new TableColumn<>("Semestre");
        colSemestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));
        colSemestre.setPrefWidth(100);
        
        @SuppressWarnings("unchecked")
        TableColumn<InscripcionTableData, String>[] columnas = new TableColumn[]{colCurso, colEstudiante, colAño, colSemestre};
        tablaInscripciones.getColumns().addAll(columnas);
        
        // Doble clic para editar
        tablaInscripciones.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                controller.cargarInscripcionSeleccionada(tablaInscripciones.getSelectionModel().getSelectedIndex());
            }
        });
    }
    
    private void setupLayout() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        
        Label lblTitulo = new Label("Inscripciones Registradas");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        this.getChildren().addAll(lblTitulo, tablaInscripciones);
    }
    
    public void actualizarLista(java.util.List<String> inscripciones) {
        ObservableList<InscripcionTableData> datos = FXCollections.observableArrayList();
        
        for (String inscripcionStr : inscripciones) {
            try {
                datos.add(parsearInscripcion(inscripcionStr));
            } catch (Exception e) {
                continue;
            }
        }
        
        tablaInscripciones.setItems(datos);
    }
    
    private InscripcionTableData parsearInscripcion(String inscripcionStr) {
        try {
            String curso = "N/A";
            String estudiante = "N/A";
            String año = "N/A";
            String semestre = "N/A";
            
            // Extraer curso
            int cursoStart = inscripcionStr.indexOf("curso=") + 6;
            int cursoEnd = inscripcionStr.indexOf(", año=");
            if (cursoStart > 5 && cursoEnd > cursoStart) {
                curso = inscripcionStr.substring(cursoStart, cursoEnd);
            }
            
            // Extraer año
            int añoStart = inscripcionStr.indexOf("año=") + 4;
            int añoEnd = inscripcionStr.indexOf(", semestre=");
            if (añoStart > 3 && añoEnd > añoStart) {
                año = inscripcionStr.substring(añoStart, añoEnd);
            }
            
            // Extraer semestre
            int semestreStart = inscripcionStr.indexOf("semestre=") + 9;
            int semestreEnd = inscripcionStr.indexOf(", estudiante=");
            if (semestreStart > 8 && semestreEnd > semestreStart) {
                semestre = inscripcionStr.substring(semestreStart, semestreEnd);
            }
            
            // Extraer estudiante
            int estudianteStart = inscripcionStr.indexOf("estudiante=") + 11;
            int estudianteEnd = inscripcionStr.lastIndexOf("}");
            if (estudianteStart > 10 && estudianteEnd > estudianteStart) {
                estudiante = inscripcionStr.substring(estudianteStart, estudianteEnd);
            }
            
            return new InscripcionTableData(curso, estudiante, año, semestre);
        } catch (Exception e) {
            return new InscripcionTableData("Error", "Error", "Error", "Error");
        }
    }
    
    public InscripcionTableData getSeleccionada() {
        return tablaInscripciones.getSelectionModel().getSelectedItem();
    }
    
    // Clase interna para los datos de la tabla
    public static class InscripcionTableData {
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
        
        public String getCurso() { return curso; }
        public void setCurso(String curso) { this.curso = curso; }
        
        public String getEstudiante() { return estudiante; }
        public void setEstudiante(String estudiante) { this.estudiante = estudiante; }
        
        public String getAño() { return año; }
        public void setAño(String año) { this.año = año; }
        
        public String getSemestre() { return semestre; }
        public void setSemestre(String semestre) { this.semestre = semestre; }
    }
}
