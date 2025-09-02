#!/bin/bash

# Script para ejecutar la aplicación JavaFX
echo "=== Iniciando Sistema de Gestión Universitaria (JavaFX) ==="

# Navegar al directorio del proyecto
cd /home/b17/tecno-avanzadas/lab2

# Compilar el proyecto
echo "Compilando el proyecto..."
mvn clean compile

# Verificar si necesita datos de ejemplo
echo "¿Desea cargar datos de ejemplo? (y/n):"
read -r respuesta
if [[ $respuesta == "y" || $respuesta == "Y" ]]; then
    echo "Cargando datos de ejemplo..."
    mvn exec:java -Dexec.mainClass="com.brayan.miapp.DataSeeder"
fi

# Ejecutar la aplicación JavaFX
echo "Iniciando la interfaz gráfica..."
mvn javafx:run
