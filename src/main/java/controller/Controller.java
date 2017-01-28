package controller;

import javafx.*;
import javafx.fxml.*;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;

public abstract class Controller {
	String baseURL;

	@FXML
	public void setServer(String server) {
		baseURL = server;
	}

	@FXML
	public abstract void handleOk(ActionEvent event);

	@FXML
	public void handleCancelPressed(final ActionEvent event) {
		Scene scene = ((Button) (event.getSource())).getScene();
		Stage s = (Stage) scene.getWindow();
		s.close();
	}

	@FXML
	public abstract void setView(View view);

}
