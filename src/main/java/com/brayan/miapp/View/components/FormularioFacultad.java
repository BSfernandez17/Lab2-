package com.brayan.miapp.View.components;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import com.brayan.miapp.Model.Facultad;
import com.brayan.miapp.Model.Persona;
import com.brayan.miapp.DAO.FacultadDAO;
import com.brayan.miapp.DAO.PersonaDAO;

import java.util.List;

public class FormularioFacultad {
    private VBox contenedor;
    private TextField txtId;
    private TextField txtNombre;
    private ComboBox<Persona> cmbDecano;
    private Button btnGuardar;
    private Button btnLimpiar;
    private Button btnCancelar;
    
    private FacultadDAO facultadDAO;
    private PersonaDAO personaDAO;
    private Facultad facultadActual;
    
    // Callback para notificar cambios
    private Runnable onFacultadGuardada;
    
    public FormularioFacultad() {
        this.facultadDAO = new FacultadDAO();
        this.personaDAO = new PersonaDAO();
        inicializarComponentes();
        configurarEventos();
        cargarDecanos();
    }
    
    private void inicializarComponentes() {
        contenedor = new VBox(15);
        contenedor.setPadding(new Insets(20));
        contenedor.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-radius: 5;");
        
        // Título
        Label titulo = new Label("Gestión de Facultades");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #495057;");
        
        // Grid para el formulario
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER_LEFT);
        
        // ID (solo lectura)
        Label lblId = new Label("ID:");
        lblId.setStyle("-fx-font-weight: bold;");
        txtId = new TextField();
        txtId.setEditable(false);
        txtId.setStyle("-fx-background-color: #e9ecef;");
        txtId.setPromptText("Se genera automáticamente");
        
        // Nombre
        Label lblNombre = new Label("Nombre de la Facultad:");
        lblNombre.setStyle("-fx-font-weight: bold;");
        txtNombre = new TextField();
        txtNombre.setPromptText("Ej: Facultad de Ingeniería");
        txtNombre.setPrefWidth(300);
        
        // Decano
        Label lblDecano = new Label("Decano:");
        lblDecano.setStyle("-fx-font-weight: bold;");
        cmbDecano = new ComboBox<>();
        cmbDecano.setPrefWidth(300);
        cmbDecano.setPromptText("Seleccione un decano");
        
        // Configurar StringConverter para mostrar nombres completos
        cmbDecano.setConverter(new javafx.util.StringConverter<Persona>() {
            @Override
            public String toString(Persona persona) {
                if (persona == null) return "";
                return persona.getNombres() + " " + persona.getApellidos();
            }
            
            @Override
            public Persona fromString(String string) {
                return null; // No necesario para ComboBox
            }
        });
        

        

        
        // Agregar componentes al grid
        grid.add(lblId, 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(lblNombre, 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(lblDecano, 0, 2);
        grid.add(cmbDecano, 1, 2);
        
        // Botones
        HBox botones = new HBox(10);
        botones.setAlignment(Pos.CENTER);
        
        btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        btnGuardar.setPrefWidth(100);
        
        btnLimpiar = new Button("Limpiar");
        btnLimpiar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white;");
        btnLimpiar.setPrefWidth(100);
        
        btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        btnCancelar.setPrefWidth(100);
        
        botones.getChildren().addAll(btnGuardar, btnLimpiar, btnCancelar);
        
        contenedor.getChildren().addAll(titulo, grid, botones);
    }
    
    private void configurarEventos() {
        btnGuardar.setOnAction(e -> guardarFacultad());
        btnLimpiar.setOnAction(e -> limpiarFormulario());
        btnCancelar.setOnAction(e -> {
            limpiarFormulario();
            facultadActual = null;
        });
    }
    
    private void cargarDecanos() {
        try {
            List<Persona> personas = personaDAO.listar();
            cmbDecano.getItems().clear();
            cmbDecano.getItems().addAll(personas);
        } catch (Exception e) {
            mostrarError("Error al cargar la lista de personas: " + e.getMessage());
        }
    }
    
    private void guardarFacultad() {
        if (!validarFormulario()) {
            return;
        }
        
        try {
            Facultad facultad = new Facultad();
            
            // Si estamos editando, usar el ID existente
            if (facultadActual != null) {
                facultad.setId(facultadActual.getId());
            }
            
            facultad.setNombre(txtNombre.getText().trim());
            facultad.setDecano(cmbDecano.getValue());
            
            if (facultadActual == null) {
                // Insertar nueva facultad
                facultadDAO.insertar(facultad);
                mostrarExito("Facultad creada exitosamente");
                txtId.setText(facultad.getId() != null ? facultad.getId().toString() : "");
            } else {
                // Actualizar facultad existente
                facultadDAO.actualizar(facultad);
                mostrarExito("Facultad actualizada exitosamente");
            }
            
            // Notificar cambios
            if (onFacultadGuardada != null) {
                onFacultadGuardada.run();
            }
            
        } catch (Exception e) {
            mostrarError("Error al guardar la facultad: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private boolean validarFormulario() {
        if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()) {
            mostrarError("El nombre de la facultad es obligatorio");
            txtNombre.requestFocus();
            return false;
        }
        
        if (cmbDecano.getValue() == null) {
            mostrarError("Debe seleccionar un decano");
            cmbDecano.requestFocus();
            return false;
        }
        
        return true;
    }
    
    public void cargarFacultad(Facultad facultad) {
        this.facultadActual = facultad;
        
        txtId.setText(facultad.getId() != null ? facultad.getId().toString() : "");
        txtNombre.setText(facultad.getNombre() != null ? facultad.getNombre() : "");
        
        // Seleccionar el decano en el ComboBox
        if (facultad.getDecano() != null) {
            for (Persona persona : cmbDecano.getItems()) {
                if (Double.valueOf(persona.getId()).equals(Double.valueOf(facultad.getDecano().getId()))) {
                    cmbDecano.setValue(persona);
                    break;
                }
            }
        }
    }
    
    public void limpiarFormulario() {
        txtId.clear();
        txtNombre.clear();
        cmbDecano.setValue(null);
        facultadActual = null;
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // Getters
    public VBox getContenedor() { return contenedor; }
    public Facultad getFacultadActual() { return facultadActual; }
    
    // Setter para callback
    public void setOnFacultadGuardada(Runnable callback) {
        this.onFacultadGuardada = callback;
    }
    
    public void actualizarDecanos() {
        cargarDecanos();
    }
}
