import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Utility {
     private int[][] traindata;
     private  int[][] testdata;

    public int[][] getTraindata() {
        return traindata;
    }

    public void setTraindata(int[][] traindata) {
        this.traindata = traindata;
    }

    public int[][] getTestdata() {
        return testdata;
    }

    public void setTestdata(int[][] testdata) {
        this.testdata = testdata;
    }

    /**
     * Function to read file name and return data in  2D array
     *
     * @param fileName
     * @return
     */
    public  int[][] readFile(String fileName) {
        //Reading data from file
        ArrayList<String> arrReadCSV = new ArrayList<String>();
        try {
            FileInputStream fileInStream = new FileInputStream(fileName);
            DataInputStream dataInStream = new DataInputStream(fileInStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(dataInStream));

            String strline = null;
            while ((strline = br.readLine()) != null) {
                arrReadCSV.add(strline);
            }

            dataInStream.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        //splitting the data and storing individual row and col
        int[][] data = new int[arrReadCSV.size()][65];
        for (int row = 0; row < arrReadCSV.size(); row++) {
            String str = arrReadCSV.get(row);
            String[] indivLineVal = str.split(",");
            for (int column = 0; column < indivLineVal.length; column++) {
                data[row][column] = Integer.valueOf(indivLineVal[column]);
            }
        }

        return data;
    }

    /**
     * @return
     */
    public String[] Label() {
        // Categorising results
        String[] label = new String[45];
        int results = 0;
        for (int row = 0; row < 10; row++) {
            for (int column = row + 1; column < 10; column++) {
                label[results++] = new String("{" + row + ", " + column + "}");
            }
        }
        return label;
    }

    public  String[] chooseMenu() {
        Scanner input = new Scanner(System.in);
        String[] fileArray = new String[2];
        int userInput=3;
        while (userInput>2) {
            System.out.print("Welcome please make a selection:\n" +
                    "0. Exit the program\n" +
                    "1. Run first fold test\n" +
                    "2. Run second fold test\n");
            userInput=input.nextInt();

                switch (userInput) {
                    case 0:
                        System.out.print("Exiting");
                        System.exit(0);
                        break;
                    case 1:
                        fileArray[0] = "cw2DataSet1.csv";
                        fileArray[1] = "cw2DataSet2.csv";
                        break;
                    case 2:
                        fileArray[1] = "cw2DataSet1.csv";
                        fileArray[0] = "cw2DataSet2.csv";
                        break;

                    default:
                        System.out.println("Choose a correct input please");
                        break;
                }

            }
        return fileArray;
    }
}
