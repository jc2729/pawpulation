package controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class MainController {
    private View view;
    
    //fields for fxml elements
    @FXML
    private Button importB;
    
    @FXML
    private Button searchB;
    
    @FXML
    private Button settingsB;
    
    @FXML
    private SplitPane splitPane;
    
    @FXML
    private AnchorPane menuPane;

    @FXML
    private AnchorPane bottomPane;
    
    /**
     * Presents a file chooser and loads file 
     */
    
    @FXML
    private void handleImportTextFileAction(ActionEvent event) {
        //load file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Import File");
        File file = fileChooser.showOpenDialog(splitPane.getScene().getWindow());
        
        //parse file into JSON object
        
        //submit POST request
    }
    
    @FXML
    private void handleSearchAction(ActionEvent event) {
        //make search pane visible
        
        //pass control to SearchController
    }
    
    @FXML
    private void handleSettingsAction(ActionEvent event) {
        
    }
    
    
    /**
     * Parse file into JSON object and submit POST request to add entries to database
     */
    public void addToDatabase(File file) {
        
    }
    
    /**
     * Submit GET request to server to retrieve information from database and update view
     */
    public void search() {
        
    }
    
    public void updateView() {
        
    }
    
}
