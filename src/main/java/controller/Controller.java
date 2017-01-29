package controller;

import javafx.*;
import javafx.fxml.*;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;

public abstract class Controller {
	String baseURL = "localhost:8080";

//	@FXML
//	public void setServer(String server) {
//		baseURL = server;
//	}

	@FXML
	public void handleCancelPressed(final ActionEvent event) {
		Scene scene = ((Button) (event.getSource())).getScene();
		Stage s = (Stage) scene.getWindow();
		s.close();
	}


}
