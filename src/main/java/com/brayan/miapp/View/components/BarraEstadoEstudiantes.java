package com.brayan.miapp.View.components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

public class BarraEstadoEstudiantes {
    private HBox barraEstado;
    private Label estadoLabel;
    
    public BarraEstadoEstudiantes() {
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        barraEstado = new HBox();
        barraEstado.setPadding(new Insets(5));
        barraEstado.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1 0 0 0;");
        
        estadoLabel = new Label("Listo");
        estadoLabel.setStyle("-fx-font-size: 12px;");
        
        barraEstado.getChildren().add(estadoLabel);
    }
    
    public HBox getBarraEstado() { return barraEstado; }
    
    public void mostrarMensaje(String mensaje) {
        estadoLabel.setText(mensaje);
    }
    
    public void mostrarError(String error) {
        estadoLabel.setText("Error: " + error);
        estadoLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: red;");
    }
    
    public void mostrarExito(String mensaje) {
        estadoLabel.setText(mensaje);
        estadoLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: green;");
    }
    
    public void limpiar() {
        estadoLabel.setText("Listo");
        estadoLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: black;");
    }
}
