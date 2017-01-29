
package database;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

//need to refactor code
public class Database {
	ReadLock readLock = new ReentrantReadWriteLock().readLock();
	WriteLock writeLock = new ReentrantReadWriteLock().writeLock();
	Hashtable<String, JsonObject> data;
	Set<String> zipCodes;
	Set<String> species;
	Set<String> testTypes;
	String minYear;

	public Database() {
		data = new Hashtable<String, JsonObject>();
		zipCodes = new TreeSet<String>();
		species = new TreeSet<String>();
		minYear = "2018";
	}

	public Set getZipCodes() {
		try {
			readLock.lock();
			return zipCodes;
		} finally {
			readLock.unlock();
		}
	}

	public Set getSpecies() {
		try {
			readLock.lock();
			return species;

		} finally {
			readLock.unlock();
		}
	}

	public Set getTestTypes() {
		try {
			readLock.lock();
			return testTypes;
		}

		finally {
			readLock.unlock();
		}
	}

	public String getMinYear() {
		try {
			readLock.lock();
			return minYear;
		} finally {
			readLock.unlock();
		}
	}

	public void add(JsonObject elem) {
		String date = elem.get("date").getAsString();
		writeLock.lock();
		data.put(date, elem);
		if (Integer.valueOf(date.substring(0, 4)) < Integer.valueOf(minYear)) {
			minYear = date.substring(0, 4);
		}
		zipCodes.add(elem.get("zip").getAsString());
		species.add(elem.get("species").getAsString());
		writeLock.unlock();
	}

	public JsonArray export(JsonObject elem) {
		Comparator comparator = new DateComparator();
		PriorityQueue<JsonObject> filtered = new PriorityQueue<JsonObject>(15, comparator);
		boolean zip = elem.has("zip");
		boolean species = elem.has("species");
		boolean disease = elem.has("disease");
		boolean tested = elem.has("tested");
		boolean date = elem.has("startDate") && elem.has("endDate");
		boolean test = elem.has("test");

		readLock.lock();
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
		readLock.unlock();
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
		boolean tested = elem.has("tested");
		boolean date = elem.has("date");
		boolean test = elem.has("test");
		int[] results = new int[2];
		readLock.lock();
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
		readLock.unlock();
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

	class DateComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			return new Integer(((JsonObject) o1).get("date").getAsInt())
					.compareTo(new Integer(((JsonObject) o2).get("date").getAsInt()));
		}

		public boolean equals(Object o1) {
			return false; // not supported
		}
	}
}
