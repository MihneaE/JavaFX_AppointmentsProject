package com.example.demo1;

import Model.Appointment;
import Model.ListImplementation;
import Service.AppointmentsService;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** SEARCH AND FILTER CONTROLLER, WHICH HAVE FILTER AND SEARCH BUTTONS **/

public class SearchAndFilterController {
    private AppointmentsService appointmentsService;
    private final TextField nameField;
    private final TextField ageField;
    private final TextField sportField;
    private final TextField durationField;
    private final Button searchButton;
    private final TextArea searchResult;
    private final Button filterByAgeButton;
    private final Button filterBySubscriptionButton;
    private final Button filterBySportButton;
    private final Button filterByDurationButton;
    private final Button filterByPriceButton;
    private final Button filterByNameAndAgeButton;
    private TableView<Appointment> filterTableView;

    public SearchAndFilterController(AppointmentsService appointmentsService, TableView<Appointment> filterTableView)
    {
        this.appointmentsService = appointmentsService;
        this.nameField = new TextField();
        this.ageField = new TextField();
        this.sportField = new TextField();
        this.durationField = new TextField();
        this.searchButton = new Button("SEARCH");
        this.searchResult = new TextArea();
        this.filterByAgeButton = new Button("FILTER BY AGE");
        this.filterBySubscriptionButton = new Button("FILTER BY SUBSCRIPTION");
        this.filterBySportButton = new Button("FILTER BY SPORT");
        this.filterByDurationButton = new Button("FILTER BY DURATION");
        this.filterByPriceButton = new Button("FILTER BY PRICE");
        this.filterByNameAndAgeButton = new Button("FILTER BY NAME AND AGE");
        this.filterTableView = filterTableView;
    }

