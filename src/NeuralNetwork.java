
public class NeuralNetwork {
    private double rate;
    private double[][][] weight = new double[10][10][65];


    /**
     *
     * @param rate
     */
    public NeuralNetwork(double rate){
        this.rate=rate;
    }


    /**
     * Calculate the perceptron's output before signing
     * Using dot product distances to Activate Function
     * @param weight
     * @param row
     * @return
     */
    private double calcResult(double[] weight, int[] row) {
        double result = weight[0];
        for (int i = 0; i < 64; i++) {
            result += weight[i + 1] * row[i];
        }
        return result;
    }

    /**
     * Sign function for perceptron
     * @param perceptron
     * @return
     */
    private int sign(double perceptron) {
        if (perceptron > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * This function updates the weight dynamically accordingly
     * Default value does not change unless value is correct and weight it updated
     * The correlation coefficient checks for variation in the output data obtained
     * @param d1 first dimension
     * @param d2 second dimension
     * @param row full row values in the array
     * @return
     */
    private boolean updateWeight(int d1, int d2, int[] row) {
        boolean weighResult = false;

      //assigning rows sign based on the output
        int t;
        if (row[64] == d1) {
            t = 1;
        } else if (row[64] == d2) {
            t = -1;
        } else {
            t = 0;
        }

        double[] w = weight[d1][d2];
        int output = sign(calcResult(w, row));
        //checking variations
        double coeff = rate * (t - output);

       //updating weight
        w[0] += coeff;
        for (int correctVal = 0; correctVal < 64; correctVal++) {
            if ((t - output) * row[correctVal] > 0) {
                weighResult = true;
            }
            w[correctVal + 1] += coeff * row[correctVal];
        }
        return weighResult;
    }

    /**
     * Confusion Matrix Function
     * Compares the 64 of the test data to the guessed value
     * ML has correctly guessed the correct store value with the index of the row
     * @param d1 first dimension
     * @param d2 second dimension
     * @param testdata actual test data row and column
     * @return true positive, false negative, false positive and true negative
     */
    private int[][] confMatrix(int d1, int d2, int[][] testdata) {
        int truePositive = 0, falsePositive = 0, trueNegative = 0,falseNegative = 0;
        for (int i = 0; i < testdata.length; i++) {
            if (testdata[i][64] == d1) {
                double output = calcResult(weight[d1][d2], testdata[i]);
                if (sign(output) == 1) {
                    truePositive++;
                } else {
                    falseNegative++;
                }
            } else if (testdata[i][64] == d2) {
                double output = calcResult(weight[d1][d2], testdata[i]);
                if (sign(output) == 1) {
                    falsePositive++;
                } else {
                    trueNegative++;
                }
            }
        }

        return new int[][] { { truePositive, falseNegative }, { falsePositive, trueNegative } };
    }

    /**
     *
     * Trains a single perceptron
     * @param d1 first dimension
     * @param d2 second dimension
     * @param traindata training data row and column
     * @return 1 if d1 is true else d2=-1
     */

    private int trainSinglePerceptron(int d1, int d2, int[][] traindata) {

        //distribution of weight between -1 and 1
        for (int i = 0; i < 65; i++) {
            weight[d1][d2][i] = Math.random() * 2 - 1;
        }

        // Results are compared with the result obtained by the machine and weight is updated
        int epoch = 0;
        while (true) {
            int[][] conf = confMatrix(d1, d2, traindata);
            double old_tptn = (conf[0][0] + conf[1][1]);
            double totalOldPN = (conf[0][0] + conf[0][1] + conf[1][0] + conf[1][1]);

            double oldacc = old_tptn * 1.0 / totalOldPN;
            for (int i = 0; i < traindata.length; i++) {
                if (traindata[i][64] == d1 || traindata[i][64] == d2) {
                    updateWeight(d1, d2, traindata[i]);
                }
            }
            conf = confMatrix(d1, d2, traindata);

            double new_tptn = (conf[0][0] + conf[1][1]);
            double totalNewPN = (conf[0][0] + conf[0][1] + conf[1][0] + conf[1][1]);

            double newacc = new_tptn * 1.0 / totalNewPN;
            if (newacc <= oldacc) {
                break;
            }
            epoch++;
        }

        return epoch;
    }

    /**
     * Training all perceptron with the training data
     * @param traindata
     * @return
     */
    public int[][] train(int[][] traindata) {
        int[][] epoch = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = i + 1; j < 10; j++) {
                epoch[i][j] = trainSinglePerceptron(i, j, traindata);
            }
        }

        return epoch;
    }


    /**
     * Testing all perceptrons with the whole test data and returns confusion matrix
     * @param testdata
     * @return confusion matrix
     */
    public int[][] testFunction(int[][] testdata) {
        int[][] confMat = new int[45][4];
        int count = 0;
        for (int row = 0; row < 10; row++) {
            for (int column = row + 1; column < 10; column++) {
                int[][] conf = confMatrix(row, column, testdata);

                confMat[count][0] = conf[0][0];
                confMat[count][1] = conf[0][1];
                confMat[count][2] = conf[1][0];
                confMat[count][3] = conf[1][1];
                count++;

            }
        }

        return confMat;
    }


}
