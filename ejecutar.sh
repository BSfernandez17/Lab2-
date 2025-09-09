#!/bin/bash

# Script para ejecutar las aplicaciones del sistema universitario
# Uso: ./ejecutar.sh [opcion]

echo "🎓 Sistema de Gestión Universitaria"
echo "====================================="

case "$1" in
    "menu"|"")
        echo "🏠 Ejecutando Menú Principal (MenuPrincipal)..."
        mvn javafx:run@menu-principal
        ;;
    "main"|"main-app")
        echo "🏠 Ejecutando Aplicación Principal (MainApp)..."
        mvn javafx:run@main-app
        ;;
    "facultades")
        echo "📚 Ejecutando Gestión de Facultades..."
        mvn javafx:run@facultades
        ;;
    "programas")
        echo "🎓 Ejecutando Gestión de Programas..."
        mvn javafx:run@programas
        ;;
    "datos")
        echo "📊 Poblando base de datos con datos de ejemplo..."
        mvn exec:java -Dexec.mainClass="com.brayan.miapp.DAO.DataSeeder"
        ;;
    "limpiar")
        echo "🧹 Limpiando base de datos..."
        mvn exec:java -Dexec.mainClass="com.brayan.miapp.DAO.DataSeeder" -Dexec.args="clean"
        ;;
    "compilar")
        echo "🔨 Compilando proyecto..."
        mvn clean compile
        ;;
    "ayuda"|"help")
        echo ""
        echo "Opciones disponibles:"
        echo "  menu        - Ejecutar menú principal nuevo (MenuPrincipal) [por defecto]"
        echo "  main-app    - Ejecutar aplicación principal (MainApp)"
        echo "  facultades  - Ejecutar gestión de facultades"
        echo "  programas   - Ejecutar gestión de programas"
        echo "  datos       - Poblar base de datos con datos de ejemplo"
        echo "  limpiar     - Limpiar datos de la base de datos"
        echo "  compilar    - Compilar el proyecto"
        echo "  ayuda       - Mostrar esta ayuda"
        echo ""
        echo "Ejemplos:"
        echo "  ./ejecutar.sh                # Ejecuta el menú principal nuevo"
        echo "  ./ejecutar.sh main-app       # Ejecuta la app principal (MainApp)"
        echo "  ./ejecutar.sh facultades     # Ejecuta gestión de facultades"
        echo "  ./ejecutar.sh datos          # Pobla la base de datos"
        ;;
    *)
        echo "❌ Opción no válida: $1"
        echo "💡 Usa './ejecutar.sh ayuda' para ver las opciones disponibles"
        exit 1
        ;;
esac
