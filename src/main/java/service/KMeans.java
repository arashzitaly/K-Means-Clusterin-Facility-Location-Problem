package main.java.service;

import main.java.util.Util;

import java.sql.Timestamp;
import java.util.ArrayList;

public class KMeans {
    String log;
    private int clusterCount,numOfData;
    private static Data data0 = new Data(0,0, 0);
    private ArrayList<Data> dataList;
    private ArrayList<Cluster> clusters;
    private int repeatCount;
    private Data[] points;


    public KMeans(int numOfCluster, int numOfData) {
        this.clusterCount = numOfCluster;
        this.numOfData = numOfData;
    }

    public KMeansReport run() {
        KMeansReport kMeansReport = new KMeansReport(0,0);
        initData();
        initClusters();
        int time = 0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        time = (int)timestamp.getTime();
        repeatCount=0;

        while(true) {
            bindToClusters();
            updateClustersCenters();
            repeatCount++;
            if (repeatCount > 1000) {
                break;
            } else {
                if (!bindToClusters()) {
                    break;
                }
            }
        }
        timestamp = new Timestamp(System.currentTimeMillis());
        time = (int)timestamp.getTime() - time;
        kMeansReport.setRepeatCount(repeatCount);
        kMeansReport.setTime(time);
        return kMeansReport;
    }


    private void initData() {
        dataList = DataProvider.getInstance().getRandomDataList(numOfData);

    }


    private void initClusters() {
        clusters = new ArrayList<Cluster>();
        for(int i=0; i<clusterCount; i++)
            clusters.add(new Cluster(data0, i, new ArrayList<Data>()));

        points = Util.getPrimaryClustersCenters(dataList, clusterCount);
        int i=0;
        for(Cluster cluster:clusters) {
            cluster.setCenter(points[i]);
            i++;
        }
    }


    private boolean bindToClusters() {
        double min;
        double distance;
        boolean changed = false;
        Cluster minCluster;

        for(Cluster cluster:clusters) {
            cluster.setDataList(new ArrayList<Data>());
        }

        for(Data data:dataList) {
            min=-1;
            minCluster = clusters.get(0);
            for(Cluster cluster:clusters) {
                distance = Util.getDistance(data, cluster.getCenter());
                if(min==-1) min=distance;
                if(distance <= min) {
                    minCluster = cluster;
                    min=distance;
                }
            }
            minCluster.getDataList().add(data);
            if (data.updateClusterId(minCluster.getKey())) {
                changed = true;
            }
        }
        return changed;
    }


    private void updateClustersCenters() {
        points = Util.getAndUpdateClustersCenters(clusters);
    }


    public ArrayList<Data> getDataList() {
        return dataList;
    }

    public ArrayList<Cluster> getClusters() {
        return clusters;
    }

    public int getRepeatCount() {
        return repeatCount;
    }
}



