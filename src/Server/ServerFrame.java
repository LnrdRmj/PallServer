package Server;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class ServerFrame extends JFrame implements Runnable, ActionListener{

	public JPanel panel;
	public ListClientPanel pClientPanel;

	public JButton bStart;

	private ServerSocket server;
	private Vector <Socket> clients;
	private int serverPort = 6000;

	private Thread threadRaccoltaClient;
	private ThreadPassaggioPalla threadPassaggioPalla;

	public static final int listClienPanel_index = 0;
	public static final int bStart_index = 2;
	
	public ServerFrame() {

		super("ServerFrame");

		// Instanzio dati utili alla connessione
		this.server = getServer(serverPort);
		clients = new Vector <Socket>();

		// Panel padre
		panel = new JPanel();
		//panel.setBackground(Color.RED);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Istanzio i componenti
		pClientPanel = new ListClientPanel(serverPort);
		bStart = new JButton("Lancia la pallina");
		bStart.setAlignmentX(CENTER_ALIGNMENT);

		// Assemblo il frame
		panel.add(pClientPanel);
		panel.add(Box.createVerticalGlue());
		panel.add(bStart);
		panel.add(Box.createVerticalStrut(21));

		// Aggiungo il listener al bottone
		bStart.addActionListener(this);
		// Preparo il Frame
		this.add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 650, 400, 400);
		this.setVisible(true);

		this.threadPassaggioPalla = new ThreadPassaggioPalla(this.server, panel);

		// Thread iniziale per la raccolta dei clients
		threadRaccoltaClient = new Thread(this);
		threadRaccoltaClient.start();
		threadPassaggioPalla.start();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//threadRaccoltaClient.interrupt();

		threadPassaggioPalla.launchBall();

		bStart.setEnabled(false);
		
	}

	@Override
	public void run() {
		while(true) {
			Socket newSocket = accept();
			String ip = newSocket.getInetAddress().getHostAddress();
			int port = newSocket.getPort();
			((ListClientPanel)pClientPanel).addClientLabel(ip, port);
			threadPassaggioPalla.addClient(newSocket);
			clients.add(newSocket);
		}
	}

	public Socket accept() {
		try {
			return server.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ServerSocket getServer(int port) {
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
