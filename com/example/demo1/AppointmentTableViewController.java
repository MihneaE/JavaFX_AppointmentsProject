package com.example.demo1;

import Model.Appointment;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class AppointmentTableViewController {
    private TableView<Appointment> tableView;

    public AppointmentTableViewController(TableView<Appointment> tableView)
    {
        this.tableView = tableView;
    }

    public void appointmentTableView(ObservableList<Appointment> observableList)
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

        TableColumn<Appointment, Boolean> isActive = new TableColumn<>("Is Active");
        isActive.setCellValueFactory(cellData-> new SimpleObjectProperty<>(cellData.getValue().isActive()));

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(ageColumn);
        tableView.getColumns().add(subscriptionColumn);
        tableView.getColumns().add(balanceColumn);
        tableView.getColumns().add(startColumn);
        tableView.getColumns().add(endColumn);
        tableView.getColumns().add(durationColumn);
        tableView.getColumns().add(sportColumn);
        tableView.getColumns().add(priceColumn);
        tableView.getColumns().add(isActive);
    }


}
