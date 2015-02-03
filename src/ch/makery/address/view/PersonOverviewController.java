package ch.makery.address.view;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

/**
 * by j.meier
 * on 06.01.2015
 * All rights reserved.
 */
public class PersonOverviewController
{
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Text firstNameLabel;
    @FXML
    private Text lastNameLabel;
    @FXML
    private Text streetLabel;
    @FXML
    private Text cityLabel;
    @FXML
    private Text postalCodeLabel;
    @FXML
    private Text birthdayLabel;

    //reference to the main application
    private MainApp mainApp;

    /**
     * constructor.
     * is called before the initialise() method.
     */
    public PersonOverviewController()
    {
    }


    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        //Initialize the person table with the two columns.

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;

        //add obervable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }
}
