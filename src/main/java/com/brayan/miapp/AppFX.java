package com.brayan.miapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.brayan.miapp.DAO.DatabaseSetup;
import com.brayan.miapp.Model.Persona;
import com.brayan.Services.InscripcionesPersonas;

public class AppFX extends Application {
    
    private InscripcionesPersonas servicio;
    private ListView<String> listaPersonas;
    private TextField txtId, txtNombres, txtApellidos, txtEmail;
    private Label lblEstado;
    
    @Override
    public void start(Stage primaryStage) {
        // Inicializar base de datos y servicio
        DatabaseSetup.init();
        servicio = new InscripcionesPersonas();
        servicio.cargarDatos();
        
        // Configurar la ventana principal
        primaryStage.setTitle("Sistema de Gestión Universitaria");
        
        // Crear el layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Panel superior con título
        Label titulo = new Label("Sistema de Gestión Universitaria");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        HBox panelTitulo = new HBox(titulo);
        panelTitulo.setAlignment(Pos.CENTER);
        panelTitulo.setPadding(new Insets(0, 0, 20, 0));
        
        // Panel izquierdo - Formulario
        VBox panelFormulario = crearPanelFormulario();
        panelFormulario.setPrefWidth(300);
        
        // Panel central - Lista
        VBox panelLista = crearPanelLista();
        
        // Panel inferior - Estado
        lblEstado = new Label("Sistema iniciado correctamente");
        lblEstado.setStyle("-fx-text-fill: green;");
        HBox panelEstado = new HBox(lblEstado);
        panelEstado.setPadding(new Insets(10, 0, 0, 0));
        
        // Ensamblar layout
        root.setTop(panelTitulo);
        root.setLeft(panelFormulario);
        root.setCenter(panelLista);
        root.setBottom(panelEstado);
        
        // Crear escena
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Cargar datos iniciales
        actualizarLista();
    }
    
