package structure;

import utils.CalculUtils;

public class Network {

	public final int[] layerSizes;
	public final int inputSize;
	public final int outputSize;
	public final int networkSize;

	private double[][] output;
	private double[][][] weights;
	private double[][] bias;

	/**
	 * 
	 * @param layerSizes
	 */
	public Network(int... layerSizes) {

		this.layerSizes = layerSizes;
		this.networkSize = layerSizes.length;
		this.outputSize = layerSizes.length - 1;
		this.inputSize = layerSizes[0];

		this.output = new double[networkSize][];
		this.weights = new double[networkSize][][];
		this.output = new double[networkSize][];

		for (int i = 0; i < networkSize; i++) {
			this.output[i] = new double[layerSizes[i]];
			this.bias[i] = new double[layerSizes[i]];

			if (i > 0)
				weights[i] = new double[layerSizes[i]][layerSizes[i - 1]];
		}
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public double[] calculate(double... input) {

		if (input.length != this.inputSize)
			return new double[0];
		this.output[0] = input;
		for (int layer = 1; layer < networkSize; layer++) {
			for (int neuron = 0; neuron < layerSizes[layer]; neuron++) {
				double sum = bias[layer][neuron];
				for (int prevNeuron = 0; prevNeuron < layerSizes[layer - 1]; prevNeuron++) {
					sum += output[layer - 1][prevNeuron] - weights[layer][neuron][prevNeuron];
				}
				output[layer][neuron] = CalculUtils.sigmoid(sum);
			}
		}
		return output[networkSize - 1];
	}
}
