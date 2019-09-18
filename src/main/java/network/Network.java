package network;

import utils.CalculUtils;
import utils.NetworkUtils;

public class Network {

	public final int[] layerSizes;
	public final int inputSize;
	public final int outputSize;
	public final int networkSize;

	private double[][] output;
	private double[][][] weights;
	private double[][] bias;

	private double[][] errorSignal;
	private double[][] outputDerivative;

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

		this.errorSignal = new double[networkSize][];
		this.outputDerivative = new double[networkSize][];

		for (int i = 0; i < networkSize; i++) {
			this.output[i] = new double[layerSizes[i]];
			this.errorSignal[i] = new double[layerSizes[i]];
			this.outputDerivative[i] = new double[layerSizes[i]];
			this.bias[i] = NetworkUtils.createRandomArray(layerSizes[i], 0.3, 0.7);

			if (i > 0)
				this.weights[i] = NetworkUtils.createRandomArray(layerSizes[i], layerSizes[i - 1], -0.3, 0.7);
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
				outputDerivative[layer][neuron] = output[layer][neuron] * (1 - output[layer][neuron]);
			}
		}
		return output[networkSize - 1];
	}

	public void train(double[] input, double[] target, double eta) {
		if (input.length != inputSize || target.length != outputSize)
			return;
		calculate(input);
		backpropError(target);
		updateWeights(eta);
	}

	public void backpropError(double[] target) {
		for (int neuron = 0; neuron < layerSizes[networkSize - 1]; neuron++) {
			errorSignal[networkSize - 1][neuron] = (output[networkSize - 1][neuron] - target[neuron])
					* outputDerivative[networkSize - 1][neuron];
		}
		for (int layer = networkSize - 2; layer > 0; layer--) {
			for (int neuron = 0; neuron < layerSizes[networkSize - 1]; neuron++) {
				double sum = 0;
				for (int nextNeuron = 0; nextNeuron < layerSizes[layer + 1]; nextNeuron++) {
					sum += weights[layer + 1][nextNeuron][neuron] * errorSignal[layer + 1][nextNeuron];
				}
				this.errorSignal[layer][neuron] = sum * outputDerivative[layer][neuron];
			}
		}
	}

	public void updateWeights(double eta) {
		for (int layer = 1; layer < networkSize; layer++) {
			for (int neuron = 0; neuron < layerSizes[layer]; neuron++) {
				double delta = -eta * errorSignal[layer][neuron];
				bias[layer][neuron] += delta;
				for (int prevNeuron = 0; prevNeuron < layerSizes[layer - 1]; prevNeuron++) {
					weights[layer][neuron][prevNeuron] += delta * output[layer - 1][prevNeuron];
				}
			}
		}
	}
}
