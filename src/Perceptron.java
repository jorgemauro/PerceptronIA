import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;

class Perceptron
{
    static int MAX = 100;
    static double APRENDIZADO = 0.1;
    static int QTDINSTANCIA = 100;
    static int t = 0;
    public static void main(String args[]){
        //three variables (features)
        double[] x = new double [QTDINSTANCIA];
        double[] y = new double [QTDINSTANCIA];
        double[] z = new double [QTDINSTANCIA];
        int[] outputs = new int [QTDINSTANCIA];

        //fifty random points of class 1
        for(int i = 0; i < QTDINSTANCIA/2; i++){
            x[i] = nRandom(5 , 10);
            y[i] = nRandom(4 , 8);
            z[i] = nRandom(2 , 9);
            outputs[i] = 1;
            System.out.println(x[i]+"\t"+y[i]+"\t"+z[i]+"\t"+outputs[i]);
        }

        //fifty random points of class 0
        for(int i = 50; i < QTDINSTANCIA; i++){
            x[i] = nRandom(-1 , 3);
            y[i] = nRandom(-4 , 2);
            z[i] = nRandom(-3 , 5);
            outputs[i] = 0;
            System.out.println(x[i]+"\t"+y[i]+"\t"+z[i]+"\t"+outputs[i]);
        }

        double[] pesos = new double[4];// 3 for input variables and one for bias
        double localError, globalError;
        int i, p, intera, output;

        pesos[0] = nRandom(0,1);// w1
        pesos[1] = nRandom(0,1);// w2
        pesos[2] = nRandom(0,1);// w3
        pesos[3] = nRandom(0,1);// this is the bias

        intera = 0;
        do {
            intera++;
            globalError = 0;
            //loop through all instances (complete one epoch)
            for (p = 0; p < QTDINSTANCIA; p++) {
                // calculate predicted class
                output = calculaSaida(t,pesos, x[p], y[p], z[p]);
                // difference between predicted and actual class values
                localError = outputs[p] - output;
                //update pesos and bias
                pesos[0] += APRENDIZADO * localError * x[p];
                pesos[1] += APRENDIZADO * localError * y[p];
                pesos[2] += APRENDIZADO * localError * z[p];
                pesos[3] += APRENDIZADO * localError;
                //summation of squared error (error value for all instances)
                globalError += (localError*localError);
            }

			/* Root Mean Squared Error */
            System.out.println("intera "+intera+" : RMSE = "+Math.sqrt(globalError/QTDINSTANCIA));
        } while (globalError != 0 && intera<=MAX);

        System.out.println("\n=======\n Equação de fronteira de decisão:");
        System.out.println(pesos[0] +"*x + "+pesos[1]+"*y +  "+pesos[2]+"*z + "+pesos[3]+" = 0");

        //generate 10 new random points and check their classes
        //notice the range of -10 and 10 means the new point could be of class 1 or 0
        //-10 to 10 covers all the ranges we used in generating the 50 classes of 1's and 0's above
        for(int j = 0; j < 10; j++){
            double x1 = nRandom(-10 , 10);
            double y1 = nRandom(-10 , 10);
            double z1 = nRandom(-10 , 10);

            output = calculaSaida(t,pesos, x1, y1, z1);
            System.out.println("\n=======\n Novo Ponto:");
            System.out.println("x = "+x1+",y = "+y1+ ",z = "+z1);
            System.out.println("class = "+output);
        }
    }
    public static double nRandom(int min , int max) {
        DecimalFormat df = new DecimalFormat("#.####");
        NumberFormat f= NumberFormat.getInstance();
        double d = min + Math.random() * (max - min);
        String s = df.format(d);
        double x = 0;
        try {
            x = f.parse(s).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return x;
    }

    static int calculaSaida(int t, double pesos[], double x, double y, double z)
    {
        double sum = x * pesos[0] + y * pesos[1] + z * pesos[2] + pesos[3];
        return (sum >= t) ? 1 : 0;
    }

}