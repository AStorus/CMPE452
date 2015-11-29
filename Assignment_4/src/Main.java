import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	/*By Andrew Storus*/
	
	private static double data[][];
	private static int DATA_SIZE = 50000;
	private static double weights[];
	private static double learningRate;
	private static int node1 = 0;
	private static int node2 = 1;
	
	
	public static void main(String[] args) {
		
		//This is the single dimension data we are creating
		double[] oData = new double[DATA_SIZE];
		
		//Import the data from the supplied csv file
		importData();
		//Initialize all the data
		init();
		System.out.println("initial weights " + weights[node1] + " " + weights[node2]);
		//Train the network
		train();
		System.out.println("final weights " + weights[node1] + " " + weights[node2]);
		//Calculate what the single dimension output should be for all 50000 data points
		oData = outputData();
		//Write to readme.txt the weights and add the new single dimension y values to the third column
		//in sounds.csv
		writeToFile(oData);
	}
	
	private static void writeToFile(double[] oData) {
		//Write the weights to readme.txt, found in the Java workspace for this project
		File f1 = null;
		try {
			f1 = new File("readme.txt");
			f1.delete();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileWriter outputFile = new FileWriter("readme.txt");
			BufferedWriter out = new BufferedWriter(outputFile);
			out.write("final weights " + weights[node1] + " " + weights[node2]);
			out.newLine();
			out.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		//Write the y values to output.csv
		File f2 = null;
		try {
			f2 = new File("output.csv");
			f2.delete();
		} catch(Exception e) {
			e.printStackTrace();
		}
		//Add the new single dimension y values to the output file, also found in the 
		//workspace directory for this project
		try {
			FileWriter outputFile = new FileWriter("output.csv");
			BufferedWriter out = new BufferedWriter(outputFile);
			for(int i = 0; i < DATA_SIZE; i++) {
				out.write("" + oData[i]);
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static double[] outputData() {
		//create the output data
		double[] oData = new double[DATA_SIZE];
		for(int i = 0; i < DATA_SIZE; i++) {
			//for each data piece, calculate a new data piece by using the nodes
			//and their weights which we just calculated
			oData[i] = weights[node1] * data[i][node1] + weights[node2] * data[i][node2];
		}
		return oData;
	}
	
	private static void train() {
		double y, k;
		//Loop over all the data we are supplied with
		for(int i = 0; i < DATA_SIZE; i++) {
			/*** Wi+1 = Wi + n * yi *(xi - ki * Wi)  ***/
			//Calculate what y should be given the formula on slide 18 of the 13_PCA.pdf powerpoint
			y = weights[node1] * data[i][node1] + weights[node2] * data[i][node2];
			k = y * y;
			//Term on the right of the += is equivalent to the above triple asterixed statement
			weights[node1] += learningRate * (y * data[i][node1] - k * weights[node1]);
			weights[node2] += learningRate * (y * data[i][node2] - k * weights[node2]);
		}
	}
	
	private static void init() {
		//Initialize the weights for each node
		//Node 1 has an initial weight of 1,
		//Node 2 has an initial weight of 0
		weights = new double[2];
		weights[node1] = 1;
		weights[node2] = 0;
		//Initialize the learning rate to be 0.1
		learningRate = 0.1;

	}
	
	private static void importData() {
		//Import the data from the csv file into a member 2D array
		data = new double[50000][2];
		String file = "sound.csv";
		BufferedReader fileReader = null;
		final String DELIMITER = ",";
		int i = 0, j = 0;
		
		try {
			String line = "";
			double val;
			fileReader = new BufferedReader(new FileReader(file));
			while ((line = fileReader.readLine()) != null) {
				String[] tokens = line.split(DELIMITER);
				for(String token : tokens)
                {
					val = Double.parseDouble(token);
					data[j][i % 2] = val;
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

}
