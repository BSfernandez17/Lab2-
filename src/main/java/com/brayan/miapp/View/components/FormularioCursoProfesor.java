package com.brayan.miapp.View.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.brayan.miapp.View.controllers.CursoProfesorController;
import com.brayan.miapp.Model.Curso;
import com.brayan.miapp.Model.Profesor;

public class FormularioCursoProfesor extends VBox {
    
    private CursoProfesorController controller;
    private ComboBox<Curso> cbCurso;
    private ComboBox<Profesor> cbProfesor;
    
    public FormularioCursoProfesor(CursoProfesorController controller) {
        this.controller = controller;
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        cbCurso = new ComboBox<>();
        cbProfesor = new ComboBox<>();
        
        // Configurar ComboBoxes
        cbCurso.setPrefWidth(250);
        cbProfesor.setPrefWidth(250);
        
        // Aplicar estilos
        String estiloComboBox = "-fx-pref-width: 250; -fx-padding: 8; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-background-radius: 5;";
        cbCurso.setStyle(estiloComboBox);
        cbProfesor.setStyle(estiloComboBox);
    }
    
    private void setupLayout() {
        this.setSpacing(15);
        this.setPadding(new Insets(20, 20, 20, 0));
        this.setPrefWidth(350);
        this.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 0 2 0 0;");
        
        Label lblTitulo = new Label("Asignación Curso-Profesor");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Campos del formulario
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(15);
        formulario.setPadding(new Insets(10));
        
        // Etiquetas
        String estiloLabel = "-fx-font-weight: bold; -fx-text-fill: #34495e;";
        Label lblCurso = new Label("Curso:");
        Label lblProfesor = new Label("Profesor:");
        
        lblCurso.setStyle(estiloLabel);
        lblProfesor.setStyle(estiloLabel);
        
        formulario.add(lblCurso, 0, 0);
        formulario.add(cbCurso, 1, 0);
        formulario.add(lblProfesor, 0, 1);
        formulario.add(cbProfesor, 1, 1);
        
        // Botones
        VBox panelBotones = new VBox(10);
        Button btnAsignar = new Button("➕ Asignar");
        Button btnDesasignar = new Button("🗑️ Desasignar");
        Button btnLimpiar = new Button("🧹 Limpiar");
        Button btnRefrescar = new Button("🔄 Refrescar");
        
        String estiloBotones = "-fx-pref-width: 150; -fx-padding: 10; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;";
        btnAsignar.setStyle(estiloBotones + "-fx-background-color: #27ae60; -fx-text-fill: white;");
        btnDesasignar.setStyle(estiloBotones + "-fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnLimpiar.setStyle(estiloBotones + "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        btnRefrescar.setStyle(estiloBotones + "-fx-background-color: #f39c12; -fx-text-fill: white;");
        
        btnAsignar.setOnAction(e -> controller.asignarCursoProfesor());
        btnDesasignar.setOnAction(e -> controller.desasignarCursoProfesor());
        btnLimpiar.setOnAction(e -> controller.limpiarFormulario());
        btnRefrescar.setOnAction(e -> controller.refrescarDatos());
        
        panelBotones.getChildren().addAll(btnAsignar, btnDesasignar, btnLimpiar, btnRefrescar);
        panelBotones.setAlignment(Pos.CENTER);
        
        this.getChildren().addAll(lblTitulo, formulario, panelBotones);
    }
    
    // Métodos públicos para el controlador
    public boolean validarCampos() {
        return cbCurso.getValue() != null && cbProfesor.getValue() != null;
    }
    
    public Curso getCursoSeleccionado() {
        return cbCurso.getValue();
    }
    
    public Profesor getProfesorSeleccionado() {
        return cbProfesor.getValue();
    }
    
    public void limpiarFormulario() {
        cbCurso.setValue(null);
        cbProfesor.setValue(null);
    }
    
    public void cargarDatos(Curso curso, Profesor profesor) {
        cbCurso.setValue(curso);
        cbProfesor.setValue(profesor);
    }
    
    public void actualizarComboBoxes(java.util.List<Curso> cursos, java.util.List<Profesor> profesores) {
        cbCurso.getItems().clear();
        cbCurso.getItems().addAll(cursos);
        
        cbProfesor.getItems().clear();
        cbProfesor.getItems().addAll(profesores);
    }
}
