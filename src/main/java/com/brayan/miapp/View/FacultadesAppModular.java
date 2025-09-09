package com.brayan.miapp.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import com.brayan.miapp.DAO.DatabaseSetup;
import com.brayan.miapp.DAO.FacultadDAO;
import com.brayan.miapp.Model.Facultad;
import com.brayan.miapp.View.components.FormularioFacultad;
import com.brayan.miapp.View.components.ListaFacultades;
import com.brayan.miapp.View.components.BarraEstadoFacultades;
import com.brayan.miapp.View.components.FacultadTableData;

public class FacultadesAppModular extends Application {
    
    private FormularioFacultad formularioFacultad;
    private ListaFacultades listaFacultades;
    private BarraEstadoFacultades barraEstado;
    private FacultadDAO facultadDAO;
    
    private Button btnNuevo;
    private Button btnEditar;
    private Button btnEliminar;
    private Button btnActualizar;
    
    // Constructor para crear ventana directamente
    public FacultadesAppModular() {
        // Constructor vacío para uso como ventana secundaria
    }
    
    // Método para abrir como ventana nueva (sin usar Application.launch)
    public void abrirVentana() {
        try {
            Stage stage = new Stage();
            inicializarVentana(stage);
        } catch (Exception e) {
            mostrarError("Error al abrir la ventana de facultades: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        inicializarVentana(primaryStage);
    }
    
    // Método común para inicializar la ventana
    private void inicializarVentana(Stage primaryStage) {
        // Inicializar base de datos
        try {
            DatabaseSetup.init();
        } catch (Exception e) {
            mostrarError("Error al inicializar la base de datos: " + e.getMessage());
            return;
        }
        
        // Inicializar componentes
        facultadDAO = new FacultadDAO();
        formularioFacultad = new FormularioFacultad();
        listaFacultades = new ListaFacultades();
        barraEstado = new BarraEstadoFacultades();
        
        // Layout principal
        BorderPane root = new BorderPane();
        
        // Panel izquierdo - Formulario
        VBox panelIzquierdo = new VBox(10);
        panelIzquierdo.setPadding(new Insets(10));
        panelIzquierdo.setPrefWidth(400);
        
        // Botones de acción (crear primero los botones)
        HBox botonesAccion = crearBotonesAccion();
        panelIzquierdo.getChildren().addAll(botonesAccion, formularioFacultad.getContenedor());
        
        // Configurar eventos DESPUÉS de crear los botones
        configurarEventos();
        
        // Panel derecho - Lista
        VBox panelDerecho = new VBox(10);
        panelDerecho.setPadding(new Insets(10));
        panelDerecho.getChildren().add(listaFacultades.getContenedor());
        
        // Configurar layout
        root.setLeft(panelIzquierdo);
        root.setCenter(panelDerecho);
        root.setBottom(barraEstado.getContenedor());
        
        // Cargar datos iniciales
        actualizarLista();
        
        // Configurar escena
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setTitle("Sistema de Gestión de Facultades");
        primaryStage.setScene(scene);
        
        // Solo cerrar esta ventana, no toda la aplicación
        primaryStage.setOnCloseRequest(e -> primaryStage.close());
        
        primaryStage.show();
    }
    
    private HBox crearBotonesAccion() {
        HBox botones = new HBox(10);
        botones.setPadding(new Insets(0, 0, 10, 0));
        
        btnNuevo = new Button("Nueva Facultad");
        btnNuevo.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold;");
        btnNuevo.setPrefWidth(120);
        
        btnEditar = new Button("Editar");
        btnEditar.setStyle("-fx-background-color: #ffc107; -fx-text-fill: black; -fx-font-weight: bold;");
        btnEditar.setPrefWidth(80);
        btnEditar.setDisable(true);
        
        btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold;");
        btnEliminar.setPrefWidth(80);
        btnEliminar.setDisable(true);
        
        btnActualizar = new Button("Actualizar Lista");
        btnActualizar.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        btnActualizar.setPrefWidth(120);
        
        botones.getChildren().addAll(btnNuevo, btnEditar, btnEliminar, btnActualizar);
        return botones;
    }
    
    private void configurarEventos() {
        // Eventos de botones
        btnNuevo.setOnAction(e -> nuevaFacultad());
        btnEditar.setOnAction(e -> editarFacultad());
        btnEliminar.setOnAction(e -> eliminarFacultad());
        btnActualizar.setOnAction(e -> actualizarLista());
        
        // Evento de selección en la tabla
        listaFacultades.getTabla().getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null && !newValue.getId().trim().isEmpty()) {
                    btnEditar.setDisable(false);
                    btnEliminar.setDisable(false);
                    barraEstado.actualizarSeleccion(newValue.getNombre());
                } else {
                    btnEditar.setDisable(true);
                    btnEliminar.setDisable(true);
                    barraEstado.actualizarSeleccion(null);
                }
            }
        );
        
        // Callback cuando se guarda una facultad
        formularioFacultad.setOnFacultadGuardada(() -> {
            actualizarLista();
            barraEstado.mostrarExito("Facultad guardada correctamente");
        });
    }
    
    private void nuevaFacultad() {
        formularioFacultad.limpiarFormulario();
        barraEstado.actualizarEstado("Creando nueva facultad...");
    }
    
    private void editarFacultad() {
        FacultadTableData seleccionada = listaFacultades.getFacultadSeleccionada();
        if (seleccionada == null || seleccionada.getId().trim().isEmpty()) {
            barraEstado.mostrarError("Debe seleccionar una facultad para editar");
            return;
        }
        
        try {
            Double id = Double.parseDouble(seleccionada.getId());
            Facultad facultad = facultadDAO.buscarPorId(id);
            
            if (facultad != null) {
                formularioFacultad.cargarFacultad(facultad);
                barraEstado.actualizarEstado("Editando facultad: " + facultad.getNombre());
            } else {
                barraEstado.mostrarError("Facultad no encontrada");
            }
        } catch (Exception e) {
            barraEstado.mostrarError("Error al cargar facultad para edición: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void eliminarFacultad() {
        FacultadTableData seleccionada = listaFacultades.getFacultadSeleccionada();
        if (seleccionada == null || seleccionada.getId().trim().isEmpty()) {
            barraEstado.mostrarError("Debe seleccionar una facultad para eliminar");
            return;
        }
        
        // Confirmar eliminación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar esta facultad?");
        confirmacion.setContentText("Facultad: " + seleccionada.getNombre() + "\\nEsta acción no se puede deshacer.");
        
        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                try {
                    Double id = Double.parseDouble(seleccionada.getId());
                    facultadDAO.eliminar(id);
                    actualizarLista();
                    formularioFacultad.limpiarFormulario();
                    barraEstado.mostrarExito("Facultad eliminada correctamente");
                } catch (Exception e) {
                    barraEstado.mostrarError("Error al eliminar facultad: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
    
    private void actualizarLista() {
        try {
            listaFacultades.actualizarLista();
            int total = listaFacultades.getDatos().size();
            barraEstado.actualizarTotal(total);
            barraEstado.actualizarEstado("Lista actualizada");
        } catch (Exception e) {
            barraEstado.mostrarError("Error al actualizar lista: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
