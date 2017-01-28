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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	private Stage loginStage;

	@FXML
	private TextField usernameField;

	@FXML
	private TextField passwordField;

	//Username field
	//Password field

	public void setStage(Stage stage) {
		loginStage = stage;
	}

	//button click method
	//get username from username field
	//get password from password field
	//convert to username/password combination class and send to server

	@FXML
	private void handleLoginButton(final ActionEvent event){
		String username = usernameField.getText();
		String password = passwordField.getText();

		if(username.equals("") || password.equals("")){
			Alert a = new Alert(AlertType.ERROR, "Username and password cannot be empty.");
			a.showAndWait();
			return;
		}

		JsonObject loginCreds = new JsonObject();
		loginCreds.addProperty("username", username);
		loginCreds.addProperty("password", password);

		try{
			URL url = new URL(AccessInfo.baseURL + "/login?=sessionID=" + AccessInfo.sessionID);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			w.println(loginCreds);
			w.flush();

			if(connection.getResponseCode() == 201){
			}
			else{
				Alert a = new Alert(AlertType.ERROR, "Could not connect to server.");
				a.showAndWait();
			}
		}
		catch(IOException e){

		}
	}
}
