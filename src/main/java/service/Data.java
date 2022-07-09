package main.java.service;

public class Data {
    private double x; // latitude
    private double y; // longitude
    private int clusterId;
    private double weight;

    public Data(double x, double y, double weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.clusterId = -1;
    }


    public int getClusterId() {
        return this.clusterId;
    }

    private void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    boolean updateClusterId(int clusterId) {
        boolean changedClusterId = (clusterId != this.clusterId);
        setClusterId(clusterId);
        return changedClusterId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
