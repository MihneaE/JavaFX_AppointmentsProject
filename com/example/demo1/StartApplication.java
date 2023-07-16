package com.example.demo1;

import Model.Bank;
import RandomFile.RandomFilePersons;
import RandomFile.RandomStartAppointments;
import Model.Appointment;
import Model.ListImplementation;
import Model.Person;
import Service.AppointmentsService;
import Service.FileService;
import Sorting.*;
import Validator.Validator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**  HERE IS DEFINED FUNCTION START, FROM WHERE THE APPLICATION IS STARTING**/

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("APPOINTMENTS");

        Random random = new Random();
        Bank bank = new Bank();
        Lock lock = new ReentrantLock();
        ListImplementation<Person> personListImplementation = new ListImplementation<>();
        RandomFilePersons randomFilePersons = new RandomFilePersons(random, personListImplementation);
        randomFilePersons.generatePersons();
        randomFilePersons.loadPersons();

        ListImplementation<Appointment> appointmentListImplementation = new ListImplementation<>();
        Map<String, Double> sportPricesPerHour = new HashMap<>();
        RandomStartAppointments randomStartAppointments = new RandomStartAppointments(random, appointmentListImplementation, randomFilePersons, sportPricesPerHour, bank);
        randomStartAppointments.generateStartAppointmentsFile();

        ListImplementation<Appointment> appointmentsFINAL = randomStartAppointments.loadAppointments();
        Validator validator = new Validator(appointmentsFINAL);
        FileService fileService = new FileService(randomFilePersons);
        AppointmentsService appointmentsService = new AppointmentsService(appointmentsFINAL, fileService, validator);

        PersonSorting personSorting = new PersonSorting();
        DateSorting dateSorting = new DateSorting();
        DurationSorting durationSorting = new DurationSorting();
        SportTypeSorting sportTypeSorting = new SportTypeSorting();
        PriceSorting priceSorting = new PriceSorting();

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ObservableList<Appointment> observableList = FXCollections.observableArrayList(appointmentsService.getAllAppointmentsAsNormalList());

        TableView<Appointment> tableView = new TableView<>();
        TableView<Appointment> filterTableView = new TableView<>();
        TableView<Appointment> sortingTableView = new TableView<>();
        AppointmentTableViewController appointmentTableViewController = new AppointmentTableViewController(tableView);
        SearchAndFilterController searchAndFilterController = new SearchAndFilterController(appointmentsService, filterTableView);
        SortingController sortingController = new SortingController(sortingTableView, personSorting, dateSorting, durationSorting, sportTypeSorting, priceSorting, appointmentsService);
        GenerateController generateController = new GenerateController(tableView, appointmentsService, randomStartAppointments, randomFilePersons, random, lock, bank, fileService);
        appointmentTableViewController.appointmentTableView(observableList);
        searchAndFilterController.setupFilterTableView();
        sortingController.setupSortingTableView();
        tableView.setItems(observableList);

        ButtonsController buttonsController = new ButtonsController(bank);
        buttonsController.setupAddButton(appointmentsService, tableView);
        buttonsController.setupDeleteButton(appointmentsService, tableView);
        buttonsController.setupUpdateButton(appointmentsService, tableView);
        buttonsController.setupInfoButton(appointmentsService);
        buttonsController.setupBankButton();

        searchAndFilterController.setupSearchButton();
        searchAndFilterController.setupFilterByAge();
        searchAndFilterController.setupFilterBySubscription();
        searchAndFilterController.setupFilterBySport();
        searchAndFilterController.setupFilterByDuration();
        searchAndFilterController.setupFilterByPrice();
        searchAndFilterController.setupFilterByNameAndAge();

        sortingController.setupPersonSorting();
        sortingController.setupDateSorting();
        sortingController.setupDurationSorting();
        sortingController.setupSportSorting();
        sortingController.setupPriceSorting();

        generateController.setupGenerateaAppointments();

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        HBox buttonBox1 = new HBox(20, buttonsController.getAddButton(), buttonsController.getRemoveButton(), buttonsController.getUpdateButton(), generateController.getGenerateButton());
        HBox infoButton = new HBox(20, buttonsController.getBankButton(), buttonsController.getInfoButton());

        BorderPane topBar = new BorderPane();
        topBar.setLeft(buttonBox1);
        topBar.setRight(infoButton);

        BorderPane.setMargin(buttonBox1, new Insets(10, 50, 10,50));
        BorderPane.setMargin(infoButton, new Insets(10, 50, 10, 50));

        tableView.setPrefHeight(120);

        VBox buttonswithTableView1 = new VBox(10, topBar, tableView);

        HBox buttonBox2 = new HBox(20, searchAndFilterController.getFilterByAgeButton(),searchAndFilterController.getFilterBySubscriptionButton(), searchAndFilterController.getFilterBySportButton(), searchAndFilterController.getFilterByDurationButton(), searchAndFilterController.getFilterByPriceButton(), searchAndFilterController.getFilterByNameAndAgeButton());
        BorderPane mid1 = new BorderPane();
        mid1.setLeft(buttonBox2);
        BorderPane.setMargin(buttonBox2, new Insets(10, 50, 20, 50));

        VBox buttonswithTableView2 = new VBox(10, buttonswithTableView1, mid1);
        VBox buttonswithTableView3 = new VBox(buttonswithTableView2, searchAndFilterController.getFilterTableView());

        searchAndFilterController.getFilterTableView().setPrefHeight(120);

        HBox buttonBox3 = new HBox(20, sortingController.getSortPersonButton(), sortingController.getSortDateButton(), sortingController.getSortDurationButton(), sortingController.getSortSportButton(), sortingController.getSortPriceButton());
        BorderPane mid2 = new BorderPane();
        mid2.setLeft(buttonBox3);
        BorderPane.setMargin(buttonBox3, new Insets(10, 50, 20, 50));

        VBox buttonswithTableView4 = new VBox(buttonswithTableView3, mid2);
        VBox buttonswithTableView5 = new VBox(buttonswithTableView4,sortingController.getSortingTableView());

        sortingController.getSortingTableView().setPrefHeight(120);

        HBox fieldsForSearch = searchAndFilterController.getSearchBox();
        BorderPane lastPane = new BorderPane();
        lastPane.setLeft(fieldsForSearch);
        BorderPane.setMargin(fieldsForSearch, new Insets(10, 50, 20, 50));

        VBox buttonswithTableView6 = new VBox(20, buttonswithTableView5, lastPane);
        VBox buttonswithTableView7 = new VBox(10, buttonswithTableView6, searchAndFilterController.getSearchResult());

        searchAndFilterController.getSearchResult().setPrefHeight(60);

        Scene scene = new Scene(buttonswithTableView7, 1000, 750);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws CloneNotSupportedException, IOException {
        launch();
        /*
        Bank bank = new Bank();
        Random random = new Random();
        ListImplementation<Person> personListImplementation = new ListImplementation<>();
        RandomFilePersons randomFilePersons = new RandomFilePersons(random, personListImplementation);
        randomFilePersons.generatePersons();
        randomFilePersons.loadPersons();

        ListImplementation<Appointment> appointmentListImplementation = new ListImplementation<>();
        Validator validator = new Validator(appointmentListImplementation);
        Map<String, Double> sportPricesPerHour = new HashMap<>();
        RandomStartAppointments randomStartAppointments = new RandomStartAppointments(random, appointmentListImplementation, randomFilePersons, sportPricesPerHour, bank);

        randomStartAppointments.generateStartAppointmentsFile();
        appointmentListImplementation = randomStartAppointments.loadAppointments();

        FileService fileService = new FileService(randomFilePersons);
        AppointmentsService appointmentsService = new AppointmentsService(appointmentListImplementation, fileService, validator);

        for (int i = 0; i < appointmentsService.getTotalAppointments(); ++i)
            System.out.println(appointmentsService.getAllAppontments().get(i));

        System.out.println('\n');

        for (int i = 0; i < appointmentsService.getTotalAppointments(); ++i)
        {
            System.out.println(appointmentsService.getAllAppointmentsAsNormalList().get(i));
        }

         */
    }

}