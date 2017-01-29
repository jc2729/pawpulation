package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import com.google.gson.JsonObject;

public class ArrToCSV {
	
	private static String fileLoc = "results.csv";
	
	public static void arrayToCSV(JsonObject [] objs) throws IOException{
		StringBuilder res = new StringBuilder();
		res.append("Date,");
		res.append("ZipCode,");
		res.append("Species,");
		res.append("Disease,");
		res.append("Test,");
		res.append("TestResult\n");
		for(JsonObject o : objs){
			res.append(o.get("date").toString().substring(0,4) + "-" + 
					o.get("date").toString().substring(4,6) + "-" +
					o.get("date").toString().substring(6) + ",");
			res.append(o.get("zip").toString() + ",");
			res.append(o.get("species").toString() + ",");
			res.append(o.get("disease").toString() + ",");
			res.append(o.get("test").toString() + ",");
			res.append(o.get("tested").toString() + ",");
			res.append("\n");
		}
		File f = new File(fileLoc);
		FileWriter write = new FileWriter(f);
		write.write(res.toString());
		write.close();
	}

}
