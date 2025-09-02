package com.brayan.miapp.View.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BarraEstado extends HBox {
    
    private Label lblEstado;
    
    public BarraEstado() {
        initializeComponents();
        setupLayout();
        setupStyles();
    }
    
    private void initializeComponents() {
        lblEstado = new Label("Sistema iniciado correctamente");
        lblEstado.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
    }
    
    private void setupLayout() {
        this.setPadding(new Insets(10, 0, 0, 0));
        this.getChildren().add(lblEstado);
    }
    
    private void setupStyles() {
        this.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 2 0 0 0;");
    }
    
    public void mostrarEstado(String mensaje, boolean esError) {
        lblEstado.setText(mensaje);
        if (esError) {
            lblEstado.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        } else {
            lblEstado.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        }
    }
}
