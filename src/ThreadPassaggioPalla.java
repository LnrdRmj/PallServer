import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JPanel;

public class ThreadPassaggioPalla extends Thread {

	private ServerSocket server;
	private Vector<Socket> clients;
	private ListClientPanel pListCLient;
	
	private Socket newClient;
	
	public ThreadPassaggioPalla(ServerSocket server, ListClientPanel panel) {
		
		clients = new Vector <Socket> ();
		
		this.server = server;
		this.pListCLient = panel;
		
	}

	@Override
	public void run() {
		
		for (Socket i : clients) {
			DataOutputStream dos = new DataOutputStream(getOutputStream(i));
			writeUTF(dos, "Inizio");
		}
		
		DataOutputStream dos = new DataOutputStream(getOutputStream(clients.firstElement()));
		writeUTF(dos, "d;0;3;1");
		
		Iterator <Socket> itr;
		
		while(true) {
			
			itr = clients.iterator();
			
			while(itr.hasNext()) {
				
				Socket i = itr.next();
				
				DataInputStream dis = new DataInputStream(getInputStream(i));
				if (available(dis) > 0) {
					
					String letto = readUTF(dis);
					//System.out.println("Il client ha letto " +  "(" + i.getPort() + ") " + letto);
					
					if(letto.equals("Disconnessione")) {
						try {
							i.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						pListCLient.removeLabel(i.getPort());
						itr.remove();
					}
					else {
						if (i.equals(clients.firstElement()) && letto.charAt(0) == 's') {
							// Rimbalza verso destra
							letto.replaceFirst("s", "d");
							dos = new DataOutputStream(getOutputStream(i));
							this.writeUTF(dos, "Rimbalza");
						}
						else if(i.equals(clients.lastElement()) && letto.charAt(0) == 'd') {
							// Rimbalza verso sinistra
							letto.replaceFirst("d", "s");
							dos = new DataOutputStream(getOutputStream(i));
							this.writeUTF(dos, "Rimbalza");
						}
						else if (letto.charAt(0) == 's'){
							// Passa la palla alla finestra alla sua sinistra
							dos = new DataOutputStream(getOutputStream(clients.get(clients.indexOf(i) - 1)));
							this.writeUTF(dos, letto);
							dos = new DataOutputStream(getOutputStream(i));
							this.writeUTF(dos, "-1");
						}
						else if (letto.charAt(0) == 'd') {
							dos = new DataOutputStream(getOutputStream(clients.get(clients.indexOf(i) + 1)));
							this.writeUTF(dos, letto);
							dos = new DataOutputStream(getOutputStream(i));
							this.writeUTF(dos, "-1");
						}
					}
				}
			}
			
			if (newClient != null) {
				clients.add(newClient);
				newClient = null;
			}
			
		}
	}
	
	public void addClient(Socket newClient, boolean running) {
		//clients.add(newClient);
		
		if (running) {
			writeUTF(new DataOutputStream(getOutputStream(newClient)), "Inizio");
			this.newClient = newClient;
		}
		else {
			clients.add(newClient);
		}
		
	}
	
	private String readUTF(DataInputStream dis) {
		try {
			return dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void writeUTF(DataOutputStream dos, String message) {
		try {
			dos.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public InputStream getInputStream(Socket s) {
		try {
			return s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public OutputStream getOutputStream(Socket s) {
		try {
			return s.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int available(DataInputStream dis) {
		try {
			return dis.available();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
