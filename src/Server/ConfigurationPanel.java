package Server;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;
import javax.swing.event.*;

public class ConfigurationPanel extends JPanel implements ChangeListener{
	
	private JSlider sBallRadius;
	private int maxSliderValue = 100;
	private int minSliderValue = 10;
	
	private int radius = (maxSliderValue + minSliderValue) / 2;
	
	public ConfigurationPanel() {
		
		sBallRadius = createSlider();
		this.add(Box.createVerticalStrut(100));
		this.add(sBallRadius);
		
		update();
		
	}

	private JSlider createSlider() {
																	  // In mezzo
		JSlider slider = new JSlider(minSliderValue, maxSliderValue, (maxSliderValue + minSliderValue) / 2);
		slider.addChangeListener(this);
		slider.createStandardLabels(10, 10);
		return slider;
		
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		
		radius = sBallRadius.getValue();
		update();
		
	}
	
	public void paint(Graphics g) {
		
		super.paint(g);
		
		g.setColor(Color.black);
		g.fillOval(this.getWidth() / 2 - radius, this.getHeight() / 2 - radius, this.radius * 2, this.radius * 2);
		
	}
	
	public void update() {
		this.revalidate();
		this.repaint();
	}
	
	
	
}
