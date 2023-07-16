package com.example.demo1;

import Model.Appointment;
import Model.Bank;
import Model.ListImplementation;
import Model.Person;
import Service.AppointmentsService;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/** BUTTONS CONTROLLER RESPONSABLE FOR ADD, REMOVE, UPDATE, INFO AND TOTAL AMOUNT BUTTONS **/

public class ButtonsController {
    private Button addButton;
    private Button removeButton;
    private Button updateButton;
    private Button infoButton;
    private Button bankButton;
    private Bank bank;

    public ButtonsController(Bank bank)
    {
        this.addButton = new Button("ADD");
        this.removeButton = new Button("DELETE");
        this.updateButton = new Button("UPDATE");
        this.infoButton = new Button("INFO");
        this.bankButton = new Button("TOTAL AMOUNT");
        this.bank = bank;
    }

    public void setupAddButton(AppointmentsService appointmentsService, TableView<Appointment> tableView)
    {
        addButton.setOnAction(event -> {

            Dialog<Appointment> dialog = new Dialog<>();
            dialog.setTitle("ADD NEW APPOINTMENT");

            ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            TextField nameField = new TextField();
            TextField ageField = new TextField();
            TextField subcriptionField = new TextField();
            TextField balanceField = new TextField();
            TextField startDate = new TextField();
            TextField endDate = new TextField();
            TextField durationField = new TextField();
            TextField sportField = new TextField();
            TextField priceField = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.add(new Label("Name:"), 0, 0);
            gridPane.add(nameField, 1, 0);
            gridPane.add(new Label("Age:"), 0, 1);
            gridPane.add(ageField, 1, 1);
            gridPane.add(new Label("Subscription:"), 0, 2);
            gridPane.add(subcriptionField, 1, 2);
            gridPane.add(new Label("Balance:"), 0, 3);
            gridPane.add(balanceField, 1, 3);
            gridPane.add(new Label("Start Date:"), 0, 4);
            gridPane.add(startDate, 1, 4);
            gridPane.add(new Label("End Date:"), 0, 5);
            gridPane.add(endDate, 1, 5);
            gridPane.add(new Label("Duration:"), 0, 6);
            gridPane.add(durationField,1, 6);
            gridPane.add(new Label("Sport:"), 0, 7);
            gridPane.add(sportField, 1, 7);
            gridPane.add(new Label("Price:"), 0, 8);
            gridPane.add(priceField, 1, 8);

            dialog.getDialogPane().setContent(gridPane);

            dialog.setResultConverter(dialogButton -> {

                if (dialogButton == saveButtonType)
                {
                    Person person = new Person(nameField.getText(), Integer.parseInt(ageField.getText()), subcriptionField.getText(), Double.parseDouble(balanceField.getText()));
                    LocalDateTime start = LocalDateTime.parse(startDate.getText());
                    LocalDateTime end = LocalDateTime.parse(endDate.getText());
                    Long duration = Long.parseLong(durationField.getText());
                    String sport = sportField.getText();
                    Double price = Double.parseDouble(priceField.getText());

                    Appointment addedAppointment = new Appointment(person, start, end, duration, sport, price);

                    bank.addToBank(price);

                    return  addedAppointment;
                }

                return null;
            });

            Optional<Appointment> result = dialog.showAndWait();

            result.ifPresent(appointment -> {
                try {
                    appointmentsService.addAppointment(appointment);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                tableView.getItems().add(appointment);
            });
        });
    }

    public void setupDeleteButton(AppointmentsService appointmentsService, TableView<Appointment> tableView)
    {
        removeButton.setOnAction(event -> {

            Appointment selectedAppointment = tableView.getSelectionModel().getSelectedItem();

            if (selectedAppointment != null)
            {
                bank.lessFromBank(selectedAppointment.getPrice());

                tableView.getItems().remove(selectedAppointment);
                try {
                    appointmentsService.removeAppointment(selectedAppointment);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setupUpdateButton(AppointmentsService appointmentsService, TableView<Appointment> tableView)
    {
        updateButton.setOnAction(event -> {

            Appointment selectedAppointment = tableView.getSelectionModel().getSelectedItem();

            if (selectedAppointment != null)
            {
                Dialog<Appointment> dialog = new Dialog<>();
                dialog.setTitle("UPDATE APPOINTMENT");

                ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

                TextField nameField = new TextField();
                nameField.setText(selectedAppointment.getPerson().getName());

                TextField ageField = new TextField();
                ageField.setText(String.valueOf(selectedAppointment.getPerson().getAge()));

                TextField subcriptionField = new TextField();
                subcriptionField.setText(selectedAppointment.getPerson().getSubscription());

                TextField balanceField = new TextField();
                balanceField.setText(String.valueOf(selectedAppointment.getPerson().getBalance()));

                TextField startDate = new TextField();
                startDate.setText(String.valueOf(selectedAppointment.getStart()));

                TextField endDate = new TextField();
                endDate.setText(String.valueOf(selectedAppointment.getEnd()));

                TextField durationField = new TextField();
                durationField.setText(String.valueOf(selectedAppointment.getDuration()));

                TextField sportField = new TextField();
                sportField.setText(selectedAppointment.getSport_type());

                TextField priceField = new TextField();
                priceField.setText(String.valueOf(selectedAppointment.getPrice()));

                GridPane gridPane = new GridPane();
                gridPane.add(new Label("Name:"), 0, 0);
                gridPane.add(nameField, 1, 0);
                gridPane.add(new Label("Age:"), 0, 1);
                gridPane.add(ageField, 1, 1);
                gridPane.add(new Label("Subscription:"), 0, 2);
                gridPane.add(subcriptionField, 1, 2);
                gridPane.add(new Label("Balance:"), 0, 3);
                gridPane.add(balanceField, 1, 3);
                gridPane.add(new Label("Start Date:"), 0, 4);
                gridPane.add(startDate, 1, 4);
                gridPane.add(new Label("End Date:"), 0, 5);
                gridPane.add(endDate, 1, 5);
                gridPane.add(new Label("Duration:"), 0, 6);
                gridPane.add(durationField,1, 6);
                gridPane.add(new Label("Sport:"), 0, 7);
                gridPane.add(sportField, 1, 7);
                gridPane.add(new Label("Price:"), 0, 8);
                gridPane.add(priceField, 1, 8);

                dialog.getDialogPane().setContent(gridPane);

                dialog.setResultConverter(dialogButton -> {

                    if (dialogButton == saveButtonType)
                    {
                        bank.lessFromBank(selectedAppointment.getPrice());

                        Person person = new Person(nameField.getText(), Integer.parseInt(ageField.getText()), subcriptionField.getText(), Double.parseDouble(balanceField.getText()));
                        LocalDateTime start = LocalDateTime.parse(startDate.getText());
                        LocalDateTime end = LocalDateTime.parse(endDate.getText());
                        Long duration = Long.parseLong(durationField.getText());
                        String sport = sportField.getText();
                        Double price = Double.parseDouble(priceField.getText());

                        Appointment updatedAppointment = new Appointment(person, start, end, duration, sport, price);

                        bank.addToBank(price);

                        return  updatedAppointment;
                    }

                    return null;
                });

                Optional<Appointment> result = dialog.showAndWait();

                result.ifPresent(appointment -> {
                    int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
                    try {
                        appointmentsService.updateAppointmentByIndex(selectedIndex, appointment);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    tableView.getItems().set(selectedIndex, appointment);
                });
            }
        });
    }

    public void setupInfoButton(AppointmentsService appointmentsService)
    {
        this.infoButton.setOnAction(event -> {
            Map<String, Integer> countBySport = appointmentsService.countAppointmentsBySport();
            Map<Long, Integer> countByDuration = appointmentsService.countAppointmentsByDuration();
            Map<Double, Integer> countByPrice = appointmentsService.countAppointmentsByPrice();
            Map<String, Integer> countBySubscription = appointmentsService.countAppontmentsBySubscription();
            Map<String, Long> countByActive = appointmentsService.countAppointmentsByActive();
            int totalAppointments = appointmentsService.getTotalAppointments();

            String countBySportStr = countBySport.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue())
                    .collect(Collectors.joining(" | "));
            String countByDurationStr = countByDuration.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue())
                    .collect(Collectors.joining(" | "));
            String countByPriceStr = countByPrice.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue())
                    .collect(Collectors.joining(" | "));
            String countBySubscriptiontStr = countBySubscription.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue())
                    .collect(Collectors.joining(" | "));
            String countByActiveStr = countByActive.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue())
                    .collect(Collectors.joining(" | "));


            String infoMessage = "TOTAL APPOINTMENTS: " + totalAppointments + '\n' + "APPOINTMENTS BY SPORTS: " + countBySportStr +
                    '\n' + "APPONTMENTS BY DURATION IN MINUTES: " + countByDurationStr + '\n' + "APPOINTMENTS BY PRICE: " + countByPriceStr +
                    '\n' + "APPOINTMENTS BY SUBCRIPTION: " + countBySubscriptiontStr + "\n" + "APPOINTMENTS BY ACTIVE: " + countByActiveStr;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("APPOINTMENTS INFO");

            Label label = new Label(infoMessage);
            label.setWrapText(true);

            ScrollPane scrollPane = new ScrollPane(label);
            scrollPane.setPrefWidth(500);

            alert.setHeaderText(null);
            alert.getDialogPane().setContent(scrollPane);
            alert.showAndWait();
        });
    }

    public void setupBankButton()
    {
        this.bankButton.setOnAction(event -> {
            Double totalAmount = bank.getTotalAmount();

            Label label = new Label();
            label.setText("Total amount: " + totalAmount);
            label.setStyle("-fx-font-size: 16px;");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("TOTAL AMOUNT");
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(label);

            alert.showAndWait();
        });
    }

    public Button getBankButton() {
        return bankButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getInfoButton()
    {
        return infoButton;
    }
}