    public void setupFilterTableView()
    {
        TableColumn<Appointment, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPerson().getName()));

        TableColumn<Appointment, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPerson().getAge()).asObject());

        TableColumn<Appointment, String> subscriptionColumn = new TableColumn<>("Subscription");
        subscriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPerson().getSubscription()));

        TableColumn<Appointment, Double> balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPerson().getBalance()).asObject());

        TableColumn<Appointment, LocalDateTime> startColumn = new TableColumn<>("Start Date");
        startColumn.setCellValueFactory(cellData-> new SimpleObjectProperty<>(cellData.getValue().getStart()));

        TableColumn<Appointment, LocalDateTime> endColumn = new TableColumn<>("End Date");
        endColumn.setCellValueFactory(cellData-> new SimpleObjectProperty<>(cellData.getValue().getEnd()));

        TableColumn<Appointment, Long> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getDuration()).asObject());

        TableColumn<Appointment, String> sportColumn = new TableColumn<>("Sport");
        sportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSport_type()));

        TableColumn<Appointment, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        this.filterTableView.getColumns().add(nameColumn);
        this.filterTableView.getColumns().add(ageColumn);
        this.filterTableView.getColumns().add(subscriptionColumn);
        this.filterTableView.getColumns().add(balanceColumn);
        this.filterTableView.getColumns().add(startColumn);
        this.filterTableView.getColumns().add(endColumn);
        this.filterTableView.getColumns().add(durationColumn);
        this.filterTableView.getColumns().add(sportColumn);
        this.filterTableView.getColumns().add(priceColumn);
    }

    public void setupSearchButton()
    {
        this.searchButton.setOnAction(event -> {
                String name = this.nameField.getText().trim();
                Integer age = Integer.valueOf(this.ageField.getText().trim());
                String sport = this.sportField.getText().trim();
                Long duration = Long.valueOf(this.durationField.getText().trim());

                Appointment appointment = appointmentsService.searchAppointment(name, age, sport, duration);

                if (appointment != null)
                    searchResult.setText(appointment.toString());
                else
                    searchResult.setText("No results found!");
        });
    }

    public HBox getSearchBox()
    {
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);

        gridPane.add(new Label("Age:"), 2, 0);
        gridPane.add(ageField, 3, 0);

        gridPane.add(new Label("Sport:"), 0, 1);
        gridPane.add(sportField, 1, 1);

        gridPane.add(new Label("Duration:"), 2, 1);
        gridPane.add(durationField, 3, 1);

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        HBox searchBox = new HBox(40, gridPane, searchButton);
        searchBox.setAlignment(Pos.CENTER);

        return searchBox;
    }

    public void setupFilterByAge()
    {
        this.filterByAgeButton.setOnAction(event -> {

            Dialog<Integer> dialog = new Dialog<>();
            dialog.setTitle("FILTER BY AGE");

            ButtonType confirmationButton = new ButtonType("CONFIRM", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmationButton, ButtonType.CANCEL);

            TextField ageF = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.add(new Label("AGE"), 0, 0);
            gridPane.add(ageF, 1, 0);

            dialog.getDialogPane().setContent(gridPane);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmationButton)
                    return Integer.parseInt(ageF.getText());

                return null;
            });

            Optional<Integer> result = dialog.showAndWait();

            result.ifPresent(event2 -> {
                Integer age = Integer.parseInt(ageF.getText());

                List<Appointment> appointments = appointmentsService.filterAppointmentsByPersonAge(age);
                ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);

                this.filterTableView.setItems(observableList);
            });
        });
    }

    public void setupFilterBySubscription()
    {
        this.filterBySubscriptionButton.setOnAction(event -> {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("FILTER BY SUBCRIPTION");

                ButtonType confirmationButton = new ButtonType("CONFIRM", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(confirmationButton, ButtonType.CANCEL);

                TextField subscriptionF = new TextField();

                GridPane gridPane = new GridPane();
                gridPane.add(new Label("SUBSCRIPTION:"), 0, 0);
                gridPane.add(subscriptionF, 1, 0);

                dialog.getDialogPane().setContent(gridPane);

                dialog.setResultConverter(dialogButton -> {
                     if (dialogButton == confirmationButton) {
                         return subscriptionF.getText();
                     }
                    return null;
                });

                Optional<String> result = dialog.showAndWait();

                result.ifPresent(event2 -> {
                    String subscription = subscriptionF.getText();

                    List<Appointment> appointments = appointmentsService.filterAppointmentsByPersonSubscription(subscription);
                    ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);

                    this.filterTableView.setItems(observableList);
                });
        });
    }

    public void setupFilterBySport()
    {
        this.filterBySportButton.setOnAction(event -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("FILTER BY SPORT");

            ButtonType confirmationButton = new ButtonType("CONFIRM", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmationButton, ButtonType.CANCEL);

            TextField sportF = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.add(new Label("SPORT:"), 0, 0);
            gridPane.add(sportF, 1, 0);

            dialog.getDialogPane().setContent(gridPane);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmationButton) {
                    return sportF.getText();
                }
                return null;
            });

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(event2 -> {
                String sport = sportF.getText();

                List<Appointment> appointments = appointmentsService.filterAppointmentsBySport(sport);
                ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);

                this.filterTableView.setItems(observableList);
            });

        });
    }

    public void setupFilterByDuration()
    {
        this.filterByDurationButton.setOnAction(event -> {
            Dialog<Long> dialog = new Dialog<>();
            dialog.setTitle("FILTER BY DURATION");

            ButtonType confirmationButton = new ButtonType("CONFIRM", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmationButton, ButtonType.CANCEL);

            TextField durationF = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.add(new Label("DURATION:"), 0, 0);
            gridPane.add(durationF, 1, 0);

            dialog.getDialogPane().setContent(gridPane);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmationButton)
                    return Long.parseLong(durationF.getText());
                return null;
            });

            Optional<Long> result = dialog.showAndWait();

            result.ifPresent(event2 -> {
                Long duration = Long.parseLong(durationF.getText());

                List<Appointment> appointments = appointmentsService.filterAppointmentsByDuration(duration);
                ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);

                this.filterTableView.setItems(observableList);
            });
        });
    }

    public void setupFilterByPrice()
    {
        this.filterByPriceButton.setOnAction(event -> {
            Dialog<Double> dialog = new Dialog<>();
            dialog.setTitle("FILTER BY PRICE");

            ButtonType confirmationButton = new ButtonType("CONFIRM", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmationButton, ButtonType.CANCEL);

            TextField priceF = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.add(new Label("PRICE:"), 0, 0);
            gridPane.add(priceF, 1, 0);

            dialog.getDialogPane().setContent(gridPane);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmationButton)
                    return Double.parseDouble(priceF.getText());
                return null;
            });

            Optional<Double> result = dialog.showAndWait();

            result.ifPresent(event2 -> {
                Double price = Double.parseDouble(priceF.getText());

                List<Appointment> appointments = appointmentsService.filterAppointmentsByPrice(price);
                ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);

                this.filterTableView.setItems(observableList);
            });
        });
    }

    public void setupFilterByNameAndAge()
    {
        this.filterByNameAndAgeButton.setOnAction(event -> {
            Dialog<Pair<String, Integer>> dialog = new Dialog();
            dialog.setTitle("FILTER BY NAME AND AGE");

            ButtonType confirmationButton = new ButtonType("CONFIRM", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmationButton, ButtonType.CANCEL);

            TextField nameF = new TextField();
            TextField ageF = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.add(new Label("NAME:"), 0, 0);
            gridPane.add(nameF, 1, 0);
            gridPane.add(new Label("AGE:"), 0, 1);
            gridPane.add(ageF, 1, 1);

            dialog.getDialogPane().setContent(gridPane);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmationButton)
                    return new Pair<>(nameF.getText(), Integer.parseInt(ageF.getText()));

                return null;
            });

            Optional<Pair<String, Integer>> result = dialog.showAndWait();

            result.ifPresent(pair -> {
                String name = pair.getKey();
                Integer age = pair.getValue();

                List<Appointment> appointments = appointmentsService.filterAppointmentsByPersonNameAndAge(name, age);
                ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);

                this.filterTableView.setItems(observableList);
            });
        });
    }

    public TextArea getSearchResult()
    {
        return searchResult;
    }

    public Button getFilterByAgeButton() {
        return filterByAgeButton;
    }

    public Button getFilterByDurationButton() {
        return filterByDurationButton;
    }

    public Button getFilterByPriceButton() {
        return filterByPriceButton;
    }

    public Button getFilterBySportButton() {
        return filterBySportButton;
    }

    public Button getFilterBySubscriptionButton() {
        return filterBySubscriptionButton;
    }

    public TableView<Appointment> getFilterTableView() {
        return filterTableView;
    }

    public Button getFilterByNameAndAgeButton() {
        return filterByNameAndAgeButton;
    }
}
