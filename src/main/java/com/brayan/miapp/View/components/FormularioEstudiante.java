package com.brayan.miapp.View.components;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import com.brayan.miapp.Model.Estudiante;
import com.brayan.miapp.Model.Programa;
import java.util.Date;

public class FormularioEstudiante {
    private GridPane formulario;
    private TextField idField;
    private TextField nombresField;
    private TextField apellidosField;
    private TextField emailField;
    private TextField codigoField;
    private TextField programaField;
    private TextField promedioField;
    private CheckBox activoField;
    private Button btnAgregar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnLimpiar;
    
    public FormularioEstudiante() {
        inicializarComponentes();
        configurarLayout();
    }
    
    private void inicializarComponentes() {
        formulario = new GridPane();
        
        // Campos de texto
        idField = new TextField();
        idField.setPromptText("ID del estudiante");
        
        nombresField = new TextField();
        nombresField.setPromptText("Nombres");
        
        apellidosField = new TextField();
        apellidosField.setPromptText("Apellidos");
        
        emailField = new TextField();
        emailField.setPromptText("Email");
        
        codigoField = new TextField();
        codigoField.setPromptText("Código");
        
        programaField = new TextField();
        programaField.setPromptText("ID Programa");
        
        promedioField = new TextField();
        promedioField.setPromptText("Promedio");
        
        activoField = new CheckBox("Activo");
        activoField.setSelected(true);
        
        // Botones
        btnAgregar = new Button("Agregar");
        btnActualizar = new Button("Actualizar");
        btnEliminar = new Button("Eliminar");
        btnLimpiar = new Button("Limpiar");
        
        // Estilos
        btnAgregar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnActualizar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnEliminar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        btnLimpiar.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
    }
    
    private void configurarLayout() {
        formulario.setHgap(10);
        formulario.setVgap(10);
        formulario.setPadding(new Insets(10));
        
        // Labels y campos
        formulario.add(new Label("ID:"), 0, 0);
        formulario.add(idField, 1, 0);
        
        formulario.add(new Label("Nombres:"), 0, 1);
        formulario.add(nombresField, 1, 1);
        
        formulario.add(new Label("Apellidos:"), 0, 2);
        formulario.add(apellidosField, 1, 2);
        
        formulario.add(new Label("Email:"), 0, 3);
        formulario.add(emailField, 1, 3);
        
        formulario.add(new Label("Código:"), 0, 4);
        formulario.add(codigoField, 1, 4);
        
        formulario.add(new Label("Programa ID:"), 0, 5);
        formulario.add(programaField, 1, 5);
        
        formulario.add(new Label("Promedio:"), 0, 6);
        formulario.add(promedioField, 1, 6);
        
        formulario.add(new Label("Estado:"), 0, 7);
        formulario.add(activoField, 1, 7);
        
        // Botones
        formulario.add(btnAgregar, 0, 8);
        formulario.add(btnActualizar, 1, 8);
        formulario.add(btnEliminar, 0, 9);
        formulario.add(btnLimpiar, 1, 9);
        
        // Configurar expansión
        GridPane.setHgrow(idField, Priority.ALWAYS);
        GridPane.setHgrow(nombresField, Priority.ALWAYS);
        GridPane.setHgrow(apellidosField, Priority.ALWAYS);
        GridPane.setHgrow(emailField, Priority.ALWAYS);
        GridPane.setHgrow(codigoField, Priority.ALWAYS);
        GridPane.setHgrow(programaField, Priority.ALWAYS);
        GridPane.setHgrow(promedioField, Priority.ALWAYS);
    }
    
    public GridPane getFormulario() { return formulario; }
    
    public void cargarEstudiante(Estudiante estudiante) {
        if (estudiante != null) {
            idField.setText(String.valueOf(estudiante.getId()));
            nombresField.setText(estudiante.getNombres());
            apellidosField.setText(estudiante.getApellidos());
            emailField.setText(estudiante.getEmail());
            codigoField.setText(String.valueOf(estudiante.getCodigo()));
            if (estudiante.getPrograma() != null) {
                programaField.setText(String.valueOf(estudiante.getPrograma().getId()));
            }
            promedioField.setText(String.valueOf(estudiante.getPromedio()));
            activoField.setSelected(estudiante.getActivo());
        }
    }
    
    public Estudiante obtenerEstudiante() {
        try {
            String idText = idField.getText().trim();
            String codigoText = codigoField.getText().trim();
            String promedioText = promedioField.getText().trim();
            
            // Validaciones básicas
            if (idText.isEmpty() || codigoText.isEmpty() || promedioText.isEmpty() ||
                nombresField.getText().trim().isEmpty() || apellidosField.getText().trim().isEmpty()) {
                return null;
            }
            
            double id = Double.parseDouble(idText);
            if (id <= 0) {
                return null;
            }
            
            // Crear un programa temporal (esto debería mejorarse con datos reales)
            Programa programa = null;
            String programaIdText = programaField.getText().trim();
            if (!programaIdText.isEmpty()) {
                // Por ahora crear un programa básico
                programa = new Programa(Double.parseDouble(programaIdText), "", 0.0, new Date(), null);
            }
            
            return new Estudiante(
                id,
                nombresField.getText().trim(),
                apellidosField.getText().trim(),
                emailField.getText().trim(),
                Double.parseDouble(codigoText),
                programa,
                activoField.isSelected(),
                Double.parseDouble(promedioText)
            );
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public void limpiarFormulario() {
        idField.clear();
        nombresField.clear();
        apellidosField.clear();
        emailField.clear();
        codigoField.clear();
        programaField.clear();
        promedioField.clear();
        activoField.setSelected(true);
    }
    
    // Getters para botones
    public Button getBtnAgregar() { return btnAgregar; }
    public Button getBtnActualizar() { return btnActualizar; }
    public Button getBtnEliminar() { return btnEliminar; }
    public Button getBtnLimpiar() { return btnLimpiar; }
}
