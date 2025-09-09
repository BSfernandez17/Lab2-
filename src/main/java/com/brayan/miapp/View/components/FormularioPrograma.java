package com.brayan.miapp.View.components;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.brayan.miapp.Model.Programa;
import com.brayan.miapp.Model.Facultad;

import java.util.Date;
import java.util.List;

public class FormularioPrograma {
    private VBox contenedor;
    private TextField txtId;
    private TextField txtNombre;
    private TextField txtDuracion;
    private DatePicker dtpRegistro;
    private ComboBox<Facultad> cmbFacultad;
    private Button btnGuardar;
    private Button btnLimpiar;
    private Button btnEliminar;
    
    private Programa programaActual;
    private boolean esActualizacion = false;
    
    public FormularioPrograma() {
        inicializarComponentes();
        configurarEventos();
    }
    
    private void inicializarComponentes() {
        contenedor = new VBox(15);
        contenedor.setPadding(new Insets(10));
        
        // Título
        Label titulo = new Label("Formulario de Programa");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Grid para los campos
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        
        // Campos del formulario
        grid.add(new Label("ID:"), 0, 0);
        txtId = new TextField();
        txtId.setPromptText("Auto-generado");
        txtId.setEditable(false);
        grid.add(txtId, 1, 0);
        
        grid.add(new Label("Nombre:"), 0, 1);
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del programa");
        grid.add(txtNombre, 1, 1);
        
        grid.add(new Label("Duración (semestres):"), 0, 2);
        txtDuracion = new TextField();
        txtDuracion.setPromptText("Ej: 8");
        grid.add(txtDuracion, 1, 2);
        
        grid.add(new Label("Fecha de Registro:"), 0, 3);
        dtpRegistro = new DatePicker();
        dtpRegistro.setPromptText("Seleccionar fecha");
        grid.add(dtpRegistro, 1, 3);
        
        grid.add(new Label("Facultad:"), 0, 4);
        cmbFacultad = new ComboBox<>();
        cmbFacultad.setPromptText("Seleccionar facultad");
        cmbFacultad.setPrefWidth(200);
        
        // Configurar StringConverter para mostrar el nombre de la facultad
        cmbFacultad.setConverter(new javafx.util.StringConverter<Facultad>() {
            @Override
            public String toString(Facultad facultad) {
                return facultad != null ? facultad.getNombre() : "";
            }
            
            @Override
            public Facultad fromString(String string) {
                return null; // No necesario para este caso
            }
        });
        
        grid.add(cmbFacultad, 1, 4);
        
        // Botones
        HBox botonesBox = new HBox(10);
        btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        btnLimpiar = new Button("Limpiar");
        btnLimpiar.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        
        btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        btnEliminar.setVisible(false);
        
        botonesBox.getChildren().addAll(btnGuardar, btnLimpiar, btnEliminar);
        
        contenedor.getChildren().addAll(titulo, grid, botonesBox);
    }
    
    private void configurarEventos() {
        btnLimpiar.setOnAction(e -> limpiarFormulario());
    }
    
    public void cargarFacultades(List<Facultad> facultades) {
        ObservableList<Facultad> facultadItems = FXCollections.observableArrayList(facultades);
        cmbFacultad.setItems(facultadItems);
    }
    
    public void cargarPrograma(Programa programa) {
        if (programa != null) {
            this.programaActual = programa;
            this.esActualizacion = true;
            
            txtId.setText(programa.getId() != null ? programa.getId().toString() : "");
            txtNombre.setText(programa.getNombre());
            txtDuracion.setText(programa.getDuracion() != null ? programa.getDuracion().toString() : "");
            
            if (programa.getRegistro() != null) {
                dtpRegistro.setValue(programa.getRegistro().toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate());
            }
            
            if (programa.getFacultad() != null) {
                cmbFacultad.setValue(programa.getFacultad());
            }
            
            btnEliminar.setVisible(true);
        }
    }
    
    public void limpiarFormulario() {
        txtId.clear();
        txtNombre.clear();
        txtDuracion.clear();
        dtpRegistro.setValue(null);
        cmbFacultad.setValue(null);
        this.programaActual = null;
        this.esActualizacion = false;
        btnEliminar.setVisible(false);
    }
    
    public Programa obtenerProgramaDelFormulario() {
        try {
            Double id = txtId.getText().isEmpty() ? null : Double.parseDouble(txtId.getText());
            String nombre = txtNombre.getText().trim();
            Double duracion = txtDuracion.getText().isEmpty() ? null : Double.parseDouble(txtDuracion.getText());
            
            // Si no se selecciona fecha, usar la fecha actual
            Date registro = dtpRegistro.getValue() != null ? 
                Date.from(dtpRegistro.getValue().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()) : 
                new Date(); // Fecha actual por defecto
            
            Facultad facultad = cmbFacultad.getValue();
            
            if (nombre.isEmpty()) {
                mostrarError("El nombre es obligatorio");
                return null;
            }
            
            if (duracion == null || duracion <= 0) {
                mostrarError("La duración debe ser un número positivo");
                return null;
            }
            
            if (facultad == null) {
                mostrarError("Debe seleccionar una facultad");
                return null;
            }
            
            return new Programa(id, nombre, duracion, registro, facultad);
            
        } catch (NumberFormatException e) {
            mostrarError("Error en formato de números");
            return null;
        }
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public boolean validarFormulario() {
        return obtenerProgramaDelFormulario() != null;
    }
    
    // Getters
    public VBox getContenedor() { return contenedor; }
    public Button getBtnGuardar() { return btnGuardar; }
    public Button getBtnLimpiar() { return btnLimpiar; }
    public Button getBtnEliminar() { return btnEliminar; }
    public boolean esActualizacion() { return esActualizacion; }
    public Programa getProgramaActual() { return programaActual; }
}
