package com.brayan.miapp.View.modules.componentes.cursos;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import com.brayan.miapp.View.controllers.StatusCallback;

public class BarraEstadoCursos extends HBox implements StatusCallback {
    
    private Label labelEstado;
    
    public BarraEstadoCursos() {
        super(10);
        labelEstado = new Label("Listo");
        labelEstado.setStyle("-fx-padding: 5px;");
        getChildren().add(labelEstado);
        setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px 0 0 0; -fx-padding: 5px;");
    }
    
    @Override
    public void mostrarEstado(String mensaje, boolean esError) {
        labelEstado.setText(mensaje);
        if (esError) {
            labelEstado.setTextFill(Color.RED);
        } else {
            labelEstado.setTextFill(Color.GREEN);
        }
    }
    
    public void limpiarEstado() {
        labelEstado.setText("Listo");
        labelEstado.setTextFill(Color.BLACK);
    }
}
