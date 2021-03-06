
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
	ArrayList<JsonObject> data;
	Set<String> zipCodes;
	Set<String> species;
	Set<String> testTypes;
	String minYear;

	public Database() {
		data = new ArrayList<JsonObject>();
		zipCodes = new TreeSet<String>();
		species = new TreeSet<String>();
		testTypes = new TreeSet<String>();
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
		data.add(elem);

		if (Integer.valueOf(date.substring(0, 4)) < Integer.valueOf(minYear)) {
			minYear = date.substring(0, 4);
		}
		zipCodes.add(elem.get("zip").getAsString());
//		System.out.println(elem.get("zip").getAsString());

		species.add(elem.get("species").getAsString());
//		System.out.println(elem.get("species").getAsString());
		assert !species.isEmpty();
//		System.out.println(species.size()+"size");
		testTypes.add(elem.get("test").getAsString());
		assert !testTypes.isEmpty();
		writeLock.unlock();
	}

	public JsonArray export(JsonObject elem) {
		PriorityQueue<JsonObject> filtered = filter(elem);
		JsonArray filteredArray = new JsonArray();
		while (!filtered.isEmpty()) {
			filteredArray.add(filtered.poll());
		}
		return filteredArray;
	}

	public JsonArray retrieveSummary(JsonObject elem) {
		PriorityQueue<JsonObject> filtered = filter(elem);
		int[] results = new int[2];
		while (!filtered.isEmpty()) {
			if (filtered.poll().get("tested").getAsString().equals("pos")) {
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
	
	private PriorityQueue<JsonObject> filter(JsonObject elem){
		PriorityQueue<JsonObject> filtered = new PriorityQueue(15, new DateComparator());
		boolean zip = elem.has("zip");
		boolean species = elem.has("species");
		boolean disease = elem.has("disease");
		boolean tested = elem.has("tested");
		boolean date = elem.has("date");
		boolean test = elem.has("test");
		readLock.lock();
		for(JsonObject dataPoint : data){
			filtered.add(dataPoint);
		}
		for (JsonObject dataPoint : data) {
			if (zip) {
				if (!elem.get("zip").equals(dataPoint.get("zip"))) {
					filtered.remove(dataPoint);
				}
			}
			if (species)
				if (!elem.get("species").equals(dataPoint.get("species"))) {
					filtered.remove(dataPoint);
				}
			if (disease)
				if (!elem.get("disease").equals(dataPoint.get("disease"))){
					filtered.remove(dataPoint);
				}
			if (tested)
				if (!elem.get("tested").equals(dataPoint.get("tested"))) {
					filtered.remove(dataPoint);
				}
			if (date)
				if (elem.get("startDate").getAsInt() < dataPoint.get("date").getAsInt()
						&& elem.get("endDate").getAsInt() > dataPoint.get("date").getAsInt()) {
					
				} else if (date) {
					filtered.remove(dataPoint);
				}
			if (test)
				if (!elem.get("test").equals(dataPoint.get("test"))) {
					filtered.remove(dataPoint);
				}
	
		}
		readLock.unlock();
		return filtered;
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
