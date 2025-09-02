package com.brayan.miapp.View.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class BarraEstadoCursosProfesores extends HBox {
    
    private Label lblEstado;
    private Label lblTotal;
    
    public BarraEstadoCursosProfesores() {
        initializeComponents();
        setupLayout();
    }
    
    private void initializeComponents() {
        lblEstado = new Label("Listo");
        lblEstado.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        
        lblTotal = new Label("Total: 0 asignaciones");
        lblTotal.setStyle("-fx-text-fill: #7f8c8d;");
    }
    
    private void setupLayout() {
        this.setPadding(new Insets(10, 20, 10, 20));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 1 0 0 0;");
        
        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);
        
        this.getChildren().addAll(lblEstado, espaciador, lblTotal);
    }
    
    public void actualizarEstado(String mensaje) {
        lblEstado.setText(mensaje);
        if (mensaje.toLowerCase().contains("error")) {
            lblEstado.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        } else {
            lblEstado.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        }
    }
    
    public void actualizarTotal(int total) {
        lblTotal.setText("Total: " + total + " asignaciones");
    }
}
