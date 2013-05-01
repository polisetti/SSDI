package prediction;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collections;

public class PredictionAlgorithm {
    private double beta0, beta1, ybar, xbar;
    public double predictedValue() {
        int MAXN = 1000;
        int n = 0;

        double[] x = new double[MAXN];
        double[] y = new double[MAXN];

        // first pass: read in data, compute xbar and ybar
        double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;

        try{
            String Filename = "C:\\Users\\owner\\Desktop\\FileAdapterOutput\\linearRegressionInput.txt";
            ArrayList<String> rows = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(new FileReader(Filename));
            String s;

            while((s = reader.readLine())!=null) {
                rows.add(s);
            }

            Collections.sort(rows);
            reader.close();

            FileWriter writer = new FileWriter(Filename);
            int counter = 0;
            for(String cur: rows) {
                writer.write(cur + "\n");
                counter++;
            }
            writer.close();
            
            String strLine;

            FileInputStream fstream = new FileInputStream(Filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while ((strLine = br.readLine()) != null) {
                String [] tokens = strLine.split(" ");
                x[n] = counter--;
                y[n] = Double.parseDouble(tokens[2]);
                sumx  += x[n];
                sumx2 += x[n] * x[n];
                sumy  += y[n];
                n++;
            }
            fstream.close();
            in.close();
            br.close();

            //Close the input stream
            in.close();

            this.xbar = sumx / n;
            this.ybar = sumy / n;

            // second pass: compute summary statistics
            double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
            for (int i = 0; i < n; i++) {
                xxbar += (x[i] - xbar) * (x[i] - xbar);
                yybar += (y[i] - ybar) * (y[i] - ybar);
                xybar += (x[i] - xbar) * (y[i] - ybar);
            }

            this.beta1 = xybar / xxbar;
            this.beta0 = this.ybar - this.beta1 * this.xbar;

            return this.beta0;
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
            return 0.0;
        }
    }

    public void setBeta0(double beta0) {
        this.beta0 = beta0;
    }

    public double getBeta0() {
        return beta0;
    }

    public void setXbar(double xbar) {
        this.xbar = xbar;
    }

    public double getXbar() {
        return xbar;
    }

    public void setBeta1(double beta1) {
        this.beta1 = beta1;
    }

    public double getBeta1() {
        return beta1;
    }

    public void setYbar(double ybar) {
        this.ybar = ybar;
    }

    public double getYbar() {
        return ybar;
    }
}
