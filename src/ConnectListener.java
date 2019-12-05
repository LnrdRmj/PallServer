import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ConnectListener implements ActionListener{

	private ClientPanel cp;
	
	public ConnectListener(ClientPanel cp) {
		this.cp = cp;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		cp.createSocket();
		
	}

	
	
}
