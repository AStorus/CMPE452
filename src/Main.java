import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		/// top line (y = 4)
		//x1 along y axis
		Neuron x1 = new Neuron(0.5, 0, "Input");
		//x2 along x axis
		Neuron x2 = new Neuron(4, 0, "Input");
		
		ArrayList<Neuron> inputLayerNeurons = new ArrayList<Neuron>();
		inputLayerNeurons.add(x1);
		inputLayerNeurons.add(x2);
		
		//Node A eqn => w1 = 1, w2 = 1, theta = 3
		x1.setOutputWeight(-1);
		x2.setOutputWeight(0);
		
		Neuron A = new Neuron(0, -4, "Middle Layer");
		A.addInputNeuron(x1, x2);
		
		System.out.println("A " + A.getIsFired());		
		
		//Node B eqn => w1 = 1, w2 = -2, theta = -8
		Neuron B = new Neuron(0, -8, "Middle Layer");
		x1.setOutputWeight(1);
		x2.setOutputWeight(-2);
		B.addInputNeuron(x1, x2);
		
		System.out.println("B " + B.getIsFired());
		
		//Node C eqn => w1 = 1, w2 = 3, theta = 3
		Neuron C = new Neuron(0, 11, "Middle Layer");
		x1.setOutputWeight(-1);
		x2.setOutputWeight(3);
		
		C.addInputNeuron(x1, x2);
		
		System.out.println("C " + C.getIsFired());
		
		//Node D eqn => w1 = 0, w2 = 1, theta = 4
		Neuron D = new Neuron(0, 4, "Middle Layer");
		x1.setOutputWeight(0);
		x2.setOutputWeight(1);
		
		D.addInputNeuron(x1, x2);
		
		System.out.println("D " + D.getIsFired());
		
		
		Neuron E = new Neuron(0, 8, "Middle Layer");
		x1.setOutputWeight(1);
		x2.setOutputWeight(2);
		
		E.addInputNeuron(x1, x2);
		
		System.out.println("E " + E.getIsFired());
		
		Neuron F = new Neuron(0, 13, "Middle Layer");
		x1.setOutputWeight(1);
		x2.setOutputWeight(3);
		
		E.addInputNeuron(x1, x2);
		
		System.out.println("F " + F.getIsFired());
		
		Neuron G = new Neuron(0, 4, "Middle Layer");
		x1.setOutputWeight(1);
		x2.setOutputWeight(0);
		
		ArrayList<Neuron> midLayerNeurons = new ArrayList<Neuron>();
		midLayerNeurons.add(A);
		midLayerNeurons.add(B);
		midLayerNeurons.add(C);
		midLayerNeurons.add(D);
		midLayerNeurons.add(E);
		midLayerNeurons.add(F);
		midLayerNeurons.add(G);
		
		for(Neuron n : midLayerNeurons) { 
			n.setOutputWeight(1);
		}
		
		Neuron rightSide = new Neuron(0, 3.5, "Output Layer");
		
		rightSide.addInputNeuron(A, B, C, D);
		System.out.println("output right side " + rightSide.getIsFired());
		
		Neuron leftSide = new Neuron(0, 3.5, "Output Layer");
		
		leftSide.addInputNeuron(A, E, F, G);
		System.out.println("output left side " + leftSide.getIsFired());
		
		
		Neuron output = new Neuron(0, 0.5, "Output");
		rightSide.setOutputWeight(1);
		leftSide.setOutputWeight(1);
		output.addInputNeuron(rightSide, leftSide);
		
		System.out.println("final output is " + output.getIsFired());
		
	}

}
