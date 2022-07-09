package main.java.service;

import main.java.util.Util;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

import static main.java.util.Util.getRandomNumberD;


class DataProvider {
    private static DataProvider instance = new DataProvider();

    private DataProvider() {
    }

    static DataProvider getInstance() {
        return instance;
    }


    private ArrayList<Data> getRealDataList() {
        ArrayList<Data> dataList = new ArrayList<>();

        try {
            String file = getClass().getClassLoader().getResource("GFG.csv").getFile();
            Scanner sc = new Scanner(new File(file));
            sc.useDelimiter("\n");
            while(sc.hasNext()) {
                String record = sc.next();
                String[] fields = record.split(",");
                dataList.add(new Data(Double.parseDouble(fields[0]),Double.parseDouble(fields[1]),
                        Double.parseDouble(fields[2])));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    ArrayList<Data> getRandomDataList(int numOfData) {
        ArrayList<Data> dataListReal = getRealDataList();
        ArrayList<Data> dataListRandom = new ArrayList<>();
        int dataListRealLen = dataListReal.size();
        double toleranceX = 0.002;
        double toleranceY = 0.004;
        double toleranceW = 20;
        int randReal, randReal2;
        double xRand, yRand;
        double[] signs;
        double[] signsW;

        for(int i=0; i<numOfData; i++) {
            randReal = Util.getRandomNumber(1,dataListRealLen);
            randReal2 = Util.getRandomNumber(1,dataListRealLen);

            signs =  getSigns();
            Data data = dataListReal.get(randReal-1);
            xRand = data.getX() + (signs[0] * getRandomNumberD(0, toleranceX)) ;
            yRand = data.getY() + (signs[1] * getRandomNumberD(0, toleranceY)) ;

            double weight = dataListReal.get(randReal2-1).getWeight();
            signsW = getSigns();
            weight = weight + (signsW[0] * getRandomNumberD(1, toleranceW)) ;
            if(weight <0) weight = -weight;

            dataListRandom.add(new Data(xRand, yRand, weight));
        }

        return dataListRandom;
    }

    private double[] getSigns() {
        double[] signs = new double[2];
        if (Util.getRandomNumber(1, 2) == 1) {
            signs[0] = 1d;
        } else {
            signs[0] = -1d;
        }
        if (Util.getRandomNumber(1, 2) == 1) {
            signs[1] = 1d;
        } else {
            signs[1] = -1d;
        }
        return signs;
    }

}
