package main.java.service;

import main.java.util.Util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KMeansMultyThread {

    private int clusterCount,numOfData;
    private static Data data0 = new Data(0,0, 0);
    private ArrayList<Data> dataList;
    private ArrayList<Cluster> clusters;
    private int repeatCount;
    private Data[] points;
    private int numberOfThreads;
    private ExecutorService centerExecutor;
    private ExecutorService executor;


    public KMeansMultyThread(int numOfCluster, int numOfData, int numberOfThreads) {
        this.clusterCount = numOfCluster;
        this.numOfData = numOfData;
        this.numberOfThreads = numberOfThreads;
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
        this.centerExecutor = Executors.newFixedThreadPool(numOfCluster);

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
            if (repeatCount > 1000) {
                break;
            } else {
                if (!bindToClusters()) {
                    break;
                }
            }
            repeatCount++;
        }
        timestamp = new Timestamp(System.currentTimeMillis());
        time = (int)timestamp.getTime() - time;
        kMeansReport.setRepeatCount(repeatCount);
        kMeansReport.setTime(time);
        executor.shutdown();
        centerExecutor.shutdown();
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
        boolean changed = false;
        ArrayList<BindToClustersRunnable> bindToClustersRunnables = new ArrayList<>();
        for(Cluster cluster:clusters) {
            cluster.setDataList(new ArrayList<Data>());
        }
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for(int i=0; i< numberOfThreads ; i++) {
            int subListStart = dataList.size()  / numberOfThreads * i;
            int subListEnd = dataList.size()  / numberOfThreads * (i+1);
            bindToClustersRunnables.add(new BindToClustersRunnable(dataList, clusters, subListStart, subListEnd, latch));
            executor.execute(bindToClustersRunnables.get(i));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i=0; i< this.numberOfThreads ; i++) {
            if (bindToClustersRunnables.get(i).getChanged()) {
                changed =true;

            }
        }
        return changed;
    }


    private void updateClustersCenters() {
        CountDownLatch clusterLatch = new CountDownLatch(this.clusters.size());
        for (Cluster cluster: clusters) {
            centerExecutor.execute(new UpdateCentersRunnable(cluster,clusterLatch));
        }
        try {
            clusterLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    class UpdateCentersRunnable implements Runnable {
        Cluster cluster;
        CountDownLatch latch;
        public UpdateCentersRunnable(Cluster cluster, CountDownLatch latch) {
            this.cluster = cluster;
            this.latch = latch;
        }

        @Override
        public void run() {
            this.cluster.updateCenter();
            this.latch.countDown();
        }
    }


    class BindToClustersRunnable implements Runnable {
        ArrayList<Data> dataList;
        ArrayList<Cluster> clusters;
        private boolean changed = false;
        private int subListStart, subListEnd;
        CountDownLatch latch;

        public boolean getChanged() {
            return this.changed;
        }

        public BindToClustersRunnable(ArrayList<Data> dataList, ArrayList<Cluster> clusters, int subListStart, int subListEnd, CountDownLatch latch) {
            this.clusters = clusters;
            this.dataList = dataList;
            this.subListEnd = subListEnd;
            this.subListStart = subListStart;
            this.latch = latch;
        }

        @Override
        public void run() {
            double min;
            double distance;
            Cluster minCluster;
            this.changed = false;
            for(int i = this.subListStart; i < this.subListEnd; i++) {
                min=-1;
                minCluster = clusters.get(0);
                for(Cluster cluster:clusters) {
                    distance = Util.getDistance(dataList.get(i), cluster.getCenter());
                    if(min==-1) min=distance;
                    if(distance <= min) {
                        minCluster = cluster;
                        min=distance;
                    }
                }
                minCluster.addDataS(dataList.get(i));
                if (dataList.get(i).updateClusterId(minCluster.getKey())) {

                    this.changed = true;
                }

            }
            this.latch.countDown();
        }
    }
}



