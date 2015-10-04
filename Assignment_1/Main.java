import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		double xCoord;
		double yCoord;
		
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter x coordinate");
		xCoord = s.nextDouble();
		System.out.println("Please enter y coordinate");
		yCoord = s.nextDouble();
		
		//x1 along y axis
		Neuron x1 = new Neuron(yCoord, 0, "Input");
		//x2 along x axis
		Neuron x2 = new Neuron(xCoord, 0, "Input");		
		
		ArrayList<Neuron> inputLayerNeurons = new ArrayList<Neuron>();
		inputLayerNeurons.add(x1);
		inputLayerNeurons.add(x2);
			
		//Create a new neuron with no immediate value (because it is not an input neuron
		//Node A eqn => w1 = -1, w2 = 0, theta = -4
		Neuron A = new Neuron(0, -4, "Hidden Layer");
		//Set the weights for this input
		x1.setOutputWeight(-1);
		x2.setOutputWeight(0);
		//Make x1 and x2 feed into neuron A
		//This function also automatically recalculates whether or not A has fired or not
		A.addInputNeuron(x1, x2);
		
		//Print statements showing the output of each node have been commented out
		//System.out.println("A " + A.getIsFired());		
		
		//Node B eqn => w1 = 1, w2 = -2, theta = -8
		Neuron B = new Neuron(0, -8, "Hidden Layer");
		x1.setOutputWeight(1);
		x2.setOutputWeight(-2);
		B.addInputNeuron(x1, x2);
		
		//System.out.println("B " + B.getIsFired());
		
		//Node C eqn => w1 = -1, w2 = 3, theta = 11
		Neuron C = new Neuron(0, 11, "Hidden Layer");
		x1.setOutputWeight(-1);
		x2.setOutputWeight(3);
		C.addInputNeuron(x1, x2);
		
		//System.out.println("C " + C.getIsFired());
		
		//Node D eqn => w1 = 0, w2 = 1, theta = 4
		Neuron D = new Neuron(0, 4, "Hidden Layer");
		x1.setOutputWeight(0);
		x2.setOutputWeight(1);
		D.addInputNeuron(x1, x2);
		
		//System.out.println("D " + D.getIsFired());
		
		//Node E equn => w1 = 1, w2 = 2, theta = 8
		Neuron E = new Neuron(0, 8, "Hidden Layer");
		x1.setOutputWeight(1);
		x2.setOutputWeight(2);
		E.addInputNeuron(x1, x2);
		
		//System.out.println("E " + E.getIsFired());
		
		//Node F eqn => w1 = 1, w2 = 2, theta = 13
		Neuron F = new Neuron(0, 13, "Hidden Layer");
		x1.setOutputWeight(1);
		x2.setOutputWeight(3);
		F.addInputNeuron(x1, x2);
		
		//System.out.println("F " + F.getIsFired());
		
		//Node G eqn => w1 = 1, w2 = 0, theta = 4
		Neuron G = new Neuron(0, 4, "Hidden Layer");
		x1.setOutputWeight(1);
		x2.setOutputWeight(0);
		G.addInputNeuron(x1, x2);
	
		//Create a collection of all the Neurons fed directly by the input layer neurons
		ArrayList<Neuron> midLayerNeurons = new ArrayList<Neuron>();
		midLayerNeurons.add(A);
		midLayerNeurons.add(B);
		midLayerNeurons.add(C);
		midLayerNeurons.add(D);
		midLayerNeurons.add(E);
		midLayerNeurons.add(F);
		midLayerNeurons.add(G);
		
		//Set their output weights to 1
		for(Neuron n : midLayerNeurons) { 
			n.setOutputWeight(1);
		}
		
		//This neuron fires if the point falls within the 
		//Right side of the shape
		//rightSide eqn => weight A, B, C, D = 1, theta = 3.5
		Neuron rightSide = new Neuron(0, 3.5, "Hidden Layer");
		
		//Add neurons from the right side as input neurons
		rightSide.addInputNeuron(A, B, C, D);
		//System.out.println("output right side " + rightSide.getIsFired());
		
		
		Neuron leftSide = new Neuron(0, 3.5, "Hidden Layer");
		
		leftSide.addInputNeuron(A, E, F, G);
		//System.out.println("output left side " + leftSide.getIsFired());
		
		//This neuron takes the results from the left side and right side
		//And logical ORs those results to determine whether the point is within the shape
		//output eqn => weight rightSide, leftSide = 1, theta = 0.5 
		Neuron output = new Neuron(0, 0.5, "Output");
		rightSide.setOutputWeight(1);
		leftSide.setOutputWeight(1);
		output.addInputNeuron(rightSide, leftSide);
		
		System.out.println("final output is " + output.getIsFired());
		
	}

}
