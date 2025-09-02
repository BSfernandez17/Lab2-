package com.brayan.miapp.View.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.brayan.miapp.Model.Persona;
import com.brayan.miapp.View.controllers.PersonaController;

public class FormularioPersona extends VBox {
    
    private TextField txtId, txtNombres, txtApellidos, txtEmail;
    private PersonaController controller;
    
    public FormularioPersona(PersonaController controller) {
        this.controller = controller;
        initializeComponents();
        setupLayout();
        setupStyles();
    }
    
    private void initializeComponents() {
        txtId = new TextField();
        txtNombres = new TextField();
        txtApellidos = new TextField();
        txtEmail = new TextField();
    }
    
    private void setupLayout() {
        this.setSpacing(15);
        this.setPadding(new Insets(20, 20, 20, 0));
        this.setPrefWidth(320);
        
        Label lblTitulo = new Label("Gestión de Personas");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Campos del formulario
        GridPane formulario = createFormulario();
        
        // Botones
        VBox panelBotones = createBotones();
        
        this.getChildren().addAll(lblTitulo, formulario, panelBotones);
    }
    
    private GridPane createFormulario() {
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(15);
        formulario.setPadding(new Insets(10));
        
        // Aplicar estilos a los campos
        String estiloTextField = "-fx-pref-width: 180; -fx-padding: 8; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-background-radius: 5;";
        txtId.setStyle(estiloTextField);
        txtNombres.setStyle(estiloTextField);
        txtApellidos.setStyle(estiloTextField);
        txtEmail.setStyle(estiloTextField);
        
        // Etiquetas con estilo
        String estiloLabel = "-fx-font-weight: bold; -fx-text-fill: #34495e;";
        Label lblId = new Label("ID:");
        Label lblNombres = new Label("Nombres:");
        Label lblApellidos = new Label("Apellidos:");
        Label lblEmail = new Label("Email:");
        
        lblId.setStyle(estiloLabel);
        lblNombres.setStyle(estiloLabel);
        lblApellidos.setStyle(estiloLabel);
        lblEmail.setStyle(estiloLabel);
        
        formulario.add(lblId, 0, 0);
        formulario.add(txtId, 1, 0);
        formulario.add(lblNombres, 0, 1);
        formulario.add(txtNombres, 1, 1);
        formulario.add(lblApellidos, 0, 2);
        formulario.add(txtApellidos, 1, 2);
        formulario.add(lblEmail, 0, 3);
        formulario.add(txtEmail, 1, 3);
        
        return formulario;
    }
    
    private VBox createBotones() {
        VBox panelBotones = new VBox(10);
        Button btnAgregar = new Button("➕ Agregar");
        Button btnActualizar = new Button("✏️ Actualizar");
        Button btnEliminar = new Button("🗑️ Eliminar");
        Button btnLimpiar = new Button("🧹 Limpiar");
        
        String estiloBotones = "-fx-pref-width: 150; -fx-padding: 10; -fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5;";
        btnAgregar.setStyle(estiloBotones + "-fx-background-color: #27ae60; -fx-text-fill: white;");
        btnActualizar.setStyle(estiloBotones + "-fx-background-color: #3498db; -fx-text-fill: white;");
        btnEliminar.setStyle(estiloBotones + "-fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnLimpiar.setStyle(estiloBotones + "-fx-background-color: #95a5a6; -fx-text-fill: white;");
        
        btnAgregar.setOnAction(e -> controller.agregarPersona());
        btnActualizar.setOnAction(e -> controller.actualizarPersona());
        btnEliminar.setOnAction(e -> controller.eliminarPersona());
        btnLimpiar.setOnAction(e -> limpiarFormulario());
        
        panelBotones.getChildren().addAll(btnAgregar, btnActualizar, btnEliminar, btnLimpiar);
        panelBotones.setAlignment(Pos.CENTER);
        
        return panelBotones;
    }
    
    private void setupStyles() {
        this.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 0 2 0 0;");
    }
    
    public void limpiarFormulario() {
        txtId.clear();
        txtNombres.clear();
        txtApellidos.clear();
        txtEmail.clear();
        controller.mostrarEstado("🧹 Formulario limpiado", false);
    }
    
    public void cargarPersona(Persona persona) {
        txtId.setText(String.valueOf(persona.getId()));
        txtNombres.setText(persona.getNombres());
        txtApellidos.setText(persona.getApellidos());
        txtEmail.setText(persona.getEmail());
    }
    
    public boolean validarCampos() {
        if (txtId.getText().isEmpty() || txtNombres.getText().isEmpty() || 
            txtApellidos.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            controller.mostrarEstado("⚠️ Todos los campos son obligatorios", true);
            return false;
        }
        
        try {
            Double.parseDouble(txtId.getText());
        } catch (NumberFormatException e) {
            controller.mostrarEstado("⚠️ El ID debe ser un número válido", true);
            return false;
        }
        
        if (!txtEmail.getText().contains("@")) {
            controller.mostrarEstado("⚠️ El email debe tener un formato válido", true);
            return false;
        }
        
        return true;
    }
    
    public Persona getPersonaFromForm() {
        return new Persona(
            Double.parseDouble(txtId.getText()),
            txtNombres.getText(),
            txtApellidos.getText(),
            txtEmail.getText()
        );
    }
    
    public String getIdFromForm() {
        return txtId.getText();
    }
}
