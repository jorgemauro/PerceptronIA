

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Perceptron {

    private double[] w = new double[3];

    private double NET = 0;

    private final int epocasMax = 1000;

    private int count = 0;

    private int[][] matrixLearning = new int[4][3];

    public int getCount(){

        return this.count;

    }
    Perceptron( int number) {
        if(number ==1) {//or
            this.matrixLearning[0][0] = 0;
            this.matrixLearning[0][1] = 0;
            this.matrixLearning[0][2] = 0;

            this.matrixLearning[1][0] = 0;
            this.matrixLearning[1][1] = 1;
            this.matrixLearning[1][2] = 1;

            this.matrixLearning[2][0] = 1;
            this.matrixLearning[2][1] = 0;
            this.matrixLearning[2][2] = 1;

            this.matrixLearning[3][0] = 1;
            this.matrixLearning[3][1] = 1;
            this.matrixLearning[3][2] = 1;
        }else if(number ==2) {//and
            this.matrixLearning[0][0] = 0;
            this.matrixLearning[0][1] = 0;
            this.matrixLearning[0][2] = 0;

            this.matrixLearning[1][0] = 0;
            this.matrixLearning[1][1] = 1;
            this.matrixLearning[1][2] = 0;

            this.matrixLearning[2][0] = 1;
            this.matrixLearning[2][1] = 0;
            this.matrixLearning[2][2] = 0;

            this.matrixLearning[3][0] = 1;
            this.matrixLearning[3][1] = 1;
            this.matrixLearning[3][2] = 1;
        }else if(number ==3) { //xor
            this.matrixLearning[0][0] = 0;
            this.matrixLearning[0][1] = 0;
            this.matrixLearning[0][2] = 0;

            this.matrixLearning[1][0] = 0;
            this.matrixLearning[1][1] = 1;
            this.matrixLearning[1][2] = 1;

            this.matrixLearning[2][0] = 1;
            this.matrixLearning[2][1] = 0;
            this.matrixLearning[2][2] = 1;

            this.matrixLearning[3][0] = 1;
            this.matrixLearning[3][1] = 1;
            this.matrixLearning[3][2] = 0;
        }
        w[0] = 0;
        w[1] = 0;
        w[2]= 0;

    }

    int executar(int x1, int x2) {

        NET = (x1 * w[0]) + (x2 * w[1]) + ((-1) * w[2]);

        if (NET >= 0) {
            return 1;
        }
        return 0;
    }

    public void doTraining() {

        boolean training= true;
        int out;

        for (int i = 0; i < matrixLearning.length; i++) {
            out = executar(matrixLearning[i][0], matrixLearning[i][1]);


            if (out != matrixLearning[i][2]) {
                correct(i, out);
                training = false;
            }
        }
        this.count++;

        if((!training) && (this.count < this.epocasMax)) {
            if(this.count==this.epocasMax-1){
                System.out.println("cheguei no maximo de interações");
            }
            doTraining();


        }

    }


    private void correct(int i, int out) {

        w[0] = w[0] + ((matrixLearning[i][2] - out) * matrixLearning[i][0]);
        w[1] = w[1] + ((matrixLearning[i][2] - out) * matrixLearning[i][1]);
        w[2] = w[2] + ((matrixLearning[i][2] - out) * (-1));
    }
    private static int[] tranformaLinha(String linha){
        String[]s=linha.split(" ");
        int[] retorno= new int[s.length];
        for(int i=0;i<s.length;i++){
            retorno[i]=Integer.parseInt(s[i]);
        }
        return retorno;
    }
    public static void main(String[] args) {
     Perceptron p;
        String linha;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("file.txt"));
            int j=0;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
                String linhaLO=linha.toLowerCase();
                if(linhaLO.toLowerCase().equals("and")){
                    linha=br.readLine();
                    p=new Perceptron(2);
                }else if(linhaLO.equals("or")){
                    linha=br.readLine();
                    p=new Perceptron(1);
                }else if(linha.equals("xor")){
                    linha= br.readLine();
                    p=new Perceptron(3);
                }else{
                    System.out.println("entrada invalida");
                    break;
                }
                p.doTraining();
                int[] analys=tranformaLinha(linha);
                int exec=-1;
                for(int i=0;i<analys.length;i++){
                    if(exec==-1){
                    exec=p.executar(analys[i],analys[i+1]);
                    i=1;
                    }else{
                        exec=p.executar(exec,analys[i]);
                    }
                }
                System.out.println("round"+j+": "+exec);
                j++;
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}