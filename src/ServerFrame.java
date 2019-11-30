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

	private boolean running = false;

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

		this.threadPassaggioPalla = new ThreadPassaggioPalla(this.server, this.pClientPanel);

		// Thread iniziale per la raccolta dei clients
		threadRaccoltaClient = new Thread(this);
		threadRaccoltaClient.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//threadRaccoltaClient.interrupt();

		this.threadPassaggioPalla.start();
		running = true;

		bStart.setEnabled(false);
	}

	@Override
	public void run() {
		while(true) {
			Socket newSocket = accept();
			String ip = newSocket.getInetAddress().getHostAddress();
			int port = newSocket.getPort();
			((ListClientPanel)pClientPanel).addClientLabel(ip, port);
			threadPassaggioPalla.addClient(newSocket, running);
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
