package ch.makery.address;

import ch.makery.address.model.Person;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * by j.meier
 * on 05.01.2015
 * All rights reserved.
 */
public class MainApp extends Application
{

    private Stage stage;
    private BorderPane rootLayout;

    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public MainApp()
    {
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Meier"));
        personData.add(new Person("Peter", "Müller"));
        personData.add(new Person("Christoph", "Walter"));
        personData.add(new Person("Joni", "Meier"));
        personData.add(new Person("Peino", "VomDienst"));
        personData.add(new Person("Jacqueline", "Zuber"));
        personData.add(new Person("Jenny", "VanGogh"));
    }

    public ObservableList<Person> getPersonData()
    {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.stage = primaryStage;
        this.stage.setTitle("AddressApp");

        this.stage.getIcons().add(new Image("file:resources/images/pic.png"));
        
        initRootLayout();

        showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout()
    {
        try
        {
            //load root layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            //show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * layout
     * Shows the person overview inside the root .
     */
    public void showPersonOverview()
    {
        try
        {
            //load person overview
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            //set person overview into the center of root layout
            rootLayout.setCenter(personOverview);

            //give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Person person)
    {
        try
        {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the main stage.
     */
    public Stage getPrimaryStage()
    {
        return stage;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
