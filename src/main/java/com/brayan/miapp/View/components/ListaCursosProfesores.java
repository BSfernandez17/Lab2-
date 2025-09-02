package com.brayan.miapp.View.components;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.brayan.miapp.View.controllers.CursoProfesorController;

public class ListaCursosProfesores extends VBox {
    
    private TableView<CursoProfesorTableData> tablaAsignaciones;
    private CursoProfesorController controller;
    
    public ListaCursosProfesores(CursoProfesorController controller) {
        this.controller = controller;
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        tablaAsignaciones = new TableView<>();
        tablaAsignaciones.setPrefHeight(400);
        tablaAsignaciones.setStyle("-fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        // Crear columnas
        TableColumn<CursoProfesorTableData, String> colCurso = new TableColumn<>("Curso");
        colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        colCurso.setPrefWidth(250);
        
        TableColumn<CursoProfesorTableData, String> colProfesor = new TableColumn<>("Profesor");
        colProfesor.setCellValueFactory(new PropertyValueFactory<>("profesor"));
        colProfesor.setPrefWidth(250);
        
        TableColumn<CursoProfesorTableData, String> colNivel = new TableColumn<>("Nivel");
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        colNivel.setPrefWidth(120);
        
        TableColumn<CursoProfesorTableData, String> colContrato = new TableColumn<>("Contrato");
        colContrato.setCellValueFactory(new PropertyValueFactory<>("contrato"));
        colContrato.setPrefWidth(120);
        
        @SuppressWarnings("unchecked")
        TableColumn<CursoProfesorTableData, String>[] columnas = new TableColumn[]{colCurso, colProfesor, colNivel, colContrato};
        tablaAsignaciones.getColumns().addAll(columnas);
        
        // Doble clic para editar
        tablaAsignaciones.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                controller.cargarAsignacionSeleccionada(tablaAsignaciones.getSelectionModel().getSelectedIndex());
            }
        });
    }
    
    private void setupLayout() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        
        Label lblTitulo = new Label("Asignaciones Curso-Profesor");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        this.getChildren().addAll(lblTitulo, tablaAsignaciones);
    }
    
    public void actualizarLista(java.util.List<String> asignaciones) {
        ObservableList<CursoProfesorTableData> datos = FXCollections.observableArrayList();
        
        for (String asignacionStr : asignaciones) {
            try {
                datos.add(parsearAsignacion(asignacionStr));
            } catch (Exception e) {
                continue;
            }
        }
        
        tablaAsignaciones.setItems(datos);
    }
    
    private CursoProfesorTableData parsearAsignacion(String asignacionStr) {
        try {
            String curso = "N/A";
            String profesor = "N/A";
            String nivel = "N/A";
            String contrato = "N/A";
            
            // Extraer curso
            int cursoStart = asignacionStr.indexOf("curso=") + 6;
            int cursoEnd = asignacionStr.indexOf(", profesor=");
            if (cursoStart > 5 && cursoEnd > cursoStart) {
                curso = asignacionStr.substring(cursoStart, cursoEnd);
            }
            
            // Extraer profesor
            int profesorStart = asignacionStr.indexOf("profesor=") + 9;
            int profesorEnd = asignacionStr.indexOf(", nivel=");
            if (profesorStart > 8 && profesorEnd > profesorStart) {
                profesor = asignacionStr.substring(profesorStart, profesorEnd);
            }
            
            // Extraer nivel
            int nivelStart = asignacionStr.indexOf("nivel=") + 6;
            int nivelEnd = asignacionStr.indexOf(", contrato=");
            if (nivelStart > 5 && nivelEnd > nivelStart) {
                nivel = asignacionStr.substring(nivelStart, nivelEnd);
            }
            
            // Extraer contrato
            int contratoStart = asignacionStr.indexOf("contrato=") + 9;
            int contratoEnd = asignacionStr.lastIndexOf("}");
            if (contratoStart > 8 && contratoEnd > contratoStart) {
                contrato = asignacionStr.substring(contratoStart, contratoEnd);
            }
            
            return new CursoProfesorTableData(curso, profesor, nivel, contrato);
        } catch (Exception e) {
            return new CursoProfesorTableData("Error", "Error", "Error", "Error");
        }
    }
    
    public CursoProfesorTableData getSeleccionada() {
        return tablaAsignaciones.getSelectionModel().getSelectedItem();
    }
    
    // Clase interna para los datos de la tabla
    public static class CursoProfesorTableData {
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
        
        public String getCurso() { return curso; }
        public void setCurso(String curso) { this.curso = curso; }
        
        public String getProfesor() { return profesor; }
        public void setProfesor(String profesor) { this.profesor = profesor; }
        
        public String getNivel() { return nivel; }
        public void setNivel(String nivel) { this.nivel = nivel; }
        
        public String getContrato() { return contrato; }
        public void setContrato(String contrato) { this.contrato = contrato; }
    }
}
