package com.example.weathergui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    private final ObservableList<WeatherData> weatherDataList = FXCollections.observableArrayList(); // to update the gui table instantly
    private ExecutorService executorService;

    @Override
    public void start(Stage primaryStage) {
        TableView<WeatherData> tableView = new TableView<>(); // creating a new table
        tableView.setItems(weatherDataList); // we bind the observable list with the table in the gui

        TableColumn<WeatherData, String> cityColumn = new TableColumn<>("City"); // the Coulmn name
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city")); // the name of the value in the WeatherData class

        TableColumn<WeatherData, String> tempColumn = new TableColumn<>("Temperature (°C)");
        tempColumn.setCellValueFactory(new PropertyValueFactory<>("temperature"));

        TableColumn<WeatherData, String> humidityColumn = new TableColumn<>("Humidity (%)");
        humidityColumn.setCellValueFactory(new PropertyValueFactory<>("humidity"));

        TableColumn<WeatherData, String> windColumn = new TableColumn<>("Wind Speed (m/s)");
        windColumn.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));

        TableColumn<WeatherData, String> pressureColumn = new TableColumn<>("Pressure (hPa)");
        pressureColumn.setCellValueFactory(new PropertyValueFactory<>("pressure"));

        TableColumn<WeatherData, String> cloudinessColumn = new TableColumn<>("Cloudiness (%)");
        cloudinessColumn.setCellValueFactory(new PropertyValueFactory<>("cloudiness"));

        tableView.getColumns().addAll(cityColumn, tempColumn, humidityColumn, windColumn, pressureColumn, cloudinessColumn); // we add the columns in the table in the gui

        String[] CITIES = {"Cairo", "London", "New York", "Tokyo", "Moscow", "Mexico", "Nigeria", "Syria", "Roma"};
        for (String city : CITIES) {
            weatherDataList.add(new WeatherData(city));
        }

        Label threadLabel = new Label("Number of Threads:");
        TextField threadInput = new TextField();
        threadInput.setPromptText("e.g., 4");

        Button startButton = new Button("Start Display");
        startButton.setOnAction(e -> {
            int numThreads = parseThreadInput(threadInput.getText());
            startUpdatingWeather(weatherDataList, tableView, numThreads);
        });

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> stopUpdatingWeather());

        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        stopButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox controls = new HBox(10, threadLabel, threadInput, startButton, stopButton);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        VBox root = new VBox(15, tableView, controls);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f4f4f4; -fx-font-family: 'Arial';");

        Scene scene = new Scene(root, 800, 500);

        primaryStage.setTitle("Weather Aggregator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startUpdatingWeather(ObservableList<WeatherData> list, TableView<WeatherData> table, int numThreads) {
        executorService = Executors.newFixedThreadPool(numThreads);
        API api = new API();

        for (WeatherData item : list) {
            executorService.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    api.update(item);
                    Platform.runLater(table::refresh); // Ensure table updates on the JavaFX thread
                    try {
                        Thread.sleep(5000); // Wait for 5 seconds before fetching again
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Reset the thread's interrupt status
                        break; // Exit loop if interrupted
                    }
                }
            });
        }
    }

    private void stopUpdatingWeather() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
            System.out.println("All threads stopped.");
        }
    }

    private int parseThreadInput(String input) {
        try {
            return Math.max(1, Integer.parseInt(input));
        } catch (NumberFormatException e) {
            System.out.println("Invalid thread input. Defaulting to 1.");
            return 1;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
