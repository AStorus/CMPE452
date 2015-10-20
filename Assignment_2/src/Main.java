import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;

/*By: Andrew Storus*/


public class Main {

	private static int numDataPoints = 361;
	private static double[][] excelData;
	private static double threshold = 0;
	private static double learningRate = 0.005;
	private static int MAX_NUM_IT = 1000;
	private static int trainingRange = numDataPoints * 1000;
	
	
	public static int getNumDataPoints() {
		return numDataPoints;
	}
	
	public static double[][] getExcelData() {
		double[][] ret = excelData;
		return ret;
	}
	
	public static void main(String[] args) {
		
		
		/* Initialize an array containing the excel Data */
		setExcelData();		
		/* Initialize arrays containing the weights of the four perceptron lines (two for each method*/
		/* SF stands for Simple Feedback method*/
		/* EC stands for Error Correction method*/
		double[] iWeightsSF1 = initWeights();
		double[] iWeightsSF2 = initWeights();
		double[] iWeightsEC1 = initWeights();
		double[] iWeightsEC2 = initWeights();
		
		long tSFs = System.currentTimeMillis();
		
		/* These functions train each perceptron*/
		double[] weightsSF1 = Line1SF(iWeightsSF1.clone());
		double[] weightsSF2 = Line2SF(iWeightsSF2.clone());
		
		System.out.println("Simple Feedback method took " + (System.currentTimeMillis() - tSFs) + " miliseconds.");
		long tECs = System.currentTimeMillis();
		
		double[] weightsEC1 = Line1EC(iWeightsEC1.clone());
		double[] weightsEC2 = Line2EC(iWeightsEC2.clone());
		
		System.out.println("Error Correction method took " + (System.currentTimeMillis() - tECs) + " miliseconds.");
		
		/* A seperate class which prints the results of training*/
		Test.testANN(weightsSF1, weightsSF2, weightsEC1, weightsEC2, iWeightsSF1, iWeightsSF2, iWeightsEC1, iWeightsEC2);	
		
		long tEnd = System.currentTimeMillis();
		
		Scanner s = new Scanner(System.in);
		System.out.println("\n \nPlease enter any coordinates, and this will tell you via both methods whether or not they lie within the goal or not. \n \n");
		System.out.println("Please enter x coordinate");
		double xCoord = s.nextDouble();
		System.out.println("Please enter y coordinate");
		double yCoord = s.nextDouble();
		
		int SF = combineLines(weightsSF1, weightsSF2, xCoord, yCoord);
		int EC = combineLines(weightsEC1, weightsEC2, xCoord, yCoord);
		
		if(SF == 0)
			System.out.println("According to Simple Feedback, the point is within the goal");
		else
			System.out.println("According to Simple Feedback, the point is not within the goal");
		
		if(EC == 0)
			System.out.println("According to Error Correction, the point is within the goal");
		else
			System.out.println("According to Error Correction, the point is not within the goal");
		
		s.close();
	}
	
	private static double[] initWeights() {
		/* Initialize a random weight for each perceptron*/
		double[] weights = new double[3];
		int i = 0;
		
		for(i = 0; i < 3; i++) {
			weights[i] = randNum(0, 1);
		}
		return weights;
	}
	
	private static double[] Line1EC(double weights[]) {
		/* This perceptron uses error correction to identify points*/
		/* belonging to either class -1 or 0*/
		int output;
		double localError;
		int i = 0, j = 0, k = 0;		
		
		while(j < trainingRange) {
			if(excelData[j  % numDataPoints][2] != 1) {//Ignore class 1 points
				output = calcOutput(weights, j % numDataPoints);
				localError = excelData[j % numDataPoints][2] + output;//Compute local error for this sample
				/* finding the local error (because we are dealing with -1 as the class instead of 1 we are adding */
				while(localError != 0 &&
						k < MAX_NUM_IT) { /* If there is some local error and we have not hit the max number of iterations for that point*/
					for(i = 0; i < 3; i++) 
						weights[i] = weights[i] + localError * learningRate * excelData[j % numDataPoints][i];
					/* Adjust the weights of the perceptron accordingly*/
					k++;
					/* Recalculate output & local error*/
					output = calcOutput(weights, j % numDataPoints);
					localError = excelData[j % numDataPoints][2] + output;
				}
				
			}
			j++;
		}
		return weights;
	}
	
	private static double[] Line2EC(double weights[]) {
		/* this perceptron uses error correction to identify points */
		/* belonging to either class -1 or 0 */
		/* Uses the same methodology as the Line1EC method*/
		int i = 0, j = 0, k = 0;
		int output;
		double localError;

		
		while(j < trainingRange) {
			if(excelData[j % numDataPoints][2] != -1) {//Ignore class 1 points
				output = calcOutput(weights, j % numDataPoints);
				localError = excelData[j % numDataPoints][2] - output;//Compute local error for this sample
				while(localError != 0 &&
						k < MAX_NUM_IT) { //If there is some local error
					for(i = 0; i < 3; i++) 
						weights[i] = weights[i] - localError * learningRate * excelData[j % numDataPoints][i];
					k++;
					output = calcOutput(weights, j % numDataPoints);
					localError = excelData[j % numDataPoints][2] - output;
				}
				k = 0;
			}
			j++;
		}
		return weights;
	}
	
