package com.brayan.miapp.View.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.brayan.miapp.View.controllers.InscripcionController;
import com.brayan.miapp.Model.Curso;
import com.brayan.miapp.Model.Estudiante;

public class FormularioInscripcion extends VBox {
    
    private InscripcionController controller;
    private ComboBox<Curso> cbCurso;
    private ComboBox<Estudiante> cbEstudiante;
    private TextField txtAño;
    private TextField txtSemestre;
    
    public FormularioInscripcion(InscripcionController controller) {
        this.controller = controller;
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        cbCurso = new ComboBox<>();
        cbEstudiante = new ComboBox<>();
        txtAño = new TextField();
        txtSemestre = new TextField();
        
        // Configurar ComboBoxes
        cbCurso.setPrefWidth(200);
        cbEstudiante.setPrefWidth(200);
        
        // Aplicar estilos
        String estiloTextField = "-fx-pref-width: 200; -fx-padding: 8; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-background-radius: 5;";
        txtAño.setStyle(estiloTextField);
        txtSemestre.setStyle(estiloTextField);
        cbCurso.setStyle(estiloTextField);
        cbEstudiante.setStyle(estiloTextField);
    }
    
    private void setupLayout() {
        this.setSpacing(15);
        this.setPadding(new Insets(20, 20, 20, 0));
        this.setPrefWidth(350);
        this.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 0 2 0 0;");
        
        Label lblTitulo = new Label("Gestión de Inscripciones");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Campos del formulario
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(15);
        formulario.setPadding(new Insets(10));
        
        // Etiquetas
        String estiloLabel = "-fx-font-weight: bold; -fx-text-fill: #34495e;";
        Label lblCurso = new Label("Curso:");
        Label lblEstudiante = new Label("Estudiante:");
        Label lblAño = new Label("Año:");
        Label lblSemestre = new Label("Semestre:");
        
        lblCurso.setStyle(estiloLabel);
        lblEstudiante.setStyle(estiloLabel);
        lblAño.setStyle(estiloLabel);
        lblSemestre.setStyle(estiloLabel);
        
        formulario.add(lblCurso, 0, 0);
        formulario.add(cbCurso, 1, 0);
        formulario.add(lblEstudiante, 0, 1);
        formulario.add(cbEstudiante, 1, 1);
        formulario.add(lblAño, 0, 2);
        formulario.add(txtAño, 1, 2);
        formulario.add(lblSemestre, 0, 3);
        formulario.add(txtSemestre, 1, 3);
        
        // Botones
        VBox panelBotones = new VBox(10);
        Button btnInscribir = new Button("➕ Inscribir");
        Button btnEliminar = new Button("🗑️ Eliminar");
        Button btnLimpiar = new Button("🧹 Limpiar");
        Button btnRefrescar = new Button("🔄 Refrescar");
        
        String estiloBotones = "-fx-pref-width: 150; -fx-padding: 10; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;";
        btnInscribir.setStyle(estiloBotones + "-fx-background-color: #27ae60; -fx-text-fill: white;");
        btnEliminar.setStyle(estiloBotones + "-fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnLimpiar.setStyle(estiloBotones + "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        btnRefrescar.setStyle(estiloBotones + "-fx-background-color: #f39c12; -fx-text-fill: white;");
        
        btnInscribir.setOnAction(e -> controller.inscribirEstudiante());
        btnEliminar.setOnAction(e -> controller.eliminarInscripcion());
        btnLimpiar.setOnAction(e -> controller.limpiarFormulario());
        btnRefrescar.setOnAction(e -> controller.refrescarDatos());
        
        panelBotones.getChildren().addAll(btnInscribir, btnEliminar, btnLimpiar, btnRefrescar);
        panelBotones.setAlignment(Pos.CENTER);
        
        this.getChildren().addAll(lblTitulo, formulario, panelBotones);
    }
    
    // Métodos públicos para el controlador
    public boolean validarCampos() {
        if (cbCurso.getValue() == null || cbEstudiante.getValue() == null || 
            txtAño.getText().isEmpty() || txtSemestre.getText().isEmpty()) {
            return false;
        }
        
        try {
            Integer.parseInt(txtAño.getText());
            Integer.parseInt(txtSemestre.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        
        return true;
    }
    
    public Curso getCursoSeleccionado() {
        return cbCurso.getValue();
    }
    
    public Estudiante getEstudianteSeleccionado() {
        return cbEstudiante.getValue();
    }
    
    public Integer getAño() {
        return Integer.parseInt(txtAño.getText());
    }
    
    public Integer getSemestre() {
        return Integer.parseInt(txtSemestre.getText());
    }
    
    public void limpiarFormulario() {
        cbCurso.setValue(null);
        cbEstudiante.setValue(null);
        txtAño.clear();
        txtSemestre.clear();
    }
    
    public void cargarDatos(Curso curso, Estudiante estudiante, Integer año, Integer semestre) {
        cbCurso.setValue(curso);
        cbEstudiante.setValue(estudiante);
        txtAño.setText(año.toString());
        txtSemestre.setText(semestre.toString());
    }
    
    public void actualizarComboBoxes(java.util.List<Curso> cursos, java.util.List<Estudiante> estudiantes) {
        cbCurso.getItems().clear();
        cbCurso.getItems().addAll(cursos);
        
        cbEstudiante.getItems().clear();
        cbEstudiante.getItems().addAll(estudiantes);
    }
}
