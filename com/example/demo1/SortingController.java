package com.example.demo1;

import Model.Appointment;
import Model.ListImplementation;
import Service.AppointmentsService;
import Sorting.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/** SORTING CONTROLLER, WHICH HAVE SORTING BUTTONS **/

public class SortingController {
    private Button sortPersonButton;
    private Button sortDateButton;
    private Button sortDurationButton;
    private Button sortSportButton;
    private Button sortPriceButton;
    private TableView<Appointment> sortingTableView;
    private PersonSorting personSorting;
    private DateSorting dateSorting;
    private DurationSorting durationSorting;
    private SportTypeSorting sportTypeSorting;
    private PriceSorting priceSorting;
    private AppointmentsService appointmentsService;

    public SortingController(TableView<Appointment> sortingTableView, PersonSorting personSorting, DateSorting dateSorting, DurationSorting durationSorting, SportTypeSorting sportTypeSorting, PriceSorting priceSorting, AppointmentsService appointmentsService)
    {
        this.sortPersonButton = new Button("PERSON SORT");
        this.sortDateButton = new Button("DATE SORT");
        this.sortDurationButton = new Button("DURATION SORT");
        this.sortSportButton = new Button("SPORT SORT");
        this.sortPriceButton = new Button("PRICE SORT");
        this.sortingTableView = sortingTableView;
        this.personSorting = personSorting;
        this.dateSorting = dateSorting;
        this.durationSorting = durationSorting;
        this.sportTypeSorting = sportTypeSorting;
        this.priceSorting = priceSorting;
        this.appointmentsService = appointmentsService;
    }

    public void setupSortingTableView()
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

        this.sortingTableView.getColumns().add(nameColumn);
        this.sortingTableView.getColumns().add(ageColumn);
        this.sortingTableView.getColumns().add(subscriptionColumn);
        this.sortingTableView.getColumns().add(balanceColumn);
        this.sortingTableView.getColumns().add(startColumn);
        this.sortingTableView.getColumns().add(endColumn);
        this.sortingTableView.getColumns().add(durationColumn);
        this.sortingTableView.getColumns().add(sportColumn);
        this.sortingTableView.getColumns().add(priceColumn);
    }

    public void setupPersonSorting()
    {
        this.sortPersonButton.setOnAction(event -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("PERSON SORTING");

            ButtonType sortByName = new ButtonType("SORT BY NAME", ButtonBar.ButtonData.OK_DONE);
            ButtonType sortByAge = new ButtonType("SORT BY AGE", ButtonBar.ButtonData.OK_DONE);

            dialog.getDialogPane().getButtonTypes().addAll(sortByName, sortByAge);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == sortByName)
                    return "NAME";
                else if (dialogButton == sortByAge)
                    return "AGE";

                return null;
            });

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(sortType -> {
                ListImplementation<Appointment> appointments = appointmentsService.getAllAppontments();

                if ("NAME".equals(sortType)) {
                    List<Appointment> sortedByName = personSorting.sortByName(appointments);
                    ObservableList<Appointment> observableList = FXCollections.observableArrayList(sortedByName);
                    this.sortingTableView.setItems(observableList);
                }
                else if ("AGE".equals(sortType)) {
                    List<Appointment> sortedByAge = personSorting.sortByAge(appointments);
                    ObservableList<Appointment> observableList = FXCollections.observableArrayList(sortedByAge);
                    this.sortingTableView.setItems(observableList);
                }
            });
        });
    }

    public void setupDateSorting()
    {
        this.sortDateButton.setOnAction(event -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("DATE SORTING");

            ButtonType sortByStartDate = new ButtonType("SORT BY START DATE", ButtonBar.ButtonData.OK_DONE);
            ButtonType sortByEndDate = new ButtonType("SORT BY END DATE", ButtonBar.ButtonData.OK_DONE);

            dialog.getDialogPane().getButtonTypes().addAll(sortByStartDate, sortByEndDate);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == sortByStartDate)
                    return "START";
                else if (dialogButton == sortByEndDate)
                    return "END";

                return null;
            });

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(sortByDate -> {
                ListImplementation<Appointment> appointments = appointmentsService.getAllAppontments();

                if ("START".equals(sortByDate))
                {
                    List<Appointment> sortedList = dateSorting.sortByStartDate(appointments);
                    ObservableList<Appointment> observableList = FXCollections.observableArrayList(sortedList);

                    sortingTableView.setItems(observableList);
                }
                else if ("END".equals(sortByDate))
                {
                    List<Appointment> sortedList = dateSorting.sortByEndDate(appointments);
                    ObservableList<Appointment> observableList = FXCollections.observableArrayList(sortedList);

                    sortingTableView.setItems(observableList);
                }
            });
        });
    }

    public void setupDurationSorting()
    {
        this.sortDurationButton.setOnAction(event -> {
            ListImplementation<Appointment> appointments = appointmentsService.getAllAppontments();
            List<Appointment> sortedList = durationSorting.sort(appointments);
            ObservableList<Appointment> observableList = FXCollections.observableArrayList(sortedList);

            this.sortingTableView.setItems(observableList);
        });
    }

    public void setupSportSorting()
    {
        this.sortSportButton.setOnAction(event -> {
            ListImplementation<Appointment> appointments = appointmentsService.getAllAppontments();
            List<Appointment> sortedList = sportTypeSorting.sort(appointments);
            ObservableList<Appointment> observableList = FXCollections.observableArrayList(sortedList);

            this.sortingTableView.setItems(observableList);
        });
    }

    public void setupPriceSorting()
    {
        this.sortPriceButton.setOnAction(event -> {
            ListImplementation<Appointment> appointments = appointmentsService.getAllAppontments();
            List<Appointment> sortedList = priceSorting.sort(appointments);
            ObservableList<Appointment> observableList = FXCollections.observableArrayList(sortedList);

            this.sortingTableView.setItems(observableList);
        });
    }

    public Button getSortDateButton() {
        return sortDateButton;
    }

    public Button getSortDurationButton() {
        return sortDurationButton;
    }

    public Button getSortPersonButton() {
        return sortPersonButton;
    }

    public Button getSortPriceButton() {
        return sortPriceButton;
    }

    public Button getSortSportButton() {
        return sortSportButton;
    }

    public TableView<Appointment> getSortingTableView() {
        return sortingTableView;
    }
}
