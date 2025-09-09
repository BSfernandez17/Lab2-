package com.brayan.miapp.View.components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class BarraEstadoFacultades {
    private HBox contenedor;
    private Label lblEstado;
    private Label lblTotal;
    private Label lblSeleccionada;
    
    public BarraEstadoFacultades() {
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        contenedor = new HBox(20);
        contenedor.setPadding(new Insets(10));
        contenedor.setAlignment(Pos.CENTER_LEFT);
        contenedor.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; " +
                          "-fx-border-width: 1 0 0 0;");
        
        lblEstado = new Label("Sistema de Gestión de Facultades");
        lblEstado.setStyle("-fx-font-weight: bold; -fx-text-fill: #495057;");
        
        lblTotal = new Label("Total: 0 facultades");
        lblTotal.setStyle("-fx-text-fill: #6c757d;");
        
        lblSeleccionada = new Label("Ninguna facultad seleccionada");
        lblSeleccionada.setStyle("-fx-text-fill: #6c757d;");
        
        contenedor.getChildren().addAll(lblEstado, lblTotal, lblSeleccionada);
    }
    
    public void actualizarEstado(String mensaje) {
        lblEstado.setText(mensaje);
    }
    
    public void actualizarTotal(int total) {
        lblTotal.setText("Total: " + total + " facultad" + (total != 1 ? "es" : ""));
    }
    
    public void actualizarSeleccion(String facultadSeleccionada) {
        if (facultadSeleccionada == null || facultadSeleccionada.isEmpty()) {
            lblSeleccionada.setText("Ninguna facultad seleccionada");
        } else {
            lblSeleccionada.setText("Seleccionada: " + facultadSeleccionada);
        }
    }
    
    public void mostrarOperacion(String operacion) {
        lblEstado.setText(operacion);
        // Restaurar el mensaje por defecto después de 3 segundos
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                javafx.application.Platform.runLater(() -> 
                    lblEstado.setText("Sistema de Gestión de Facultades"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    public void mostrarError(String error) {
        lblEstado.setText("Error: " + error);
        lblEstado.setStyle("-fx-font-weight: bold; -fx-text-fill: #dc3545;");
        
        // Restaurar estilo normal después de 5 segundos
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                javafx.application.Platform.runLater(() -> {
                    lblEstado.setText("Sistema de Gestión de Facultades");
                    lblEstado.setStyle("-fx-font-weight: bold; -fx-text-fill: #495057;");
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    public void mostrarExito(String mensaje) {
        lblEstado.setText("✓ " + mensaje);
        lblEstado.setStyle("-fx-font-weight: bold; -fx-text-fill: #28a745;");
        
        // Restaurar estilo normal después de 3 segundos
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                javafx.application.Platform.runLater(() -> {
                    lblEstado.setText("Sistema de Gestión de Facultades");
                    lblEstado.setStyle("-fx-font-weight: bold; -fx-text-fill: #495057;");
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    public HBox getContenedor() {
        return contenedor;
    }
}
