package controller;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;

import javafx.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

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

	public void handleOk(ActionEvent event) {

		try {
			URL url = new URL("http://" + baseURL + "/pullData");

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
			if (testResult.getValue() != null)
				search.addProperty("testResult", testResult.getValue());
			if (startMonth.getValue() != null)
				search.addProperty("startMonth", startMonth.getValue());
			if (startYear.getValue() != null)
				search.addProperty("startYear", startYear.getValue());
			if (endYear.getValue() != null)
				search.addProperty("endYear", endYear.getValue());
			if (endMonth.getValue() != null)
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

    public void setStage(Stage mainStage) {
        // TODO Auto-generated method stub
        
    }
}
