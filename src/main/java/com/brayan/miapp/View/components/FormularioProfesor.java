package com.brayan.miapp.View.components;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import com.brayan.miapp.Model.Profesor;

public class FormularioProfesor {
    private GridPane formulario;
    private TextField idField;
    private TextField nombresField;
    private TextField apellidosField;
    private TextField emailField;
    private TextField tipoContratoField;
    private Button btnAgregar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnLimpiar;
    
    public FormularioProfesor() {
        inicializarComponentes();
        configurarLayout();
    }
    
    private void inicializarComponentes() {
        formulario = new GridPane();
        
        // Campos de texto
        idField = new TextField();
        idField.setPromptText("ID del profesor");
        
        nombresField = new TextField();
        nombresField.setPromptText("Nombres");
        
        apellidosField = new TextField();
        apellidosField.setPromptText("Apellidos");
        
        emailField = new TextField();
        emailField.setPromptText("Email");
        
        tipoContratoField = new TextField();
        tipoContratoField.setPromptText("Tipo de Contrato");
        
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
        
        formulario.add(new Label("Tipo Contrato:"), 0, 4);
        formulario.add(tipoContratoField, 1, 4);
        
        // Botones
        formulario.add(btnAgregar, 0, 5);
        formulario.add(btnActualizar, 1, 5);
        formulario.add(btnEliminar, 0, 6);
        formulario.add(btnLimpiar, 1, 6);
        
        // Configurar expansión
        GridPane.setHgrow(idField, Priority.ALWAYS);
        GridPane.setHgrow(nombresField, Priority.ALWAYS);
        GridPane.setHgrow(apellidosField, Priority.ALWAYS);
        GridPane.setHgrow(emailField, Priority.ALWAYS);
        GridPane.setHgrow(tipoContratoField, Priority.ALWAYS);
    }
    
    public GridPane getFormulario() { return formulario; }
    
    public void cargarProfesor(Profesor profesor) {
        if (profesor != null) {
            idField.setText(String.valueOf(profesor.getId()));
            nombresField.setText(profesor.getNombres());
            apellidosField.setText(profesor.getApellidos());
            emailField.setText(profesor.getEmail());
            tipoContratoField.setText(profesor.getTipoContrato());
        }
    }
    
    public Profesor obtenerProfesor() {
        try {
            String idText = idField.getText().trim();
            double id = idText.isEmpty() ? 0 : Double.parseDouble(idText);
            
            return new Profesor(
                id,
                nombresField.getText().trim(),
                apellidosField.getText().trim(),
                emailField.getText().trim(),
                tipoContratoField.getText().trim()
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
        tipoContratoField.clear();
    }
    
    // Getters para botones
    public Button getBtnAgregar() { return btnAgregar; }
    public Button getBtnActualizar() { return btnActualizar; }
    public Button getBtnEliminar() { return btnEliminar; }
    public Button getBtnLimpiar() { return btnLimpiar; }
}
