package com.brayan.miapp.View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import com.brayan.miapp.View.components.FormularioEstudiante;
import com.brayan.miapp.View.components.ListaEstudiantes;
import com.brayan.miapp.View.components.BarraEstadoEstudiantes;
import com.brayan.miapp.View.components.EstudianteTableData;
import com.brayan.miapp.View.controllers.EstudianteController;
import com.brayan.miapp.View.controllers.StatusCallback;
import com.brayan.miapp.Model.Estudiante;

import java.util.List;

public class EstudiantesAppModular extends Application {
    private FormularioEstudiante formulario;
    private ListaEstudiantes lista;
    private BarraEstadoEstudiantes barraEstado;
    private EstudianteController controller;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        inicializarComponentes();
        configurarEventos();
        configurarLayout(primaryStage);
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        formulario = new FormularioEstudiante();
        lista = new ListaEstudiantes();
        barraEstado = new BarraEstadoEstudiantes();
        controller = new EstudianteController();
    }
    
    private void configurarEventos() {
        // Configurar callback para el controller
        controller.setStatusCallback(new StatusCallback() {
            @Override
            public void mostrarEstado(String mensaje, boolean esError) {
                if (esError) {
                    barraEstado.mostrarError(mensaje);
                } else {
                    barraEstado.mostrarExito(mensaje);
                }
            }
        });
        
        // Eventos de botones
        formulario.getBtnAgregar().setOnAction(e -> {
            Estudiante estudiante = formulario.obtenerEstudiante();
            if (estudiante != null) {
                controller.agregarEstudiante(estudiante);
                cargarDatos(); // Recargar la lista
                formulario.limpiarFormulario();
            } else {
                barraEstado.mostrarError("Datos inválidos en el formulario");
            }
        });
        
        formulario.getBtnActualizar().setOnAction(e -> {
            Estudiante estudiante = formulario.obtenerEstudiante();
            if (estudiante != null && estudiante.getId() > 0) {
                controller.actualizarEstudiante(estudiante);
                cargarDatos(); // Recargar la lista
                formulario.limpiarFormulario();
            } else {
                barraEstado.mostrarError("Seleccione un estudiante válido para actualizar");
            }
        });
        
        formulario.getBtnEliminar().setOnAction(e -> {
            EstudianteTableData seleccionado = lista.getEstudianteSeleccionado();
            if (seleccionado != null) {
                double id = Double.parseDouble(seleccionado.getId());
                controller.eliminarEstudiante(id);
                cargarDatos(); // Recargar la lista
                formulario.limpiarFormulario();
            } else {
                barraEstado.mostrarError("Seleccione un estudiante para eliminar");
            }
        });
        
        formulario.getBtnLimpiar().setOnAction(e -> {
            formulario.limpiarFormulario();
            barraEstado.limpiar();
        });
        
        // Evento de selección en la tabla
        lista.getTabla().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Crear un estudiante temporal para cargar en el formulario
                try {
                    double id = Double.parseDouble(newSelection.getId());
                    Estudiante estudiante = controller.buscarEstudiante(id);
                    if (estudiante != null) {
                        formulario.cargarEstudiante(estudiante);
                    }
                } catch (NumberFormatException ex) {
                    barraEstado.mostrarError("Error al cargar estudiante seleccionado");
                }
            }
        });
    }
    
    private void configurarLayout(Stage primaryStage) {
        BorderPane root = new BorderPane();
        
        // Panel izquierdo: formulario
        formulario.getFormulario().setPadding(new Insets(10));
        root.setLeft(formulario.getFormulario());
        
        // Panel central: lista
        lista.getContenedor().setPadding(new Insets(10));
        root.setCenter(lista.getContenedor());
        
        // Panel inferior: barra de estado
        root.setBottom(barraEstado.getBarraEstado());
        
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Gestión de Estudiantes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void cargarDatos() {
        List<Estudiante> estudiantes = controller.listarEstudiantes();
        lista.limpiarLista();
        for (Estudiante estudiante : estudiantes) {
            lista.agregarEstudiante(estudiante);
        }
        barraEstado.mostrarExito("Datos cargados correctamente");
    }
}
