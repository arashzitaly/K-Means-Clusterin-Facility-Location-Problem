package main.java.service;

public class ReportData {

    private int numberOfData;
    private int numberOfClusters;
    private int runTime;
    private runMode runMode;

    public ReportData(int numberOfData, int numberOfClusters, int runTime, ReportData.runMode runMode) {
        this.numberOfData = numberOfData;
        this.numberOfClusters = numberOfClusters;
        this.runTime = runTime;
        this.runMode = runMode;
    }

    public int getNumberOfData() {
        return numberOfData;
    }

    public void setNumberOfData(int numberOfData) {
        this.numberOfData = numberOfData;
    }

    public int getNumberOfClusters() {
        return numberOfClusters;
    }

    public void setNumberOfClusters(int numberOfClusters) {
        this.numberOfClusters = numberOfClusters;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public ReportData.runMode getRunMode() {
        return runMode;
    }

    public void setRunMode(ReportData.runMode runMode) {
        this.runMode = runMode;
    }

    public enum  runMode {
        SEQUENTIAL,
        PARALLEL
    };


}
