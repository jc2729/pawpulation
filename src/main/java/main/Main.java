package main;

import javafx.application.*;
import server.Server;
import controller.StartView;

public class Main {
	
	public static void main(String [] args){
		Server server = new Server();
		server.run();
		StartView.main(args);
	}

}
