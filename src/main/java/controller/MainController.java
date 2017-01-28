package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {
    private View view;
    private Stage mainStage;

    // fields for fxml elements
    @FXML
    private MenuButton importB;

    @FXML
    private Button searchB;

    @FXML
    private Button settingsB;
    
    @FXML
    private MenuItem importTextFile;

    @FXML
    private SplitPane splitPane;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private AnchorPane bottomPane;
    
    @FXML
    private ImageView welcome;

    /**
     * Set stage for main controller.
     * @param stage
     */
    public void setStage(Stage stage) {
        mainStage = stage;
    }

    /**
     * Presents a file chooser and loads file
     */
    @FXML
    private void handleImportTextFileAction(ActionEvent event) {
        // load file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Import File");
        File file = fileChooser.showOpenDialog(splitPane.getScene().getWindow());

        // parse file into JSON object
        JsonParser parser = new JsonParser();
        try {
            JsonArray jsonArray = (JsonArray) parser.parse(new FileReader(file));
        } catch (JsonIOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        // submit POST request
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        // make search pane visible

        // pass control to SearchController
    }

    @FXML
    private void handleSettingsAction(ActionEvent event) {

    }

    /**
     * Parse file into JSON object and submit POST request to add entries to
     * database
     */
    public void addToDatabase(File file) {

    }

    /**
     * Submit GET request to server to retrieve information from database and
     * update view
     */
    public void search() {

    }

    public void updateView() {

    }

}
