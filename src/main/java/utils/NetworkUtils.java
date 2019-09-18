package utils;

import java.security.SecureRandom;

public class NetworkUtils {

	private NetworkUtils() {

	}

	public static double[] createArray(int size, double initValue) {
		if (size < 1) {
			return new double[0];
		}
		double[] ar = new double[size];
		for (int i = 0; i < size; i++) {
			ar[i] = initValue;
		}
		return ar;
	}

	public static double[] createRandomArray(int size, double lowerBound, double upperBound) {
		if (size < 1) {
			return new double[0];
		}
		double[] ar = new double[size];
		for (int i = 0; i < size; i++) {
			ar[i] = randomValue(lowerBound, upperBound);
		}
		return ar;
	}

	public static double[][] createRandomArray(int sizeX, int sizeY, double lowerBound, double upperBound) {
		if (sizeX < 1 || sizeY < 1) {
			return new double[0][0];
		}
		double[][] ar = new double[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++) {
			ar[i] = createRandomArray(sizeY, lowerBound, upperBound);
		}
		return ar;
	}

	public static double randomValue(double lowerBound, double upperBound) {
		SecureRandom sr = new SecureRandom();
		return sr.nextDouble() * ((upperBound - lowerBound) + lowerBound);
	}

	public static Integer[] randomValues(int lowerBound, int upperBound, int amount) {

		lowerBound--;
		SecureRandom sr = new SecureRandom();

		if (amount > (upperBound - lowerBound)) {
			return new Integer[0];
		}

		Integer[] values = new Integer[amount];
		for (int i = 0; i < amount; i++) {
			int n = sr.nextInt((upperBound - lowerBound + 1) + lowerBound);
			while (containsValue(values, n)) {
				n = sr.nextInt((upperBound - lowerBound + 1) + lowerBound);
			}
			values[i] = n;
		}
		return values;
	}

	public static <T extends Comparable<T>> boolean containsValue(T[] ar, T value) {
		for (int i = 0; i < ar.length; i++) {
			if (ar[i] != null && value.compareTo(ar[i]) == 0) {
					return true;	
			}

		}
		return false;
	}

	public static int indexOfHighestValue(double[] values) {
		int index = 0;
		for (int i = 1; i < values.length; i++) {
			if (values[i] > values[index]) {
				index = i;
			}
		}
		return index;
	}

}