    private VBox crearPanelFormulario() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(0, 20, 0, 0));
        
        Label lblTitulo = new Label("Gestión de Personas");
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Campos del formulario
        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(10);
        
        txtId = new TextField();
        txtNombres = new TextField();
        txtApellidos = new TextField();
        txtEmail = new TextField();
        
        formulario.add(new Label("ID:"), 0, 0);
        formulario.add(txtId, 1, 0);
        formulario.add(new Label("Nombres:"), 0, 1);
        formulario.add(txtNombres, 1, 1);
        formulario.add(new Label("Apellidos:"), 0, 2);
        formulario.add(txtApellidos, 1, 2);
        formulario.add(new Label("Email:"), 0, 3);
        formulario.add(txtEmail, 1, 3);
        
        // Botones
        HBox panelBotones = new HBox(10);
        Button btnAgregar = new Button("Agregar");
        Button btnActualizar = new Button("Actualizar");
        Button btnEliminar = new Button("Eliminar");
        Button btnLimpiar = new Button("Limpiar");
        
        btnAgregar.setOnAction(e -> agregarPersona());
        btnActualizar.setOnAction(e -> actualizarPersona());
        btnEliminar.setOnAction(e -> eliminarPersona());
        btnLimpiar.setOnAction(e -> limpiarFormulario());
        
        panelBotones.getChildren().addAll(btnAgregar, btnActualizar, btnEliminar, btnLimpiar);
        
        panel.getChildren().addAll(lblTitulo, formulario, panelBotones);
        return panel;
    }
    
    private VBox crearPanelLista() {
        VBox panel = new VBox(10);
        
        Label lblTitulo = new Label("Lista de Personas");
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        listaPersonas = new ListView<>();
        listaPersonas.setPrefHeight(400);
        
        // Al seleccionar una persona, cargar sus datos en el formulario
        listaPersonas.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) { // Doble clic
                cargarPersonaSeleccionada();
            }
        });
        
        Button btnRefrescar = new Button("Refrescar Lista");
        btnRefrescar.setOnAction(e -> {
            servicio.cargarDatos();
            actualizarLista();
            mostrarEstado("Lista actualizada desde la base de datos", false);
        });
        
        panel.getChildren().addAll(lblTitulo, listaPersonas, btnRefrescar);
        return panel;
    }
    
    private void agregarPersona() {
        try {
            if (validarCampos()) {
                Persona persona = new Persona(
                    Double.parseDouble(txtId.getText()),
                    txtNombres.getText(),
                    txtApellidos.getText(),
                    txtEmail.getText()
                );
                
                servicio.inscribir(persona);
                actualizarLista();
                limpiarFormulario();
                mostrarEstado("Persona agregada correctamente", false);
            }
        } catch (Exception e) {
            mostrarEstado("Error al agregar persona: " + e.getMessage(), true);
        }
    }
    
    private void actualizarPersona() {
        try {
            if (validarCampos()) {
                Persona persona = new Persona(
                    Double.parseDouble(txtId.getText()),
                    txtNombres.getText(),
                    txtApellidos.getText(),
                    txtEmail.getText()
                );
                
                servicio.actualizar(persona);
                actualizarLista();
                limpiarFormulario();
                mostrarEstado("Persona actualizada correctamente", false);
            }
        } catch (Exception e) {
            mostrarEstado("Error al actualizar persona: " + e.getMessage(), true);
        }
    }
    
    private void eliminarPersona() {
        try {
            if (!txtId.getText().isEmpty()) {
                Double id = Double.parseDouble(txtId.getText());
                
                // Crear una persona temporal solo con el ID para eliminar
                Persona personaAEliminar = new Persona(id, "", "", "");
                servicio.eliminar(personaAEliminar);
                
                actualizarLista();
                limpiarFormulario();
                mostrarEstado("Persona eliminada correctamente", false);
            } else {
                mostrarEstado("Ingrese el ID de la persona a eliminar", true);
            }
        } catch (Exception e) {
            mostrarEstado("Error al eliminar persona: " + e.getMessage(), true);
        }
    }
    
    private void cargarPersonaSeleccionada() {
        int index = listaPersonas.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            String personaStr = servicio.imprimirPosicion(index);
            // Parsear la cadena para extraer los datos
            // Formato: "Persona{id=1.0, nombres='Juan', apellidos='Perez', email='juan@email.com'}"
            try {
                String[] parts = personaStr.split(", ");
                String id = parts[0].substring(parts[0].indexOf("=") + 1);
                String nombres = parts[1].substring(parts[1].indexOf("'") + 1, parts[1].lastIndexOf("'"));
                String apellidos = parts[2].substring(parts[2].indexOf("'") + 1, parts[2].lastIndexOf("'"));
                String email = parts[3].substring(parts[3].indexOf("'") + 1, parts[3].lastIndexOf("'"));
                
                txtId.setText(id);
                txtNombres.setText(nombres);
                txtApellidos.setText(apellidos);
                txtEmail.setText(email);
            } catch (Exception e) {
                mostrarEstado("Error al cargar datos de la persona seleccionada", true);
            }
        }
    }
    
    private boolean validarCampos() {
        if (txtId.getText().isEmpty() || txtNombres.getText().isEmpty() || 
            txtApellidos.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            mostrarEstado("Todos los campos son obligatorios", true);
            return false;
        }
        
        try {
            Double.parseDouble(txtId.getText());
        } catch (NumberFormatException e) {
            mostrarEstado("El ID debe ser un número válido", true);
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        txtId.clear();
        txtNombres.clear();
        txtApellidos.clear();
        txtEmail.clear();
    }
    
    private void actualizarLista() {
        listaPersonas.getItems().clear();
        listaPersonas.getItems().addAll(servicio.imprimirListado());
    }
    
    private void mostrarEstado(String mensaje, boolean esError) {
        lblEstado.setText(mensaje);
        if (esError) {
            lblEstado.setStyle("-fx-text-fill: red;");
        } else {
            lblEstado.setStyle("-fx-text-fill: green;");
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
