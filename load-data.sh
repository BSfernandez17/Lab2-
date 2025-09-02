#!/bin/bash

# Script para cargar datos de ejemplo
echo "=== Cargando datos de ejemplo ==="

# Navegar al directorio del proyecto
cd /home/b17/tecno-avanzadas/lab2

# Compilar y ejecutar el DataSeeder
echo "Ejecutando DataSeeder..."
mvn exec:java -Dexec.mainClass="com.brayan.miapp.DataSeeder"

echo "=== Datos cargados exitosamente ==="
