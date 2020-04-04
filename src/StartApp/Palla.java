package StartApp;

import java.awt.Color;

import Client.SmoothColorChanger;

public class Palla {
	
	public static int d = 100;
	public static int x = 0, y = 0;
	public static int xOffset = 3, yOffset = 1;
	public static SmoothColorChanger scc;
	
	public static void setOptions(int d, int x, int y, int xOffset, int yOffset, SmoothColorChanger scc) {
		
		Palla.d = d;
		Palla.x = x;
		Palla.y = y;
		Palla.xOffset = xOffset;
		Palla.xOffset = yOffset;
		Palla.scc = scc;
		
	}
	
	// Gets all the infos needed to pass and receive the ball
	public static String getInfo() {
		
		String info = Palla.xOffset < 0 ? "s;" : "d;";
		
		info = info.concat(	y + ";" + 
						   	xOffset + ";" + 
						   	yOffset + ";" + 
						   	scc.getR() + ";"+ 
						   	scc.getG() + ";"+ 
						   	scc.getB() + ";"+ 
						   	scc.getValueToChange() + ";"+ 
						   	scc.getOffset());
		
		return info;
		
	}
	
	// Return the color of the ball
	public static Color getColor() {return scc.getColor();}
	
}
