import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;

import javax.swing.JFrame;

public class ClientFrame extends JFrame implements WindowListener{
	
	private ClientPanel cp;
	
	public ClientFrame(int width, int height) {
		
		super("ClientFrame");
		
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		
		cp = new ClientPanel();
		this.add(cp);
		
		this.setVisible(true);
		
		this.addWindowListener(this);
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		this.cp.disconnect();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
