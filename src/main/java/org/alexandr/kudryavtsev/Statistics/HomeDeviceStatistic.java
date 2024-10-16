package org.alexandr.kudryavtsev.Statistics;

import lombok.Getter;

@Getter
public class HomeDeviceStatistic {

    private long countAllGeneratedHomeRequests;
    private long countAllCanceledHomeRequests;
    private double timeInSystem;
    private double timeInDevice;
    private double sumSquareTimeInDevice;
    private double timeInBuffer;
    private double sumSquareTimeInBuffer;

    public HomeDeviceStatistic() {
        this.timeInBuffer = 0;
        this.timeInSystem = 0;
        this.timeInDevice = 0;
        this.countAllCanceledHomeRequests = 0;
        this.countAllGeneratedHomeRequests = 0;
        this.sumSquareTimeInBuffer = 0;
        this.sumSquareTimeInDevice = 0;
    }

    public void addCountAllGeneratedHomeRequests() {
        this.countAllGeneratedHomeRequests++;
    }

    public void addCountCanceledHomeRequests() {
        this.countAllCanceledHomeRequests++;
    }

    public void addTotalTime(double time) {
        this.timeInSystem += time;
    }

    public void addBufferTime(double time) {
        this.timeInBuffer += time;
        this.sumSquareTimeInBuffer += time * time;
    }
    public void addTimeInDevice(double time) {
        this.timeInDevice += time;
        this.sumSquareTimeInDevice += time * time;
    }

    public double getBufferTimeDispersion() { return (this.sumSquareTimeInBuffer /
            this.countAllGeneratedHomeRequests - Math.pow(this.timeInBuffer / this.countAllGeneratedHomeRequests, 2)); }
    public double getDeviceTimeDispersion() { return (this.sumSquareTimeInDevice / this.countAllGeneratedHomeRequests
            - Math.pow(this.timeInDevice / this.countAllGeneratedHomeRequests, 2)); }

}
