package server;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.io.StringReader;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import database.Database;

public class Server {
	Database db;
	JsonParser parser;

	public Server() {
		db = new Database();
		parser = new JsonParser();
	}

	public void run() {
		port(8080);
		post("/import", (request, response) -> {
			response.header("Content-Type", "application/json");
			response.status(201);
			String reqbod = request.body();
			JsonParser parser = new JsonParser();
			JsonArray dataArray = parser.parse(reqbod).getAsJsonArray();
			for (JsonElement elem : dataArray) {
				db.add(elem.getAsJsonObject());
			}
			return "";
		});

		post("/login", (request, response) -> {
			String reqbod = request.body();
			JsonObject userPass = new Gson().fromJson(new StringReader(reqbod), JsonObject.class);
			String username = userPass.getAsJsonPrimitive("username").getAsString();
			String password = userPass.getAsJsonPrimitive("password").getAsString();

			// if good
			response.status(201);

			// if bad: response.status(401)
			// response.status(401);
			return "";

		});

		post("/search", (request, response) -> {
			response.header("Content-Type", "application/json");
			response.status(200);
			return db.retrieveSummary(parser.parse(request.body()).getAsJsonObject());
		});

		post("/export", (request, response) -> {
			response.header("Content-Type", "application/json");
			response.status(200);
			return db.export(parser.parse(request.body()).getAsJsonObject());
		});

		get("/populate", (request, response) -> {
			response.header("Content-Type", "application/json");
			response.status(201);
			String field = request.queryParams("field");
			if (field.equals("zip")) {
				Set<String> zipCodes = db.getZipCodes();
				JsonArray zipJson = new JsonArray();
				for (String zip : zipCodes) {
					zipJson.add(zip);
				}
				return zipJson;
			}
			else if (field.equals("species")) {
				Set<String> species = db.getSpecies();
				JsonArray speciesJson = new JsonArray();
				for (String specie : species) {
					speciesJson.add(specie);
				}
				return speciesJson;
			}
			else if (field.equals("test")) {
				Set<String> testTypes = db.getTestTypes();
				JsonArray testTypeJson = new JsonArray();
				for (String test : testTypes) {
					testTypeJson.add(test);
				}
				return testTypeJson;
			}
			else if (field.equals("date")) {
				JsonObject dateObj = new JsonObject();
				dateObj.addProperty("date", db.getMinYear());
				return dateObj;
			}
			return "";
		});

	}

}
