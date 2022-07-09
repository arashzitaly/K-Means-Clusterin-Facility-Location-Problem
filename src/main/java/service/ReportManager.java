package main.java.service;

import main.java.ui.MainForm;
import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ReportManager {

    private ArrayList<ReportData> reportDataList;

    public ReportManager() {
        this.reportDataList = new ArrayList<>();
    }

    private ReportData runEnsemble(int numberOfData, int numberOfClusters, int ensumbles, ReportData.runMode runMode) {

        int repeatCountSum = 0;
        int timeSum = 0;

            for(int i = 0; i < ensumbles; i++) {
                if(runMode == ReportData.runMode.SEQUENTIAL) {
                    KMeans kmeans = new KMeans(numberOfClusters, numberOfData);
                    KMeansReport kMeansReport = kmeans.run();
                    timeSum += kMeansReport.getTime();
                    repeatCountSum += kMeansReport.getRepeatCount();
                } else {
                    KMeansMultyThread kmeans = new KMeansMultyThread(numberOfClusters, numberOfData, 8);
                    KMeansReport kMeansReport = kmeans.run();
                    timeSum += kMeansReport.getTime();
                    repeatCountSum += kMeansReport.getRepeatCount();
                }

            }
            timeSum = timeSum / ensumbles;
            repeatCountSum = repeatCountSum / ensumbles;

            ReportData reportData = new ReportData(numberOfData,numberOfClusters,timeSum,runMode);
            MainForm.getInstance().insertLog(String.format("time: %d (ms),    cluster: %d,  data: %d, repeats: %d",timeSum,numberOfClusters,numberOfData, repeatCountSum));

        return reportData;
    }

    public JPanel reportWithConstClusterNumber(int numberOfData, int numberOfClusters, int steps, int ensumbles, ReportData.runMode runMode) {

        int totalTimeLimit = 60 * 5 * 1000;
        int singleRunTimeLimit = 2 * 1000;
        int totalTime = 0;
        int initTime = 0;
        int pointLimit = 20;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        initTime = (int)timestamp.getTime();
        while (true) {
            ReportData reportData = runEnsemble(numberOfData, numberOfClusters, ensumbles, runMode);
            this.reportDataList.add(reportData);
            numberOfData+= steps;
            timestamp = new Timestamp(System.currentTimeMillis());
            totalTime = (int)timestamp.getTime() - initTime;
            if (reportData.getRunTime() >= singleRunTimeLimit ) {
                break;
            }
            if (totalTime >= totalTimeLimit ) {
                break;
            }
            pointLimit--;
            if (pointLimit == 0) {
                break;
            }
        }



        double x[] = getDataX();
        double y[] = getTime();

        return plot(x, y, "data");
    }


    public JPanel reportWithConstDataNumber(int numberOfData, int numberOfClusters, int steps, int ensumbles, ReportData.runMode runMode) {

        int totalTimeLimit = 60* 5 * 1000 ;
        int singleRunTimeLimit = 2 * 1000 * 60;
        int totalTime = 0;
        int initTime = 0;
        int pointLimit = 20;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        initTime = (int)timestamp.getTime();
        while (true) {
            ReportData reportData = runEnsemble(numberOfData, numberOfClusters, ensumbles, runMode);
            this.reportDataList.add(reportData);
            numberOfClusters+= steps;
            timestamp = new Timestamp(System.currentTimeMillis());
            totalTime = (int)timestamp.getTime() - initTime;
            if (reportData.getRunTime() >= singleRunTimeLimit ) {
                break;
            }
            if (totalTime >= totalTimeLimit ) {
                break;
            }
            pointLimit--;
            if (pointLimit == 0) {
                break;
            }
        }

        double x[] = getClustersX();
        double y[] = getTime();

        return plot(x, y,"clusters");

    }


    private JPanel plot(double[] x, double[] y, String text) {

        Plot2DPanel plot = new Plot2DPanel();
        plot.setFont(new Font("Tahoma",Font.BOLD,12));
        plot.setAxisLabels("number of "+ text, "mean running time(ms)");

        plot.addLinePlot("LP", x, y);

        plot.addScatterPlot("SP", Color.red,x,y);
        return plot;

    }

    private double[] getClustersX() {
        int size = this.reportDataList.size();
        double[] x = new double[size];
        int i = 0;
        for(ReportData reportData: this.reportDataList) {
            x[i]=(double)reportData.getNumberOfClusters();
            i++;
        }
        return x;
    }



    private double[] getDataX() {
        int size = this.reportDataList.size();
        double[] x = new double[size];
        int i = 0;
        for(ReportData reportData: this.reportDataList) {
            x[i]=(double)reportData.getNumberOfData();
            i++;
        }
        return x;
    }



    private double[] getTime() {
        int size = this.reportDataList.size();
        double[] y = new double[size];
        int i = 0;
        for(ReportData reportData: this.reportDataList) {
            y[i]=(double)reportData.getRunTime();
            i++;
        }
        return y;
    }

}
