package com.brayan.miapp.View.modules;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.brayan.miapp.View.components.FormularioPrograma;
import com.brayan.miapp.View.components.ListaProgramas;
import com.brayan.miapp.View.components.BarraEstadoProgramas;
import com.brayan.miapp.View.controllers.ProgramaController;
import com.brayan.miapp.View.controllers.FacultadController;
import com.brayan.miapp.Model.Programa;
import com.brayan.miapp.Model.Facultad;
import com.brayan.miapp.DAO.DatabaseSetup;

import java.util.List;

public class ProgramasAppModular extends Application {
    
    private FormularioPrograma formularioPrograma;
    private ListaProgramas listaProgramas;
    private BarraEstadoProgramas barraEstado;
    private ProgramaController programaController;
    private FacultadController facultadController;
    
    // Constructor para crear ventana directamente
    public ProgramasAppModular() {
        // Constructor vacío para uso como ventana secundaria
    }
    
    // Método para abrir como ventana nueva (sin usar Application.launch)
    public void abrirVentana() {
        try {
            Stage stage = new Stage();
            inicializarVentana(stage);
        } catch (Exception e) {
            System.err.println("Error al abrir la ventana de programas: " + e.getMessage());
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
        DatabaseSetup.init();
        
        primaryStage.setTitle("Gestión de Programas Académicos - Modular");
        
        // Inicializar componentes
        inicializarComponentes();
        configurarEventos();
        cargarDatos();
        
        // Layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        root.setLeft(formularioPrograma.getContenedor());
        root.setCenter(listaProgramas.getContenedor());
        root.setBottom(barraEstado.getContenedor());
        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        
        // Solo cerrar esta ventana, no toda la aplicación
        primaryStage.setOnCloseRequest(e -> primaryStage.close());
        
        primaryStage.show();
    }
    
    private void inicializarComponentes() {
        formularioPrograma = new FormularioPrograma();
        listaProgramas = new ListaProgramas();
        barraEstado = new BarraEstadoProgramas();
        programaController = new ProgramaController();
        facultadController = new FacultadController();
    }
    
    private void configurarEventos() {
        // Evento guardar
        formularioPrograma.getBtnGuardar().setOnAction(e -> {
            Programa programa = formularioPrograma.obtenerProgramaDelFormulario();
            if (programa != null) {
                try {
                    if (formularioPrograma.esActualizacion()) {
                        programaController.actualizarPrograma(programa);
                        listaProgramas.actualizarPrograma(programa);
                        barraEstado.mostrarOperacionExitosa("Actualización");
                    } else {
                        programaController.agregarPrograma(programa);
                        listaProgramas.agregarPrograma(programa);
                        barraEstado.mostrarOperacionExitosa("Creación");
                    }
                    formularioPrograma.limpiarFormulario();
                    actualizarContador();
                } catch (Exception ex) {
                    barraEstado.mostrarError("Error al guardar: " + ex.getMessage());
                }
            }
        });
        
        // Evento eliminar
        formularioPrograma.getBtnEliminar().setOnAction(e -> {
            if (formularioPrograma.getProgramaActual() != null) {
                try {
                    programaController.eliminarPrograma(formularioPrograma.getProgramaActual().getId());
                    listaProgramas.eliminarPrograma(formularioPrograma.getProgramaActual().getId().toString());
                    formularioPrograma.limpiarFormulario();
                    barraEstado.mostrarOperacionExitosa("Eliminación");
                    actualizarContador();
                } catch (Exception ex) {
                    barraEstado.mostrarError("Error al eliminar: " + ex.getMessage());
                }
            }
        });
        
        // Evento selección en tabla
        listaProgramas.getTabla().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && newSelection.getId() != null && !newSelection.getId().trim().isEmpty()) {
                try {
                    Double id = Double.parseDouble(newSelection.getId());
                    Programa programa = programaController.obtenerPorId(id);
                    if (programa != null) {
                        formularioPrograma.cargarPrograma(programa);
                        barraEstado.actualizarEstado("Programa seleccionado: " + programa.getNombre());
                    } else {
                        barraEstado.mostrarError("No se pudo encontrar el programa seleccionado");
                    }
                } catch (NumberFormatException ex) {
                    barraEstado.mostrarError("ID de programa inválido: " + newSelection.getId());
                } catch (Exception ex) {
                    barraEstado.mostrarError("Error al cargar programa: " + ex.getMessage());
                }
            }
        });
        
        // Evento limpiar
        formularioPrograma.getBtnLimpiar().setOnAction(e -> {
            formularioPrograma.limpiarFormulario();
            barraEstado.limpiarEstado();
        });
    }
    
    private void cargarDatos() {
        try {
            // Cargar facultades para el combo
            List<Facultad> facultades = facultadController.listarFacultades();
            formularioPrograma.cargarFacultades(facultades);
            
            // Cargar programas en la tabla
            List<Programa> programas = programaController.listarProgramas();
            listaProgramas.limpiarLista();
            for (Programa programa : programas) {
                listaProgramas.agregarPrograma(programa);
            }
            
            actualizarContador();
            barraEstado.actualizarEstado("Datos cargados correctamente");
            
        } catch (Exception e) {
            barraEstado.mostrarError("Error al cargar datos: " + e.getMessage());
        }
    }
    
    private void actualizarContador() {
        barraEstado.actualizarTotal(listaProgramas.getDatos().size());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
