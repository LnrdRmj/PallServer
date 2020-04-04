package Server;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class ServerFrame extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int width = 800;
	private int height = 400;
	
	private JPanel rootPanel;
		private ConfigurationPanel pConfig;
		private ListClientPanel pClientPanel;

	private ServerSocket server;
	private int serverPort = 6000;

	private Thread threadRaccoltaClient;
	private ThreadPassaggioPalla threadPassaggioPalla;

	public static final int listClienPanel_index = 0;
	public static final int bStart_index = 2;
	
	public ServerFrame() {

		super("ServerFrame");

		// Instanzio dati utili alla connessione
		this.server = getServer(serverPort);

		// Panel padre
		rootPanel = new JPanel();
		//panel.setBackground(Color.RED);
		rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.X_AXIS));

		// Istanzio i componenti
		pClientPanel = new ListClientPanel(serverPort, this);
		pConfig = new ConfigurationPanel();

		// Assemblo il frame
		rootPanel.add(pClientPanel);
		rootPanel.add(pConfig);
		
		// Preparo il Frame
		this.add(rootPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 650, width, height);
		this.setVisible(true);

		this.threadPassaggioPalla = new ThreadPassaggioPalla(this.server, rootPanel);

		// Thread iniziale per la raccolta dei clients
		threadRaccoltaClient = new Thread(this);
		threadRaccoltaClient.start();
		threadPassaggioPalla.start();
		
	}

	public void launchBall() {
		
		threadPassaggioPalla.launchBall();
		
	}
	
	@Override
	public void run() {
		while(true) {
			Socket newSocket = accept();
			String ip = newSocket.getInetAddress().getHostAddress();
			int port = newSocket.getPort();
			((ListClientPanel)pClientPanel).addClientLabel(ip, port);
			threadPassaggioPalla.addClient(newSocket);
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
