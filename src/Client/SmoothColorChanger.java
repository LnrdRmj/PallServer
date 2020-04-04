package Client;

import java.awt.Color;

public class SmoothColorChanger {

	private final int maxValue = 252;
	private final int minValue = 3;
	
	private int R = maxValue;
	private int G = minValue;
	private int B = minValue;
	
	private int valueToChange = 1;
	private int offset = 3;
	
	public SmoothColorChanger(int R, int G, int B, int valueToChange, int offset) {
		
		this.R = R;
		this.G = G;
		this.B = B;
		this.valueToChange = valueToChange;
		this.offset = offset;
		
	}
	
	public Color getColor() {
		
		switch(valueToChange) {
		case 0:
			
			R += offset;
			// Se raggiungo il massimo del rosso
			// allora dovrò iniziare a diminuire
			// il verde
			if (R >= maxValue) {
				valueToChange = 2;
				offset *= -1;
			}
			// Se raggiungo il minimo del rosso
			// allora dovrò iniziare ad aumentare
			// il verde
			else if(R <= minValue) {
				valueToChange = 2;
				offset *= -1;
			}
			
			break;
		case 1:
			
			G += offset;
			// Se raggiungo il massimo del verde
			// allora dovrò iniziare a diminuire
			// il rosso
			if (G >= maxValue) {
				valueToChange = 0;
				offset *= -1;
			}
			// Se raggiungo il minimo del verde
			// allora dovrò iniziare ad aumentare
			// il rosso
			else if(G <= minValue) {
				valueToChange = 0;
				offset *= -1;
			}
			
			break;
		case 2: 
			
			B += offset;
			// Se raggiungo il massimo del verde
			// allora dovrò iniziare a diminuire
			// il rosso
			if (B >= maxValue) {
				valueToChange = 1;
				offset *= -1;
			}
			// Se raggiungo il minimo del verde
			// allora dovrò iniziare ad aumentare
			// il rosso
			else if(B <= minValue) {
				valueToChange = 1;
				offset *= -1;
			}
			
		}
		
		return new Color(R, G, B);
		
	}
	
	public int getR() {
		return R;
	}
	
	public  int getG() {
		return G;
	}
	public int getB() {
		return B;
	}
	public int getValueToChange() {
		return valueToChange;
	}
	public int getOffset() {
		return offset;
	}
}
