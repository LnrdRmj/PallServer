import java.io.IOException;
import java.net.*;

public class Client {

	public static void main(String[] args) {
		
		try {
			Socket s = new Socket("localhost", 6000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
