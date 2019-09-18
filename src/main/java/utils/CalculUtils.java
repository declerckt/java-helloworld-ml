package utils;

public class CalculUtils {
	
	private CalculUtils() {
		
	}
	
	public static double sigmoid(double x) {
		return 1d/(1+Math.exp(-x));
	}
}
