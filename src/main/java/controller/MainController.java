package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.*;

public class MainController extends Controller {
    private Stage mainStage;

    private static String fileLoc = "results.csv";
    private JsonObject searchInfo;
    // fields for fxml elements
    @FXML
    private MenuItem importB;

    @FXML
    private Button exportB;

    @FXML
    private Button searchB;

    @FXML
    private SplitPane splitPane;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private AnchorPane bottomPane;

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

    @FXML
    Text textresult;

    /**
     * Set stage for main controller.
     * 
     * @param stage
     */
    public void setStage(Stage stage) {
        mainStage = stage;
        exportB.setVisible(false);
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
    public void onZipPressed(MouseEvent event) {

        try {
            URL url = new URL("http://" + baseURL + "/populate?field=zip");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 201) {
                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JsonParser parser = new JsonParser();

                JsonArray arr = parser.parse(r).getAsJsonArray();
                this.zip.getItems().clear();
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
    public void onSpeciesPressed(MouseEvent event) {
        try {
            System.out.println("hereee");
            URL url = new URL("http://" + baseURL + "/populate?field=species");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 201) {
                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JsonParser parser = new JsonParser();

                JsonArray arr = parser.parse(r).getAsJsonArray();
                this.species.getItems().clear();

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
    public void onMinYearPressed(MouseEvent event) {
        try {
            URL url = new URL("http://" + baseURL + "/populate?field=date");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            if (connection.getResponseCode() == 201) {
                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JsonParser parser = new JsonParser();

                JsonObject obj = parser.parse(r).getAsJsonObject();
                int year = Integer.parseInt(obj.get("date").getAsString());
                this.startYear.getItems().clear();

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

    @FXML
    private void onDiseasePressed(MouseEvent event) {
        this.disease.getItems().add("lyme");
        this.disease.getItems().add("anaplasma");
        this.disease.getItems().add("heartworm");
        this.disease.getItems().add("leptospirosis");
        this.disease.getItems().add("dilated cardiomyopathy");
        this.disease.getItems().add("osteosarcoma");
        this.disease.getItems().add("toxoplasma");
        this.disease.getItems().add("malignant narcissism");
        this.disease.getItems().add("hypertrophic cardiomyopathy");
        this.disease.getItems().add("plumbism");
    }

    @FXML
    private void onTestPressed(MouseEvent event) {
        try {
            URL url = new URL("http://" + baseURL + "/populate?field=test");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            if (connection.getResponseCode() == 201) {
                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JsonParser parser = new JsonParser();

                JsonArray arr = parser.parse(r).getAsJsonArray();
                this.test.getItems().clear();

                for (JsonElement obj : arr) {
                    this.test.getItems().add(obj.getAsString());
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
    private void onMinMonthPressed(MouseEvent event) {
        this.startMonth.getItems().clear();

        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                this.startMonth.getItems().add("0" + i);
            } else
                this.startMonth.getItems().add("" + i);
        }
    }

    @FXML
    private void onMaxMonthPressed(MouseEvent event) {
        this.endMonth.getItems().clear();

        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                this.endMonth.getItems().add("0" + i);
            } else
                this.endMonth.getItems().add("" + i);
        }
    }

    @FXML
    private void onMaxYearPressed(MouseEvent event) {
        try {
            URL url = new URL("http://" + baseURL + "/populate?field=date");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            if (connection.getResponseCode() == 201) {
                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JsonParser parser = new JsonParser();

                JsonObject obj = parser.parse(r).getAsJsonObject();
                int year = Integer.parseInt(obj.get("date").getAsString());
                this.endYear.getItems().clear();

                while (year < 2018) {
                    this.endYear.getItems().add(year + "");
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

    @FXML
    public void handleSearchButton(ActionEvent event) {

        try {
            if (disease.getValue() == null) {
            }
            URL url = new URL("http://" + baseURL + "/search");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestMethod("POST");

            connection.setDoOutput(true);

            JsonObject search = new JsonObject();
            if (zip.getValue() != null)
                search.addProperty("zip", zip.getValue());
            if (species.getValue() != null)
                search.addProperty("species", species.getValue());
            if (disease.getValue() != null)
                search.addProperty("disease", disease.getValue());
            else {
                Alert a = new Alert(AlertType.ERROR, "Please choose a disease type.");
                a.showAndWait();
                return;
            }
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

            searchInfo = search;
            OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
            output.write(search.toString());
            output.flush();
            connection.connect();

            if (connection.getResponseCode() == 200) {
                // TODO: change
                BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                JsonArray array = new Gson().fromJson(r, JsonArray.class);
                textresult.setText(
                        "Results:\nPositive: " + array.get(0).getAsInt() + "\nNegative: " + array.get(1).getAsInt());
                exportB.setVisible(true);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // TODO refresh the page

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
            output.write(searchInfo.toString());
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
