
package database;

import java.util.Hashtable;
import java.util.PriorityQueue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

//need to refactor code
public class Database {
	Hashtable<String, JsonObject> data;

	public Database() {
		data = new Hashtable<String, JsonObject>();
	}

	public void add(JsonObject elem) {
		data.put((elem.get("date")).toString(), elem);
	}

	public JsonArray export(JsonObject elem) {
		PriorityQueue<JsonObject> filtered = new PriorityQueue();
		boolean zip = elem.has("zip");
		boolean species = elem.has("species");
		boolean disease = elem.has("disease");
		boolean tested = elem.has("lyme");
		boolean date = elem.has("startDate") && elem.has("endDate");
		boolean test = elem.has("test");

		for (JsonObject dataPoint : data.values()) {
			if (zip) {
				if (elem.get("zip").equals(dataPoint.get("zip"))) {
					filtered.add(dataPoint);
				} else {
					filtered.remove(dataPoint);
				}
			}
			if (species)
				if (elem.get("species").equals(dataPoint.get("species"))) {
					filtered.add(dataPoint);
				} else {
					filtered.remove(dataPoint);
				}
			if (disease)
				if (elem.get("disease").equals(dataPoint.get("disease"))) {
					filtered.add(dataPoint);
				} else if (disease) {
					filtered.remove(dataPoint);
				}
			if (tested)
				if (elem.get("tested").equals(dataPoint.get("tested"))) {
					filtered.add(dataPoint);
				} else if (tested) {
					filtered.remove(dataPoint);
				}
			if (date)
				if (elem.get("startDate").getAsInt() < dataPoint.get("date").getAsInt()
						&& elem.get("endDate").getAsInt() > dataPoint.get("date").getAsInt()) {
					filtered.add(dataPoint);
				} else if (date) {
					filtered.remove(dataPoint);
				}
			if (zip)
				if (elem.get("test").equals(dataPoint.get("test"))) {
					filtered.add(dataPoint);
				} else if (test) {
					filtered.remove(dataPoint);
				}

		}
		JsonArray filteredArray = new JsonArray();
		while (!filtered.isEmpty()) {
			filteredArray.add(filtered.poll());
		}
		return filteredArray;
	}

	public JsonArray retrieveSummary(JsonObject elem) {
		PriorityQueue<JsonObject> filtered = new PriorityQueue();
		boolean zip = elem.has("zip");
		boolean species = elem.has("species");
		boolean disease = elem.has("disease");
		boolean tested = elem.has("lyme");
		boolean date = elem.has("date");
		boolean test = elem.has("test");
		int[] results = new int[2];
		for (JsonObject dataPoint : data.values()) {
			if (zip) {
				if (elem.get("zip").equals(dataPoint.get("zip"))) {
					filtered.add(dataPoint);
				} else {
					filtered.remove(dataPoint);
				}
			}
			if (species)
				if (elem.get("species").equals(dataPoint.get("species"))) {
					filtered.add(dataPoint);
				} else {
					filtered.remove(dataPoint);
				}
			if (disease)
				if (elem.get("disease").equals(dataPoint.get("disease"))) {
					filtered.add(dataPoint);
				} else if (disease) {
					filtered.remove(dataPoint);
				}
			if (tested)
				if (elem.get("tested").equals(dataPoint.get("tested"))) {
					filtered.add(dataPoint);
				} else if (tested) {
					filtered.remove(dataPoint);
				}
			if (date)
				if (elem.get("startDate").getAsInt() < dataPoint.get("date").getAsInt()
						&& elem.get("endDate").getAsInt() > dataPoint.get("date").getAsInt()) {
					filtered.add(dataPoint);
				} else if (date) {
					filtered.remove(dataPoint);
				}
			if (zip)
				if (elem.get("test").equals(dataPoint.get("test"))) {
					filtered.add(dataPoint);
				} else if (test) {
					filtered.remove(dataPoint);
				}

		}

		while (!filtered.isEmpty()) {
			if (filtered.poll().get("tested").equals("pos")) {
				results[0]++;
			} else {
				results[1]++;
			}
		}
		JsonArray resultsArray = new JsonArray();
		for (int result : results) {
			resultsArray.add(result);
		}
		return resultsArray;
	}
}
