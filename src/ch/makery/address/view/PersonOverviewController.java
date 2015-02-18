package ch.makery.address.view;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.controlsfx.dialog.Dialogs;

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
    private void initialize()
    {
        //Initialize the person table with the two columns.

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));

    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;

        //add obervable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }

    private void showPersonDetails(Person person)
    {
        if (person != null)
        {
            //fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            cityLabel.setText(person.getCity());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else
        {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            cityLabel.setText("");
            postalCodeLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson()
    {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0)
        {
            personTable.getItems().remove(selectedIndex);
        } else
        {
            showPersonDetails(null);
            //Nothing selected
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No Person Selected")
                    .message("Please select a person in the table.")
                    .showWarning();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson()
    {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked)
        {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson()
    {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null)
        {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked)
            {
                showPersonDetails(selectedPerson);
            }

        } else
        {
            // Nothing selected.
            Dialogs.create()
                    .title("No Selection")
                    .masthead("No Person Selected")
                    .message("Please select a person in the table.")
                    .showWarning();
        }
    }
}
