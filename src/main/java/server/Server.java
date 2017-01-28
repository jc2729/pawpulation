package server;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.io.StringReader;

import com.google.gson.stream.JsonReader;

import static spark.Spark.delete;

public class Server {
	
	public Server(){
		
	}
	
	public void run(){
		port(8080);
		
		post("/CritterWorld/login", (request, response) -> {
			String reqbod = request.body();
			int id = Integer.parseInt(request.queryParams("session_id"));
			JsonReader reader = new JsonReader(new StringReader(reqbod));
			
			//if good
			response.status(201);
			
			//if bad: response.status(401)

			return "";

		});
	}

}
