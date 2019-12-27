package Server;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JPanel;

public class ThreadPassaggioPalla extends Thread {

	private ServerSocket server;
	private Vector<Socket> clients;
	private JPanel panel;
	
	private Socket newClient;
	
	private int xOffset = 5, yOffset = 2;
	
	private boolean running = false;
	
	public ThreadPassaggioPalla(ServerSocket server, JPanel panel) {
		
		clients = new Vector <Socket> ();
		
		this.server = server;
		this.panel= panel;
		
	}

	public void launchBall() {
		
		for (Socket i : clients) {
			DataOutputStream dos = new DataOutputStream(getOutputStream(i));
			writeUTF(dos, "Inizio");
		}
		
		DataOutputStream dos = new DataOutputStream(getOutputStream(clients.firstElement()));
		// (d/s) = direzione della pallina (destra o sinistra);
		// (0 - this.getWidth()) x della pallina;
		// (n) XOffset della pallina;
		// (n) YOffset della pallina;
		// (0-255) R
		// (0-255) G
		// (0-255) B
		// (0,1,2) Quale valore (RGB) cambiare
		// (-,+) Aumentare o diminuire il valore (RGB)
		writeUTF(dos, "d;0;" + xOffset + ";" + yOffset + ";252;3;3;1;1");
		
		running = true;
		
	}
	
	@Override
	public void run() {
		
		DataOutputStream dos;
		DataInputStream dis;
		Iterator <Socket> itr;
		
		while(true) {
			
			itr = clients.iterator();
			
			while(itr.hasNext()) {
				
				Socket i = itr.next();
				
				dis = new DataInputStream(getInputStream(i));
				if (available(dis) > 0) {
					
					String letto = readUTF(dis);
					//System.out.println("Il client ha letto " +  "(" + i.getPort() + ") " + letto);
					
					if(letto.equals("Disconnessione")) {
						
						String info = readUTF(dis);
						
						// Se il client ha la pallina e ci sono altri client a cui passare la palla
						if (!info.contentEquals("n") && clients.size() > 1) {
							
							dos = new DataOutputStream(getOutputStream(clients.get(clients.indexOf(i) + 1)));
							
							// Se il primo client viene chiuso con la pallina
							if (i.equals(clients.firstElement())) {
								
								// allora dovrà rimbalzare per forza a destra
								if (info.charAt(0) != 'd') info = info.replaceFirst("s", "d");
								
								writeUTF(dos, info);
							}
							// Se l'ultimo client viene chiuso con la pallina
							else if (i.equals(clients.lastElement())) {
								
								// allora dovrà rimbalzare per forza a sinistra
								if (info.charAt(0) != 's') info = info.replaceFirst("d", "s");
								
								writeUTF(dos, info);
							}
							else {
								
								if (info.charAt(0) == 's') 		this.writeUTF(dos, info);
								else if(info.charAt(0) == 'd') 	this.writeUTF(dos, info);								
								
							}
						}
						
						try {
							i.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						((ListClientPanel)(panel.getComponent(ServerFrame.listClienPanel_index))).removeLabel(i.getPort());
						itr.remove();
						
						if (this.clients.size() == 0) {
							running = false;
							panel.getComponent(ServerFrame.bStart_index).setEnabled(true);
						}
						
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
	
	public void addClient(Socket newClient) {
		
		if (running) {
			writeUTF(new DataOutputStream(getOutputStream(newClient)), "Inizio");
			this.newClient = newClient;
		}
		else {
			this.newClient = newClient;
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
