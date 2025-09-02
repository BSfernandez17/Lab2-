package com.brayan.miapp.View.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TituloApp extends HBox {
    
    public TituloApp() {
        initializeComponents();
        setupLayout();
        setupStyles();
    }
    
    private void initializeComponents() {
        Label titulo = new Label("Sistema de Gestión Universitaria");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        this.getChildren().add(titulo);
    }
    
    private void setupLayout() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(0, 0, 20, 0));
    }
    
    private void setupStyles() {
        this.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 0 0 2 0;");
    }
}
