package com.example.demo1;

import Model.Appointment;
import Model.Bank;
import Model.ListImplementation;
import RandomFile.RandomFilePersons;
import RandomFile.RandomStartAppointments;
import Service.AppointmentsService;
import Service.FileService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.locks.Lock;

/** GENERATE CONTROLLER, WHICH HAVE GENERATE BUTTON **/

public class GenerateController {
    private Button generateButton;
    private TableView tableView;
    private AppointmentsService appointmentsService;
    private RandomStartAppointments randomStartAppointments;
    private RandomFilePersons randomFilePersons;
    private Random random;
    private Lock locker;
    private Bank bank;
    private FileService fileService;

    public GenerateController(TableView tableView, AppointmentsService appointmentsService, RandomStartAppointments randomStartAppointments, RandomFilePersons randomFilePersons, Random random, Lock locker, Bank bank, FileService fileService)
    {
        this.generateButton = new Button("GENERATE");
        this.tableView = tableView;
        this.appointmentsService = appointmentsService;
        this.randomStartAppointments = randomStartAppointments;
        this.randomFilePersons = randomFilePersons;
        this.random = random;
        this.locker = locker;
        this.bank = bank;
        this.fileService = fileService;
    }

    public void setupGenerateaAppointments()
    {
        this.generateButton.setOnAction(event -> {
            Dialog<Integer> dialog = new Dialog<>();
            dialog.setTitle("GENERATE");

            ButtonType generateButton = new ButtonType("Generate", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(generateButton, ButtonType.CANCEL);

            TextField generateField = new TextField();
            generateField.setText("");

            GridPane gridPane = new GridPane();
            gridPane.add(new Label("NUMBER:"), 0, 0);
            gridPane.add(generateField, 1, 0);

            dialog.getDialogPane().setContent(gridPane);

            dialog.setResultConverter(diaglogButton -> {
                if (diaglogButton == generateButton)
                    return Integer.parseInt(generateField.getText());

                return null;
            });

            Optional<Integer> result = dialog.showAndWait();

            result.ifPresent(event2 -> {
                Integer number = Integer.parseInt(generateField.getText());

                try {
                    appointmentsService.generateThreadAppointments(number, randomStartAppointments, randomFilePersons, locker, random, bank, fileService);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                List<Appointment> appointments = appointmentsService.getAllAppointmentsAsNormalList();

                ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointments);

                this.tableView.setItems(observableList);
            });
        });
    }

    public Button getGenerateButton() {
        return generateButton;
    }
}
