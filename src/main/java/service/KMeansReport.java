package main.java.service;

public class KMeansReport {
    private int time;
    private int repeatCount;

    KMeansReport(int time, int repeatCount) {
        this.time = time;
        this.repeatCount = repeatCount;
    }

    public int getTime() {
        return time;
    }

    void setTime(int time) {
        this.time = time;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }
}
