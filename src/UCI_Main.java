import java.text.DecimalFormat;

public class UCI_Main {
    public static void main(String[] args) {
        String[] fileArray = new String[2];
        // Learning rate

        while (true) {
            double rate = Double.valueOf(0.1);
            double finalEp = 0;
            double sumTstAcc = 0;
            double testFnlAccuracy = 0;
            double sumTrainAcc = 0;
            double trainFnlAccuracy = 0;
            double avgAcc = 0;
            Utility u = new Utility();
            fileArray = u.chooseMenu();
            NeuralNetwork neuralNetwork = new NeuralNetwork(rate);
            //Training Perceptrons
            u.setTraindata(u.readFile(fileArray[0]));
            u.setTestdata(u.readFile(fileArray[1]));
            int[][] ep = neuralNetwork.train(u.getTraindata());
            int[] epoch = new int[100];
            int k = 0;
            for (int i = 0; i < 10; i++) {
                for (int j = i + 1; j < 10; j++) {
                    epoch[k++] = ep[i][j];
                }
            }
            int[][] confMat = neuralNetwork.testFunction(u.getTraindata());
            double[] finalTrainAcc = trainAccuracy(confMat, neuralNetwork, u);
            double[] finalTestAcc = testAccuracy(confMat, neuralNetwork, u);
            DecimalFormat decimal = new DecimalFormat("#.####"); // ensures result to 4 decimal pt.
            for (int i = 0; i < confMat.length; i++) {

                finalEp += epoch[i];

               //Summing up the accuracy rate of each category
                sumTstAcc += finalTestAcc[i];
                testFnlAccuracy = (sumTstAcc) * 100 / finalTestAcc.length;

                sumTrainAcc += finalTrainAcc[i];
                trainFnlAccuracy = (sumTrainAcc) * 100 / finalTrainAcc.length;

                avgAcc = (testFnlAccuracy + trainFnlAccuracy) / 2;

            }
            System.out.println("\nInitial Train Rate: " + decimal.format(rate));
            System.out.println("Final Epoch: " + decimal.format(finalEp));

            System.out.println("\nFinal Train Accuracy: " + decimal.format(trainFnlAccuracy));
            System.out.println("Final Test Accuracy: " + decimal.format(testFnlAccuracy));

            System.out.println("Average Accuracy: " + decimal.format(avgAcc));
            System.out.println("\n");
        }

    }

    /**
     * @param confMat the confusion matrix
     * @param nn Neural Network
     * @param utility Utility that contains data
     * @return the final training accuracy
     */
    public static double[] trainAccuracy(int[][] confMat, NeuralNetwork nn, Utility utility) {
        //Calculating the accuracy of the training data
        int trainTrueVals = 0;
        int trainTotal = 0;
        confMat = nn.testFunction(utility.getTraindata());
        double[] finalTrainAcc = new double[confMat.length];
        for (int i = 0; i < confMat.length; i++) {
        //Accuracy is calculated using the confusion matrix formula = (tp + tn) / (tp + tn + fp + fn)
            trainTrueVals = (confMat[i][0] + confMat[i][3]);
            trainTotal = (confMat[i][0] + confMat[i][1] + confMat[i][2] + confMat[i][3]);

            finalTrainAcc[i] = trainTrueVals * 1.0 / trainTotal;
        }
        return finalTrainAcc;
    }

    /**
     * @param confMat the confusion matrix
     * @param nn Neural Network
     * @param utility Utility that contains data
     * @return
     */
    public static double[] testAccuracy(int[][] confMat, NeuralNetwork nn, Utility utility) {
        //Calculating the accuracy of the test data
        int testTrueVals = 0;
        int testTotal = 0;
        confMat = nn.testFunction(utility.getTestdata());
        double[] finalTestAcc = new double[confMat.length];
        for (int i = 0; i < confMat.length; i++) {
        //Accuracy is calculated using the confusion matrix formula = (tp + tn) / (tp + tn + fp + fn)
            testTrueVals = (confMat[i][0] + confMat[i][3]);
            testTotal = (confMat[i][0] + confMat[i][1] + confMat[i][2] + confMat[i][3]);

            finalTestAcc[i] = testTrueVals * 1.0 / testTotal;
        }
        return finalTestAcc;
    }


}
