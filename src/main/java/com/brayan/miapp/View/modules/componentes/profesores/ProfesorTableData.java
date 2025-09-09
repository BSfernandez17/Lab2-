package com.brayan.miapp.View.modules.componentes.profesores;

import javafx.beans.property.*;

public class ProfesorTableData {
    private final DoubleProperty id;
    private final StringProperty nombres;
    private final StringProperty apellidos;
    private final StringProperty email;
    private final StringProperty tipoContrato;
    
    public ProfesorTableData(Double id, String nombres, String apellidos, String email, String tipoContrato) {
        this.id = new SimpleDoubleProperty(id);
        this.nombres = new SimpleStringProperty(nombres);
        this.apellidos = new SimpleStringProperty(apellidos);
        this.email = new SimpleStringProperty(email);
        this.tipoContrato = new SimpleStringProperty(tipoContrato);
    }
    
    // Getters para propiedades (usados por TableView)
    public DoubleProperty idProperty() { return id; }
    public StringProperty nombresProperty() { return nombres; }
    public StringProperty apellidosProperty() { return apellidos; }
    public StringProperty emailProperty() { return email; }
    public StringProperty tipoContratoProperty() { return tipoContrato; }
    
    // Getters para valores
    public Double getId() { return id.get(); }
    public String getNombres() { return nombres.get(); }
    public String getApellidos() { return apellidos.get(); }
    public String getEmail() { return email.get(); }
    public String getTipoContrato() { return tipoContrato.get(); }
    
    // Setters
    public void setId(Double value) { id.set(value); }
    public void setNombres(String value) { nombres.set(value); }
    public void setApellidos(String value) { apellidos.set(value); }
    public void setEmail(String value) { email.set(value); }
    public void setTipoContrato(String value) { tipoContrato.set(value); }
}
