package com.brayan.miapp.View.modules.componentes.profesores;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import com.brayan.miapp.Model.Profesor;
import com.brayan.miapp.View.controllers.ProfesorController;
import com.brayan.miapp.View.components.ProfesorTableData;

public class FormularioProfesor extends VBox {
    
    private TextField txtId;
    private TextField txtNombres;
    private TextField txtApellidos;
    private TextField txtEmail;
    private ComboBox<String> cmbTipoContrato;
    private Button btnAgregar;
    private Button btnActualizar;
    private Button btnLimpiar;
    
    private ProfesorController controller;
    private BarraEstadoProfesores barraEstado;
    private boolean esActualizacion = false;
    
    public FormularioProfesor(BarraEstadoProfesores barraEstado) {
        this.barraEstado = barraEstado;
        this.controller = new ProfesorController();
        this.controller.setStatusCallback(barraEstado);
        
        configurarFormulario();
        configurarEventos();
    }
    
    private void configurarFormulario() {
        setSpacing(10);
        setPadding(new Insets(10));
        setAlignment(Pos.TOP_LEFT);
        setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px; -fx-padding: 10px;");
        
        Label titulo = new Label("Datos del Profesor");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_LEFT);
        
        // Campos del formulario
        txtId = new TextField();
        txtId.setPromptText("ID del profesor");
        
        txtNombres = new TextField();
        txtNombres.setPromptText("Nombres");
        
        txtApellidos = new TextField();
        txtApellidos.setPromptText("Apellidos");
        
        txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        
        cmbTipoContrato = new ComboBox<>();
        cmbTipoContrato.getItems().addAll("Catedra", "Tiempo Completo", "Medio Tiempo");
        cmbTipoContrato.setPromptText("Seleccione tipo de contrato");
        
        // Añadir al grid
        grid.add(new Label("ID:"), 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(new Label("Nombres:"), 0, 1);
        grid.add(txtNombres, 1, 1);
        grid.add(new Label("Apellidos:"), 0, 2);
        grid.add(txtApellidos, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(txtEmail, 1, 3);
        grid.add(new Label("Tipo Contrato:"), 0, 4);
        grid.add(cmbTipoContrato, 1, 4);
        
        // Botones
        btnAgregar = new Button("Agregar Profesor");
        btnActualizar = new Button("Actualizar Profesor");
        btnLimpiar = new Button("Limpiar");
        
        btnAgregar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnActualizar.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnLimpiar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        
        btnActualizar.setVisible(false);
        
        getChildren().addAll(titulo, grid, btnAgregar, btnActualizar, btnLimpiar);
    }
    
    private void configurarEventos() {
        btnAgregar.setOnAction(e -> agregarProfesor());
        btnActualizar.setOnAction(e -> actualizarProfesor());
        btnLimpiar.setOnAction(e -> limpiarFormulario());
    }
    
    private void agregarProfesor() {
        if (!validarCampos()) return;
        
        try {
            Double id = Double.parseDouble(txtId.getText());
            Profesor profesor = new Profesor(
                id,
                txtNombres.getText(),
                txtApellidos.getText(),
                txtEmail.getText(),
                cmbTipoContrato.getValue()
            );
            
            controller.agregarProfesor(profesor);
            limpiarFormulario();
            notificarCambio();
        } catch (NumberFormatException ex) {
            barraEstado.mostrarEstado("❌ ID debe ser un número válido", true);
        }
    }
    
    private void actualizarProfesor() {
        if (!validarCampos()) return;
        
        try {
            Double id = Double.parseDouble(txtId.getText());
            Profesor profesor = new Profesor(
                id,
                txtNombres.getText(),
                txtApellidos.getText(),
                txtEmail.getText(),
                cmbTipoContrato.getValue()
            );
            
            controller.actualizarProfesor(profesor);
            limpiarFormulario();
            notificarCambio();
        } catch (NumberFormatException ex) {
            barraEstado.mostrarEstado("❌ ID debe ser un número válido", true);
        }
    }
    
    private boolean validarCampos() {
        if (txtId.getText().trim().isEmpty() ||
            txtNombres.getText().trim().isEmpty() ||
            txtApellidos.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty() ||
            cmbTipoContrato.getValue() == null) {
            
            barraEstado.mostrarEstado("❌ Todos los campos son obligatorios", true);
            return false;
        }
        return true;
    }
    
    public void cargarProfesor(ProfesorTableData profesorData) {
        txtId.setText(profesorData.getId());
        txtNombres.setText(profesorData.getNombres());
        txtApellidos.setText(profesorData.getApellidos());
        txtEmail.setText(profesorData.getEmail());
        cmbTipoContrato.setValue(profesorData.getTipoContrato());
        
        btnAgregar.setVisible(false);
        btnActualizar.setVisible(true);
        esActualizacion = true;
        txtId.setEditable(false);
    }
    
    public void limpiarFormulario() {
        txtId.clear();
        txtNombres.clear();
        txtApellidos.clear();
        txtEmail.clear();
        cmbTipoContrato.setValue(null);
        
        btnAgregar.setVisible(true);
        btnActualizar.setVisible(false);
        esActualizacion = false;
        txtId.setEditable(true);
        barraEstado.limpiarEstado();
    }
    
    private Runnable onCambioListener;
    
    public void setOnCambio(Runnable listener) {
        this.onCambioListener = listener;
    }
    
    private void notificarCambio() {
        if (onCambioListener != null) {
            onCambioListener.run();
        }
    }
}
