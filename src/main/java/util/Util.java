package main.java.util;

import main.java.service.Cluster;
import main.java.service.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Util {

    public static int getRandomNumber(int first, int last) {
        double scope = (last - first) + 1;
        double rnd = Math.random() * scope + first ;
        return (int) rnd;
    }

    public static double getRandomNumberD(double first, double last) {
        double scope = (last - first);
        return Math.random() * scope + first ;
    }

    public static Data[] getPrimaryClustersCenters(ArrayList<Data> dataList, int PointCount) {

        Random rand = new Random();
        dataList.get(rand.nextInt(dataList.size()));
        Data[] points = new Data[PointCount];

        for(int i=0; i<PointCount; i++) {
            Data  point = dataList.get(rand.nextInt(dataList.size()));

            points[i]=point;
        }
        return points;
    }

    public static Data[] getAndUpdateClustersCenters(ArrayList<Cluster> clusters) {
        Data[] points = new Data[clusters.size()];
        int i=-1;
        double sumX;
        double sumY;
        double M;
        for(Cluster cluster:clusters) {
            i++;
            sumX=0;
            sumY=0;
            M=0;
            for(Data data: cluster.getDataList()) {
                sumX = sumX + data.getX()*data.getWeight();
                sumY = sumY + data.getY()*data.getWeight();
                M = M + data.getWeight();
            }
            if (M == 0) {
                Random rand = new Random();
                points[i] = new Data(getRandomNumberD(35.68942368,35.70942368)  , getRandomNumberD(51.36044205,51.40044205)  , 0);
            } else {
                points[i] = new Data(sumX/M, sumY/M, 0);
            }
            cluster.setCenter(points[i]);
        }
        return points;
    }

    public static double getDistance(Data data1, Data data2) {
        return Math.sqrt(Math.pow((data1.getX() - data2.getX()),2) + Math.pow((data1.getY() - data2.getY()),2));
    }

    public static Color getColor(int n) {

        Integer nn = (n * 1001) % 360;
        float hue = nn.floatValue()/360;
        return Color.getHSBColor(hue,(float)0.99,(float) 0.99);
    }
}
