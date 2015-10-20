
public class Test {
	public static void testANN(double weightsSF1[], double weightsSF2[], double weightsEC1[], double weightsEC2[], double[] iWeightsSF1, double[] iWeightsSF2, double[] iWeightsEC1, double[] iWeightsEC2) {
		double[][] excelData = Main.getExcelData();
		int numDataPoints = Main.getNumDataPoints();
		double x;
		double y;
		int cor = 0;
		int incor = 0;
		
		System.out.println("\n \nResults of Testing the Network against all the Data points." + "\n \n");
		
		for(int i = 0; i < numDataPoints; i++) {
			if(excelData[i][2] != 1) {
				x = excelData[i][0];
				y = excelData[i][1];
				if(Main.calcOutput(weightsEC1, x, y) == excelData[i][2]) {
					cor += 1;
				}
				else if((Main.calcOutput(weightsEC1, x, y) == 1) && (excelData[i][2] == -1)) {
					cor += 1;
				}
				else {
					incor += 1;
				}
			}
		}
		
		System.out.println("Error Correction Line 1 (Ignores Class 1)");		
		System.out.println("EC1 Correct : " + cor + "Incorrect : " + incor);
		System.out.println("__________________________________________");
		
		
		cor = 0;
		incor = 0;
		for(int i = 0; i < numDataPoints; i++) {
			if(excelData[i][2] != -1) {
				x = excelData[i][0];
				y = excelData[i][1];
				if(Main.calcOutput(weightsEC2, x, y) == excelData[i][2]) {
					cor += 1;
				}
				else if((Main.calcOutput(weightsEC2, x, y) == 1) && (excelData[i][2] == 1)) {
					cor += 1;
				}
				else {
					incor += 1;
				}
			}
		}
		
		System.out.println("Error Correction Line 2 (Ignores Class -1)");	
		System.out.println("EC2 Correct : " + cor + " Incorrect : " + incor);
		System.out.println("__________________________________________");
				
		
		cor = 0;
		incor = 0;
		for(int i = 0; i < numDataPoints; i++) {
				x = excelData[i][0];
				y = excelData[i][1];
				if(Main.combineLines(weightsEC1, weightsEC2, x, y) == excelData[i][2]) {
					cor += 1;
				}
				else if((Main.combineLines(weightsEC1, weightsEC2, x, y) == 1 && (excelData[i][2] == -1))) {
					cor += 1;
				}
				else {
					incor += 1;
				}
		}
		
		System.out.println("Error Correction");
		System.out.println("Initial Weights Line 1 - x: " + iWeightsEC1[0] + " y: " + iWeightsEC1[1] + " bias: " + iWeightsEC1[2]);
		System.out.println("Initial Weights Line 2 - x: " + iWeightsEC2[0] + " y: " + iWeightsEC2[1] + " bias: " + iWeightsEC2[2] + "\n");
		System.out.println("Final Weights Line 1 - x: " + weightsEC1[0] + " y: " + weightsEC1[1] + " bias: " + weightsEC1[2]);
		System.out.println("Final Weights Line 2 - x: " + weightsEC2[0] + " y: " + weightsEC2[1] + " bias: " + weightsEC2[2]);
		System.out.println("Correct : " + cor + " Incorrect : " + incor);
		
		System.out.println("__________________________________________");
		
		cor = 0;
		incor = 0;
		for(int i = 0; i < numDataPoints; i++) {
			if(excelData[i][2] != 1) {
				x = excelData[i][0];
				y = excelData[i][1];
				if(Main.calcOutput(weightsSF1, x, y) == excelData[i][2]) {
					cor += 1;
				}
				else if((Main.calcOutput(weightsEC1, x, y) == 1) && (excelData[i][2] == -1)) {
					cor += 1;
				}
				else if(Main.calcOutput(weightsSF1, x, y) != excelData[i][2]){
					incor += 1;
				}
			}
		}
		
		System.out.println("Simple Feedback Line 1 (Ignores Class 1)");		
		System.out.println("SF1 Correct : " + cor + " Incorrect : " + incor);
		System.out.println("__________________________________________");
				
		cor = 0;
		incor = 0;
		for(int i = 0; i < numDataPoints; i++) {
			if(excelData[i][2] != -1) {
				x = excelData[i][0];
				y = excelData[i][1];
				if(Main.calcOutput(weightsSF2, x, y) == excelData[i][2]) {
					cor += 1;
				}
				else if(Main.calcOutput(weightsSF2, x, y) != excelData[i][2]){
					incor += 1;
				}
			}
		}
		
		System.out.println("Simple Feedback Line 2 (Ignores Class -1)"); 
		System.out.println("SF2 Correct : " + cor + " Incorrect : " + incor);
		System.out.println("__________________________________________");
		
		cor = 0;
		incor = 0;
		for(int i = 0; i < numDataPoints; i++) {
				x = excelData[i][0];
				y = excelData[i][1];
				if(Main.combineLines(weightsSF1, weightsSF2, x, y) == excelData[i][2]) {
					cor += 1;
				}
				else if((Main.combineLines(weightsSF1, weightsSF2, x, y) == 1 && (excelData[i][2] == -1))) {
					cor += 1;
				}
				else {
					incor += 1;
				}
		}
		
		System.out.println("Simple Feedback");
		System.out.println("Starting Weights Line 1 - x: " + iWeightsSF1[0] + " y: " + iWeightsSF1[1] + " bias: " + iWeightsSF1[2]);
		System.out.println("Starting Weights Line 2 - x: " + iWeightsSF2[0] + " y: " + iWeightsSF2[1] + " bias: " + iWeightsSF2[2] + "\n");
		System.out.println("Final Weights Line 1 - x: " + weightsSF1[0] + " y: " + weightsSF1[1] + " bias: " + weightsSF1[2]);
		System.out.println("Final Weights Line 2 - x: " + weightsSF2[0] + " y: " + weightsSF2[1] + " bias: " + weightsSF2[2]);
		System.out.println("Correct : " + cor + " Incorrect : " + incor);
		
		System.out.println("__________________________________________");
		
	}
}
