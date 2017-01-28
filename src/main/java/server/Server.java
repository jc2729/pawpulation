package server;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import static spark.Spark.delete;

public class Server {
	
	public Server(){
		
	}
	
	public void run(){
		port(8080);
		
		post("/CritterWorld/login", (request, response) -> {
			String reqbod = request.body();
			JsonObject userPass = new Gson().fromJson(new StringReader(reqbod), JsonObject.class);
			String username = userPass.getAsJsonPrimitive("username").getAsString();
			String password = userPass.getAsJsonPrimitive("password").getAsString();
			
			
			//if good
			response.status(201);
			
			//if bad: response.status(401)

			return "";

		});
	}

}
