package com.example.cajeroautomaticomejorado;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CajeroAutomaticoMejorado extends Application {

    private double saldo = 1000.0;  // Saldo inicial
    private String nip = "1234";    // NIP inicial

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cajero Automático");

        // Pantalla de ingreso de tarjeta y NIP
        BorderPane loginPane = new BorderPane();
        loginPane.setPadding(new javafx.geometry.Insets(20));
        VBox loginBox = new VBox(10);
        loginBox.setAlignment(Pos.CENTER);

        Label cardLabel = new Label("Ingrese su tarjeta:");
        TextField cardField = new TextField();
        Label nipLabel = new Label("Ingrese su NIP:");
        PasswordField nipField = new PasswordField();
        Button loginButton = new Button("Ingresar");

        loginBox.getChildren().addAll(cardLabel, cardField, nipLabel, nipField, loginButton);
        loginPane.setCenter(loginBox);

        Scene loginScene = new Scene(loginPane, 400, 300);
        loginScene.getStylesheets().add("style.css");

        // Pantalla de opciones
        BorderPane optionsPane = new BorderPane();
        optionsPane.setPadding(new javafx.geometry.Insets(20));
        VBox optionsBox = new VBox(10);
        optionsBox.setAlignment(Pos.CENTER);

        Button saldoButton = new Button("Consultar saldo");
        Button retiroButton = new Button("Retirar dinero");
        Button pagoServiciosButton = new Button("Pagar servicios");
        Button cambiarNipButton = new Button("Cambiar NIP");
        Button prestamoButton = new Button("Solicitar préstamo");
        Button logoutButton = new Button("Salir");

        optionsBox.getChildren().addAll(saldoButton, retiroButton, pagoServiciosButton, cambiarNipButton, prestamoButton, logoutButton);
        optionsPane.setCenter(optionsBox);

        Scene optionsScene = new Scene(optionsPane, 400, 300);
        optionsScene.getStylesheets().add("style.css");

        // Manejo de eventos
        loginButton.setOnAction(e -> {
            String enteredNip = nipField.getText();
            if (enteredNip.equals(nip)) {
                primaryStage.setScene(optionsScene);
            } else {
                showAlert("Error", "NIP incorrecto");
            }
        });

        saldoButton.setOnAction(e -> showBalance(primaryStage));
        retiroButton.setOnAction(e -> retirarDinero(primaryStage));
        pagoServiciosButton.setOnAction(e -> pagarServicios(primaryStage));
        cambiarNipButton.setOnAction(e -> cambiarNip(primaryStage));
        prestamoButton.setOnAction(e -> solicitarPrestamo(primaryStage));
        logoutButton.setOnAction(e -> primaryStage.setScene(loginScene));

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showBalance(Stage primaryStage) {
        showAlert("Saldo", "Su saldo actual es: $" + saldo);
        printTicket("Saldo", "Su saldo actual es: $" + saldo);
    }

    private void retirarDinero(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Retirar dinero");
        dialog.setHeaderText("Ingrese el monto a retirar");
        dialog.setContentText("Monto:");

        dialog.showAndWait().ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= saldo) {
                    saldo -= amount;
                    showAlert("Retiro exitoso", "Ha retirado: $" + amount);
                    printTicket("Retiro", "Ha retirado: $" + amount);
                } else {
                    showAlert("Error", "Saldo insuficiente");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Monto inválido");
            }
        });
    }

    private void pagarServicios(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Pagar servicios");
        dialog.setHeaderText("Ingrese el monto a pagar");
        dialog.setContentText("Monto:");

        dialog.showAndWait().ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= saldo) {
                    saldo -= amount;
                    showAlert("Pago exitoso", "Ha pagado: $" + amount + " por servicios");
                    printTicket("Pago de servicios", "Ha pagado: $" + amount + " por servicios");
                } else {
                    showAlert("Error", "Saldo insuficiente");
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Monto inválido");
            }
        });
    }

    private void cambiarNip(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cambiar NIP");
        dialog.setHeaderText("Ingrese su nuevo NIP");
        dialog.setContentText("Nuevo NIP:");

        dialog.showAndWait().ifPresent(newNip -> {
            if (newNip.length() == 4 && newNip.matches("\\d+")) {
                nip = newNip;
                showAlert("Cambio de NIP exitoso", "Su NIP ha sido cambiado exitosamente");
                printTicket("Cambio de NIP", "Su NIP ha sido cambiado exitosamente");
            } else {
                showAlert("Error", "NIP debe ser numérico de 4 dígitos");
            }
        });
    }

    private void solicitarPrestamo(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Solicitar préstamo");
        dialog.setHeaderText("Ingrese el monto del préstamo");
        dialog.setContentText("Monto:");

        dialog.showAndWait().ifPresent(amountStr -> {
            try {
                double amount = Double.parseDouble(amountStr);
                saldo += amount;
                showAlert("Préstamo aprobado", "Se le ha otorgado un préstamo de: $" + amount);
                printTicket("Préstamo", "Se le ha otorgado un préstamo de: $" + amount);
            } catch (NumberFormatException e) {
                showAlert("Error", "Monto inválido");
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void printTicket(String operation, String details) {
        // Simulación de impresión de ticket
        System.out.println("------ Ticket ------");
        System.out.println("Operación: " + operation);
        System.out.println(details);
        System.out.println("--------------------");
    }
}
