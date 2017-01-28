package controller;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;

import javafx.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class SearchController extends Controller {
	@FXML
	ComboBox<String> zip;
	@FXML
	ComboBox<String> species;
	@FXML
	ComboBox<String> disease;
	@FXML
	ComboBox<String> test;
	@FXML
	ComboBox<String> testResult;
	@FXML
	ComboBox<String> startMonth;
	@FXML
	ComboBox<String> startYear;
	@FXML
	ComboBox<String> endMonth;
	@FXML
	ComboBox<String> endYear;

	@Override
	public void setView(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleOk(ActionEvent event) {

		try {
			URL url = new URL("http://" + baseURL + "/pullData");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			JsonObject search = new JsonObject();
			search.addProperty("zip", zip.getValue());
			search.addProperty("species", species.getValue());
			search.addProperty("disease", disease.getValue());
			search.addProperty("test", test.getValue());
			search.addProperty("testResult", testResult.getValue());
			search.addProperty("startMonth", startMonth.getValue());
			search.addProperty("startYear", startYear.getValue());
			search.addProperty("endYear", endYear.getValue());
			search.addProperty("endMonth", endMonth.getValue());

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");

			OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
			output.write(search.toString());
			output.flush();
			connection.connect();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// TODO refresh the page

	}
}
