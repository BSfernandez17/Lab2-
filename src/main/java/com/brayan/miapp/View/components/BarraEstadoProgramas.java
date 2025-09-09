package com.brayan.miapp.View.components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

public class BarraEstadoProgramas {
    private HBox contenedor;
    private Label lblEstado;
    private Label lblTotal;
    
    public BarraEstadoProgramas() {
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        contenedor = new HBox(20);
        contenedor.setPadding(new Insets(5, 10, 5, 10));
        contenedor.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1;");
        
        lblEstado = new Label("Listo");
        lblEstado.setStyle("-fx-font-weight: bold;");
        
        lblTotal = new Label("Total de programas: 0");
        
        contenedor.getChildren().addAll(lblEstado, lblTotal);
    }
    
    public void actualizarEstado(String mensaje) {
        lblEstado.setText(mensaje);
    }
    
    public void actualizarTotal(int total) {
        lblTotal.setText("Total de programas: " + total);
    }
    
    public void mostrarOperacionExitosa(String operacion) {
        lblEstado.setText("✅ " + operacion + " realizada correctamente");
        lblEstado.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
    }
    
    public void mostrarError(String error) {
        lblEstado.setText("❌ Error: " + error);
        lblEstado.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
    }
    
    public void limpiarEstado() {
        lblEstado.setText("Listo");
        lblEstado.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");
    }
    
    public HBox getContenedor() {
        return contenedor;
    }
}
