package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class LoginController extends Controller {
	private Stage loginStage;

	@FXML
	private Button loginB;

	@FXML
	private TextField usernameField;

	@FXML
	private TextField passwordField;

	// Username field
	// Password field

	public void setStage(Stage stage) {
		loginStage = stage;
	}

	@FXML
	public void handleLoginButton(final ActionEvent event) {
		String username = usernameField.getText();
		String password = passwordField.getText();

		if (username.equals("") || password.equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Username and password cannot be empty.");
			a.showAndWait();
			return;
		}

		JsonObject loginCreds = new JsonObject();
		loginCreds.addProperty("username", username);
		loginCreds.addProperty("password", password);

		try {
			URL url = new URL("http://" + baseURL + "/login");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			w.println(loginCreds);
			w.flush();
			System.out.println("here");
			if (connection.getResponseCode() == 201) {
			} 
//			else {
//				Alert a = new Alert(AlertType.ERROR, "Could not connect to server.");
//				a.showAndWait();
//				return;
//			}
		} catch (IOException e) {
			e.printStackTrace();
			Alert a = new Alert(AlertType.ERROR, "Could not connect to server.");
			a.showAndWait();
			return;
		}
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/welcome.fxml"));
		AnchorPane root;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		((MainController) loader.getController()).setStage(loginStage);
		Scene scene = new Scene(root);
		loginStage.setScene(scene);
		loginStage.show();

	}

	@Override
	public void setView(View view) {

	}
}
