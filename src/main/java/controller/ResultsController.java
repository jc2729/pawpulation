package controller;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javafx.stage.Stage;
import javafx.fxml.*;

/**
 * Results page (positives, negatives, export)
 *
 */
public class ResultsController extends Controller {
	private Stage resultStage;
	private JsonObject search;
	private int positives, negatives;
	private static String fileLoc = "results.csv";

	/**
	 * Set stage for results page controller.
	 * 
	 * @param stage
	 */
	public void setStage(Stage stage) {
		resultStage = stage;
	}

	public void init(JsonObject s, int p, int n) {
		search = s;
		positives = p;
		negatives = n;
	}

	@FXML
	private void handleExport(ActionEvent event) {
		try {
			URL url = new URL("http://" + baseURL + "/login");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
			output.write(search.toString());
			output.flush();
			connection.connect();
			if (connection.getResponseCode() == 201) {
				BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				JsonArray arr = new Gson().fromJson(r, JsonArray.class);

				StringBuilder res = new StringBuilder();
				res.append("Date,");
				res.append("ZipCode,");
				res.append("Species,");
				res.append("Disease,");
				res.append("Test,");
				res.append("TestResult\n");
				for (JsonElement e : arr) {
					JsonObject o = e.getAsJsonObject();
					res.append(o.get("date").getAsString().substring(0, 4) + "-"
							+ o.get("date").getAsString().substring(4, 6) + "-"
							+ o.get("date").getAsString().substring(6) + ",");
					res.append(o.get("zip").getAsString() + ",");
					res.append(o.get("species").getAsString() + ",");
					res.append(o.get("disease").getAsString() + ",");
					res.append(o.get("test").getAsString() + ",");
					res.append(o.get("tested").getAsString() + ",");
					res.append("\n");
				}
				File f = new File(fileLoc);
				FileWriter write = new FileWriter(f);
				write.write(res.toString());
				write.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
