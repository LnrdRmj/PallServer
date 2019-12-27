package Client;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;

public class ClientPanel extends JPanel implements ActionListener, Runnable, ChangeListener{
	
	private JLabel lHost;
	private JLabel lPort;
	private JLabel lErrore; 
	
	private JTextField tHost;
	private JTextField tPort;
	
	private JButton bConnetti;
	
	private Socket socket;
	
	private DataInputStream dis;
	private DataOutputStream dos;
	
	private Thread threadWaitBall;
	
	private boolean draw = false;
	private boolean informationSent = false;
	
	final private int port = 6000;
	final private String host = "localhost";
	
	// Attributi Pallina
	
	private int d = 100;
	private int x = 0, y = 0;
	private int xOffset = 3, yOffset = 1;
	private final int timeOut = 10;
	
	private Color colorePalla;
	
	public ClientPanel() {
		
		super();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		lHost = new JLabel("Inserisci ip server");
		lPort = new JLabel("Inserisci porta server");
		lErrore = new JLabel();
		tHost = new JTextField("localhost");
		tPort = new JTextField("6000");
		bConnetti = new JButton("Connetti al server");
		
		lHost.setAlignmentX(CENTER_ALIGNMENT);
		lPort.setAlignmentX(CENTER_ALIGNMENT);
		lErrore.setAlignmentX(CENTER_ALIGNMENT);
		tHost.setAlignmentX(CENTER_ALIGNMENT);
		tPort.setAlignmentX(CENTER_ALIGNMENT);
		bConnetti.setAlignmentX(CENTER_ALIGNMENT);
		
		tHost.setColumns(40);
		tHost.setMaximumSize(new Dimension(200, 30));
		tPort.setColumns(40);
		tPort.setMaximumSize(new Dimension(200, 30));
		
		Font f = new Font("Titolo", Font.BOLD, 24);
		
		lHost.setFont(f);
		lPort.setFont(f);
		
		f = new Font("Titolo", Font.BOLD, 18);
		
		lErrore.setFont(f);
		lErrore.setForeground(Color.RED);
		lErrore.setVisible(false);
		
		bConnetti.addActionListener(new ConnectListener(this));
		
		this.add(Box.createVerticalStrut(30));
		this.add(lHost);
		this.add(Box.createVerticalStrut(10));
		this.add(tHost);
		this.add(Box.createVerticalStrut(20));
		this.add(lPort);
		this.add(Box.createVerticalStrut(10));
		this.add(tPort);
		this.add(Box.createVerticalStrut(20));
		this.add(bConnetti);
		this.add(Box.createVerticalGlue());
		this.add(lErrore);
		this.add(Box.createVerticalStrut(70));
		
	}
	
	public void createSocket() {
		
		try {
			
			socket = setSocket(tHost.getText(), Integer.parseInt(tPort.getText()));
			
			dis = getInputStream();
			dos = getOutputStream();
			
			this.removeAll();
			this.repaint();
			this.revalidate();
			
			Thread thread = new Thread(this);
			thread.start();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			this.showError("Connessione rifiutata");
		}
		
	}
	
	@Override
	public void run() {
		if (streamRead().equals("Inizio")) {
			this.removeAll();
			Timer t = new Timer(timeOut, this);
			t.start();
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		
		try {
			if (socket == null && e.getSource() instanceof JButton) {
				socket = setSocket(tHost.getText(), Integer.parseInt(tPort.getText()));
				
				dis = getInputStream();
				dos = getOutputStream();
				
				this.removeAll();
				this.repaint();
				this.revalidate();
				
				Thread thread = new Thread(this);
				thread.start();
			}
		}
		catch(IOException ioe) {
			this.showError("Connessione rifiutata");
		}
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		if (draw) updateBallCoordinates();
		else waitBall();
	}
	
	private void showError(String errorMessage) {
		
		lErrore.setText(errorMessage);
		lErrore.setVisible(true);
		
		this.repaint();
		this.revalidate();
		
	}
	
	private void waitBall() {
	
		if (streamAvailable() > 0) {
			
			String letto = streamRead();
			//System.out.println("Il client ha letto " + letto);
			
			StringTokenizer st = new StringTokenizer(letto, ";");
			String direzione = st.nextToken();
			
			if (direzione.equals("d")) x = -d;
			else x = this.getWidth();
			
			this.y = Integer.parseInt(st.nextToken());
			this.xOffset = Integer.parseInt(st.nextToken());
			this.yOffset = Integer.parseInt(st.nextToken());
			SmoothColorChanger.setValues(
			/*R*/							Integer.parseInt(st.nextToken()),
			/*G*/							Integer.parseInt(st.nextToken()),
			/*B*/							Integer.parseInt(st.nextToken()),
			/*valueToChange(R-G-B)*/		Integer.parseInt(st.nextToken()), 
			/*offsetColor*/					Integer.parseInt(st.nextToken())
			);
			
			draw = true;
			informationSent = false;
		}
		
	}
	
	private void updateBallCoordinates() {
		 // Sbatte a destra
		if (x + xOffset > this.getWidth() - d && !informationSent && xOffset > 0) {
			writeStream(getBallInformation());
			if (this.streamRead().equals("Rimbalza")) {
				informationSent = false;
				xOffset *= -1;
			}
			else informationSent = true;
		}
		// Sbatte sinistra
		else if (x + xOffset < 0  && !informationSent && xOffset < 0) {
			writeStream(getBallInformation());
			if (this.streamRead().equals("Rimbalza")) {
				informationSent = false;
				xOffset *= -1;
			}
			else informationSent = true;
		}
		
		if ((x + xOffset < -d && xOffset < 0) || (x + xOffset > this.getWidth() && xOffset > 0)) draw = false;
		
		if (y + yOffset > this.getHeight() - d || y + yOffset < 0) yOffset *= -1;
		
		x += xOffset;
		y += yOffset;
		
	}
	
	private String getBallInformation() {
		
		String info;
		
		if (informationSent) {
			return "n";
		}
		
		if (xOffset < 0) info = "s;";
		else info = "d;";
		
		info = info.concat(this.y + ";" + this.xOffset + ";" + this.yOffset + ";" + SmoothColorChanger.getR() + ";"+ SmoothColorChanger.getG() + ";"+ SmoothColorChanger.getB() + ";"+ SmoothColorChanger.getVTC() + ";"+ SmoothColorChanger.getOffset());
		
		return info;
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		if (draw) {
			colorePalla = SmoothColorChanger.getColor();
			
			g.setColor(colorePalla);
			g.fillOval(x, y, d, d);
		}
	}
	
	private String streamRead() {
		try {
			return dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private int streamAvailable() {
		try {
			return dis.available();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public Socket setSocket(String host, int port) throws IOException{
			return new Socket(host, port);
	}

	private void writeStream(String message) {
		try {
			dos.writeUTF(message);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private DataOutputStream getOutputStream() {
		try {
			return new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private DataInputStream getInputStream() {
		try {
			return new DataInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void disconnect() {
		if (socket != null) {
			this.writeStream("Disconnessione"); 
			
			this.writeStream(getBallInformation());
			
			try {
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Socket getSocket() {
		return this.socket;
	}
}
