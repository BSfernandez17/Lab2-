#!/bin/bash

# Script para ejecutar la aplicación JavaFX
echo "=== Iniciando Sistema de Gestión Universitaria (JavaFX) ==="

# Navegar al directorio del proyecto
cd /home/b17/tecno-avanzadas/lab2

# Compilar el proyecto
echo "Compilando el proyecto..."
mvn clean compile

# Ejecutar la aplicación JavaFX
echo "Iniciando la interfaz gráfica..."
mvn javafx:run
