package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController extends Controller {
	private Stage mainStage;

	// fields for fxml elements
	@FXML
	private MenuItem importB;

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

	@FXML
	ComboBox<String> zip;

	@FXML
	ComboBox<String> species;

	@FXML
	ComboBox<String> disease;

	@FXML
	ComboBox<String> test;

	@FXML
	ComboBox<String> startMonth;

	@FXML
	ComboBox<String> startYear;

	@FXML
	ComboBox<String> endMonth;

	@FXML
	ComboBox<String> endYear;

	/**
	 * Set stage for main controller.
	 * 
	 * @param stage
	 */
	public void setStage(Stage stage) {
		mainStage = stage;
	}

	/**
	 * Presents a file chooser and loads file
	 * 
	 * @throws IOException
	 */
	@FXML
	private void handleImportAction(ActionEvent event) throws IOException {
		// load file chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Import File");
		File file = fileChooser.showOpenDialog(splitPane.getScene().getWindow());
		if (file != null) {
			// parse file into JSON object
			JsonParser parser = new JsonParser();
			try {

				JsonArray jsonArray = (JsonArray) parser.parse(new FileReader(file));
				URL url = new URL("http://" + baseURL + "/import");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/json");
				PrintWriter w = new PrintWriter(connection.getOutputStream());
				w.println(jsonArray);
				w.flush();
				if (connection.getResponseCode() == 201) {
					Alert a = new Alert(AlertType.CONFIRMATION, "Import successful");
					a.showAndWait();
				}
			} catch (JsonIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonSyntaxException e) {
				Alert a = new Alert(AlertType.WARNING, "Import unsuccessful");
				a.showAndWait();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

	@FXML
	public void onZipPressed(ActionEvent event) {
		JsonObject zip = new JsonObject();
		zip.addProperty("zip", "");

		try {
			URL url = new URL("http://" + baseURL + "/populate");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			w.println(zip);
			w.flush();
			if (connection.getResponseCode() == 201) {
				BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				JsonParser parser = new JsonParser();

				JsonArray arr = parser.parse(r).getAsJsonArray();
				for (JsonElement obj : arr) {
					this.zip.getItems().add(obj.getAsString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			Alert a = new Alert(AlertType.ERROR, "Could not connect to server.");
			a.showAndWait();
			return;
		}

	}

	@FXML
	public void onSpeciesPressed(ActionEvent event) {
		JsonObject species = new JsonObject();
		species.addProperty("species", "");

		try {
			URL url = new URL("http://" + baseURL + "/populate");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			w.println(species);
			w.flush();
			if (connection.getResponseCode() == 201) {
				BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				JsonParser parser = new JsonParser();

				JsonArray arr = parser.parse(r).getAsJsonArray();
				for (JsonElement obj : arr) {
					this.species.getItems().add(obj.getAsString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			Alert a = new Alert(AlertType.ERROR, "Could not connect to server.");
			a.showAndWait();
			return;
		}

	}

	@FXML
	public void onMinYearPressed(ActionEvent event) {
		JsonObject minYear = new JsonObject();
		minYear.addProperty("date", "");

		try {
			URL url = new URL("http://" + baseURL + "/populate");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			PrintWriter w = new PrintWriter(connection.getOutputStream());
			w.println(minYear);
			w.flush();
			if (connection.getResponseCode() == 201) {
				BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				JsonParser parser = new JsonParser();

				JsonObject obj = parser.parse(r).getAsJsonObject();
				this.startYear.getItems().add(obj.getAsString());
				int year = Integer.parseInt(obj.getAsString());
				while (year < 2018) {
					this.startYear.getItems().add(year + "");
					year++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			Alert a = new Alert(AlertType.ERROR, "Could not connect to server.");
			a.showAndWait();
			return;
		}

	}

	public void handleOk(ActionEvent event) {

		try {
			if (disease.getValue() == null) {
			}
			URL url = new URL("http://" + baseURL + "/search");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			JsonObject search = new JsonObject();
			if (zip.getValue() != null)
				search.addProperty("zip", zip.getValue());
			if (species.getValue() != null)
				search.addProperty("species", species.getValue());
			if (disease.getValue() != null)
				search.addProperty("disease", disease.getValue());
			if (test.getValue() != null)
				search.addProperty("test", test.getValue());

			StringBuilder startDate = new StringBuilder();
			if (startYear.getValue() != null)
				startDate.append(startYear.getValue());
			else
				startDate.append("0000");
			if (startMonth.getValue() != null)
				startDate.append(startMonth.getValue());
			else
				startDate.append("01");
			startDate.append("01");

			search.addProperty("startDate", startDate.toString());

			StringBuilder endDate = new StringBuilder();
			if (endYear.getValue() != null)
				endDate.append(startYear.getValue());
			else
				endDate.append("2018");
			if (endMonth.getValue() != null)
				endDate.append(startMonth.getValue());
			else
				endDate.append("12");
			endDate.append("31");

			search.addProperty("endDate", endDate.toString());

			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");

			OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
			output.write(search.toString());
			output.flush();
			connection.connect();
			if (connection.getResponseCode() == 201) {
				BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/results.fxml"));
				AnchorPane root;
				try {
					root = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
				ResultsController res = ((ResultsController) loader.getController());
				res.setStage(mainStage);
				JsonArray array = new Gson().fromJson(r, JsonArray.class);
				res.init(search, array.get(0).getAsInt(), array.get(1).getAsInt());

				Scene scene = new Scene(root);
				mainStage.setScene(scene);
				mainStage.show();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// TODO refresh the page

	}

}
