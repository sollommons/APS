package org.alexandr.kudryavtsev.System.Devices;

import lombok.Getter;
import lombok.Setter;
import org.alexandr.kudryavtsev.System.Dates.HomeRequest;

@Getter
@Setter
public class HomeDevice {

    private final int sourceNum;
    private final double lambda;
    private int countGeneratedHomeRequest;

    public HomeDevice(int sourceNum,
                      double lambda) {
        this.sourceNum = sourceNum;
        this.lambda = lambda;
        this.countGeneratedHomeRequest = 0;
    }

    public HomeRequest getNewHomeRequest(double time) {
        HomeRequest homeRequest = new HomeRequest(this.sourceNum, this.countGeneratedHomeRequest, time);
        this.countGeneratedHomeRequest++;
        return homeRequest;
    }

    public double getTimeNextHomeRequest() {
        return getLaplassNumber();
    }

    private double getLaplassNumber() {
        double temp = 0.0;
        while (temp == 0.0) {
            temp = Math.random();
        }
        return -1.0/this.lambda * Math.log(temp);
    }
}
