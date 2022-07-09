package main.java.service;

import main.java.util.Util;

import java.util.ArrayList;

public class Cluster {
    private Data center;
    private int key;
    private ArrayList<Data> dataList;

    public Cluster(Data center, int key, ArrayList<Data> dataList) {
        this.center = center;
        this.key = key;
        this.dataList = dataList;
    }

    public synchronized void addDataS(Data data) {
        this.dataList.add(data);
    }

    public void updateCenter() {
        Data newCenter;
        int i=-1;
        double sumX;
        double sumY;
        double M;
        sumX=0;
        sumY=0;
        M=0;
        for(Data data: this.dataList) {
            sumX = sumX + data.getX()*data.getWeight();
            sumY = sumY + data.getY()*data.getWeight();
            M = M + data.getWeight();
        }
        if (M == 0) {
            newCenter = new Data(Util.getRandomNumberD(35.68942368,35.70942368)  , Util.getRandomNumberD(51.36044205,51.40044205)  , 0);
        } else {
            newCenter = new Data(sumX/M, sumY/M, 0);
        }
        this.setCenter(newCenter);
    }



    public Data getCenter() {
        return center;
    }

    public void setCenter(Data center) {
        this.center = center;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public ArrayList<Data> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<Data> dataList) {
        this.dataList = dataList;
    }
}
