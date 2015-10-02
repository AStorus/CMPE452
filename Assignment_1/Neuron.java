import java.util.ArrayList;

public class Neuron {

	private double bias;
	private double outputWeight;
	private ArrayList<Neuron> inputNeurons;
	private double neuronVal;
	private boolean isFired;
	private boolean isInputNeuron;
	
	
	public Neuron(double neuronVal, double bias, String type){
		//Input neurons (x1, x2) have no input neurons themselves,
		//So they must be given a value (neuronVal) for their coordinates
		if(type.equals("Input")) {
			this.neuronVal = neuronVal;
			this.isFired = true;
			this.outputWeight = 0;
		}
		else {//If not an input type neuron (Middle Layer or beyond)
			this.neuronVal = 1;
			this.bias = bias;
			this.inputNeurons = new ArrayList<Neuron>();
			this.isFired = false;
			this.outputWeight = 0;
		}
	}
		
	public void addInputNeuron(Neuron ... neuron) {
		for(Neuron n : neuron) {
			this.inputNeurons.add(n);
		}
		this.fire();
	}
	
	public void setOutputWeight(float weight) {
		this.outputWeight = weight;
	}
	
	public void setBias(float bias) {
		this.bias = bias;
	}
	
	public void setNeuronVal(float neuronVal) {
		this.neuronVal = neuronVal;
	}
	
	public boolean getIsInputNeuron() {
		return this.isInputNeuron;
	}
	
	public double getNeuronVal() {
		return this.neuronVal;
	}
	
	public double getOutputWeight() {
		return this.outputWeight;
	}
	
	public double getBias() {
		return this.bias;
	}
	
	public boolean getIsFired() {
		return this.isFired;
	}
	
	public ArrayList<Neuron> getInputNeurons() {
		return this.inputNeurons;
	}
	
	public void fire() {
		float sum = 0;
		for(Neuron n : this.inputNeurons) {
			if(n.getIsFired() == true) {
				sum += n.getOutputWeight() * n.getNeuronVal() ; 
			}
		}
		//Assuming if the point is on the boundary then it is considered within
		if(sum >= this.bias) {
			this.isFired = true;
		}
		else {
			this.isFired = false;
		}
	}
	
	
	
}
