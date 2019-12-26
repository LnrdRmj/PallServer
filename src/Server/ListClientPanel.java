package Server;
import java.awt.*;
import java.net.Socket;
import java.util.*;

import javax.swing.*;

public class ListClientPanel extends JPanel {

	private JLabel lAscolto;
	private JLabel lTitolo;
	private Vector <JLabel> labelClients;
	
	public ListClientPanel(int porta) {
		
		labelClients = new Vector <JLabel>();
		
		//this.setBackground(Color.RED);
		
		// Pongo un layout che mi aggiunge i componenti per verticale
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Label
		lAscolto = new JLabel("Ascolto sulla porta " + porta);
		lAscolto.setAlignmentX(CENTER_ALIGNMENT);
		
		// Inizializzo e personalizzo il "titolo"
		lTitolo = new JLabel("Client Connessi");
		lTitolo.setFont(new Font("Titolo", Font.BOLD, 24));
		lTitolo.setAlignmentX(CENTER_ALIGNMENT);
		
		// Aggiungo i componenti e le spaziature tra i componenti
		this.add(Box.createVerticalStrut(20));
		this.add(lAscolto);
		this.add(Box.createVerticalStrut(20));
		this.add(lTitolo);
		this.add(Box.createVerticalStrut(20));
	}
	
	// Ogni volta che un nuovo client si connette lo visualizza col suo indirizzo ip e la porta ad esso associata
	public void addClientLabel(String ip, int port) {
		//System.out.println("ip " + ip);
		JLabel tmp = new JLabel(ip + " - " + port);
		tmp.setAlignmentX(CENTER_ALIGNMENT);
		labelClients.add(tmp);
		this.add(tmp);
		this.repaint();
		this.revalidate();
	}

	public void removeLabel(int port) {
		
		Iterator <JLabel> itr = labelClients.iterator();
		
		while (itr.hasNext()) {
			
			JLabel i = itr.next();
			
			if (i.getText().contains(Integer.toString(port))) {
				System.out.println("Tolto il client sulla porta " + port);
				itr.remove();
				this.remove(i);
				updatePanel();
				break;
			}
		}
	}
	
	public void updatePanel() {
		this.repaint();
		this.revalidate();
	}
	
}










