package Server;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

import javax.swing.*;

public class ListClientPanel extends JPanel implements ActionListener{

	private JPanel pClientInfo;
	private JPanel pButton;
	
	private JLabel lAscolto;
	private JLabel lTitolo;
	private JLabel lIpAddress;
	private Vector <JLabel> labelClients;
	
	private ServerFrame server;
	
	private JButton bStart;
	
	public ListClientPanel(int porta, ServerFrame server) {
		
		this.server = server;
		
		labelClients = new Vector <JLabel>();
		
		//this.setBackground(Color.RED);
		
		// Pongo un layout che mi aggiunge i componenti per verticale
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Label
		try {
			lIpAddress = new JLabel("Indirizzo ip del server: " + InetAddress.getLocalHost().getHostAddress());
			
		} catch (UnknownHostException e) {
			lIpAddress = new JLabel("Insirizzo ip del server: ?" );
		}
		lIpAddress.setAlignmentX(CENTER_ALIGNMENT);
		
		lAscolto = new JLabel("Ascolto sulla porta " + porta);
		lAscolto.setAlignmentX(CENTER_ALIGNMENT);
		
		// Inizializzo e personalizzo il "titolo"
		lTitolo = new JLabel("Client Connessi");
		lTitolo.setFont(new Font("Titolo", Font.BOLD, 24));
		lTitolo.setAlignmentX(CENTER_ALIGNMENT);
		
		bStart = new JButton("Lancia la pallina");
		bStart.setAlignmentX(CENTER_ALIGNMENT);
		// Aggiungo il listener al bottone
		bStart.addActionListener(this);
		
		pClientInfo = new JPanel();
		pClientInfo.setLayout(new BoxLayout(pClientInfo, BoxLayout.Y_AXIS));
		
		// Aggiungo i componenti e le spaziature tra i componenti
		pClientInfo.add(Box.createVerticalStrut(20));
		pClientInfo.add(lAscolto);
		pClientInfo.add(Box.createVerticalStrut(20));
		pClientInfo.add(lIpAddress);
		pClientInfo.add(Box.createVerticalStrut(20));
		pClientInfo.add(lTitolo);
		pClientInfo.add(Box.createVerticalStrut(25));
		
		pButton = new JPanel();
		
		pButton.add(bStart);
		
		this.add(pClientInfo);
		this.add(Box.createVerticalGlue());
		this.add(pButton);
		
	}
	
	// Ogni volta che un nuovo client si connette lo visualizza col suo indirizzo ip e la porta ad esso associata
	public void addClientLabel(String ip, int port) {
		//System.out.println("ip " + ip);
		JLabel tmp = new JLabel(ip + " - " + port);
		tmp.setAlignmentX(CENTER_ALIGNMENT);
		labelClients.add(tmp);
		pClientInfo.add(tmp);
		pClientInfo.repaint();
		pClientInfo.revalidate();
	}

	public void removeLabel(int port) {
		
		Iterator <JLabel> itr = labelClients.iterator();
		
		while (itr.hasNext()) {
			
			JLabel i = itr.next();
			
			if (i.getText().contains(Integer.toString(port))) {
				System.out.println("Tolto il client sulla porta " + port);
				itr.remove();
				pClientInfo.remove(i);
				updatePanel();
				break;
			}
		}
	}
	
	public void updatePanel() {
		this.repaint();
		this.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		this.server.launchBall();
		bStart.setEnabled(false);
		
	}
	
	public void enableButton(boolean enabled) {
		
		bStart.setEnabled(enabled);
		
	}
	
}










