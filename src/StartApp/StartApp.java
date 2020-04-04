package StartApp;

import Client.ClientFrame;
import Server.ServerFrame;

public class StartApp {
	
	public static void main(String[] args) {
		
		new ServerFrame();
		
		new ClientFrame(400,400);
		new ClientFrame(400,400);
		
	}
	
}