	public static int combineLines(double line1Weights[], double line2Weights[], double xcoord, double ycoord) {
		/* This method combines the two perceptron lines for either model*/
		/* and determines whether or not the vector is class 0 or not*/
		int output1 = calcOutput(line1Weights, xcoord, ycoord);
		int output2 = calcOutput(line2Weights, xcoord, ycoord);
		if(output1 + output2 == 0) {
			/* The point is within the net*/
			return 0;
		}
		else {
			/* The point is not within the net*/
			return 1;
		}
		
	}
	
	private static double[] Line1SF(double weights[]) {
		/* this perceptron uses simple feedback to identify points */
		/* belonging to either class -1 or 0 */ 
		int j = 0, k = 0, m = 0;
		int output;
		
		/* Loop through training data*/
		while(j < trainingRange) {
			if(excelData[j % numDataPoints][2] != 1) {/* Ignore class 1 points*/
				output = calcOutput(weights, j % numDataPoints);/* calc the output*/
				while(((output != excelData[j % numDataPoints][2]) && !(output == 1 && excelData[j % numDataPoints][2] == -1)) 
						&& m < MAX_NUM_IT){ /* while the output does not equal the output */
						/* if calcOutput's return value == the excelData value, the point was classified correctly, but must also check the -1 case for the excel data*/
						/* the calcOutput function returns 0 for correct and 1 for incorrect, so we must also check if excel data = -1 */
					if(output == 0) {//output was 0, should be -1
						for(k = 0; k < 3; k++)
							weights[k] = weights[k] - learningRate * excelData[j % numDataPoints][k];
						/* Adjust the weights subtract in both cases because output = -1*/
					}
					else if(output == -1) {//output was -1, should be 0
						for(k = 0; k < 3; k++) 
							weights[k] = weights[k] + learningRate * excelData[j % numDataPoints][k];
					}
					output = calcOutput(weights, j % numDataPoints);
					m++;
				}
				m = 0;
			}
			j++;
		}
		return weights;
	}
	
	private static double[] Line2SF(double[] weights) {
		/* Error correction method for Line 2 */
		/* Line 2 ignores class -1 points */
		/* Uses same methodology as Line1SF*/
		int j = 0, k = 0, m = 0;
		int output;
		
		while(j < trainingRange) {
			if(excelData[j % numDataPoints][2] != -1) {//Ignore class -1 points
				output = calcOutput(weights, j % numDataPoints);
				while((output != excelData[j % numDataPoints][2]) && 
						(m < MAX_NUM_IT)) {
					if(output == 0) {//if output is 0, but should be 1
						for(k = 0; k < 3; k++)
							weights[k] = weights[k] - learningRate * excelData[j % numDataPoints][k];
					}
					else if(output == 1) {//if y = 1, d = 0
						for(k = 0; k < 3; k++) 
							weights[k] = weights[k] + learningRate * excelData[j % numDataPoints][k];
					}
					output = calcOutput(weights, j % numDataPoints);
					m++;
				}
				m = 0;
			}
			j++;
		}
		return weights;
	}
	
	private static void setExcelData() {
		excelData = new double[numDataPoints][3];
		String file = "Perceptron_Asg2_data.csv";
		BufferedReader fileReader = null;
		final String DELIMITER = ",";
		int i = 0;
		int j = 0;
		
		try {
			String line = "";
			double val;
			fileReader = new BufferedReader(new FileReader(file));
			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(DELIMITER);
				for(String token : tokens)
                {
					val = Double.parseDouble(token);
					excelData[j][i % 3] = val;
                    i++;
                }
				j++;
			}
		}
		catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}
	
	/* Below are the helper methods */
	private static double randNum(int min, int max) {
		double num = min + Math.random() * (max - min);
		return num;
	}
	
	private static int calcOutput(double weights[], int dataPoint) {
		/* This checks if the data point should fire */
		/* Using the excel data and the data counter, int dataPoint*/
		double sum = 0;
		for(int i = 0; i < 2; i++) {
			sum += excelData[dataPoint][i] * weights[i];
		}
		sum += weights[2]; //Add the bias
		if(sum > threshold)
			return 0;
		else 	
			return 1;
	}
	
	public static int calcOutput(double weights[], double xcoord, double ycoord) {
		double sum;
		sum = weights[0] * xcoord + weights[1] * ycoord + weights[2];
		if(sum > threshold)
			return 0;
		else
			return 1;
	}
}
