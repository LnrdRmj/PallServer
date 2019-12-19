package Client;

import java.awt.Color;

public class SmoothColorChanger {

	private final static int maxValue = 252;
	private final static int minValue = 3;
	
	private static int R = maxValue-1;
	private static int G = minValue-1;
	private static int B = minValue-1;
	
	private static int valueToChange = 1;
	private static int offset = 1; 
	private static int multiplier = 3;
	
	static Color getColor() {
		
		switch(valueToChange) {
		case 0:
			
			R += offset;
			// Se raggiungo il massimo del rosso
			// allora dovrò iniziare a diminuire
			// il verde
			if (R >= maxValue) {
				valueToChange = 2;
				offset = -1 * multiplier;
			}
			// Se raggiungo il minimo del rosso
			// allora dovrò iniziare ad aumentare
			// il verde
			else if(R <= minValue) {
				valueToChange = 2;
				offset = 1 * multiplier;
			}
			
			break;
		case 1:
			
			G += offset;
			// Se raggiungo il massimo del verde
			// allora dovrò iniziare a diminuire
			// il rosso
			if (G >= maxValue) {
				valueToChange = 0;
				offset = -1 * multiplier;
			}
			// Se raggiungo il minimo del verde
			// allora dovrò iniziare ad aumentare
			// il rosso
			else if(G <= minValue) {
				valueToChange = 0;
				offset = 1 * multiplier;
			}
			
			break;
		case 2: 
			
			B += offset;
			// Se raggiungo il massimo del verde
			// allora dovrò iniziare a diminuire
			// il rosso
			if (B >= maxValue) {
				valueToChange = 1;
				offset = -1 * multiplier;
			}
			// Se raggiungo il minimo del verde
			// allora dovrò iniziare ad aumentare
			// il rosso
			else if(B <= minValue) {
				valueToChange = 1;
				offset = 1 * multiplier;
			}
			
		}
		
		return new Color(R, G, B);
		
	}
	
	public static void setValues(int R, int G, int B, int valueToChange, int offset) {
		
		SmoothColorChanger.R = R;
		SmoothColorChanger.G = G;
		SmoothColorChanger.B = B;
		SmoothColorChanger.valueToChange = valueToChange;
		SmoothColorChanger.offset = offset;
		
	}
	
	public static int getR() {
		return R;
	}
	
	public static int getG() {
		return G;
	}
	public static int getB() {
		return B;
	}
	public static int getVTC() {
		return valueToChange;
	}
	public static int getOffset() {
		return offset;
	}
}
