package org.alexandr.kudryavtsev.System.Devices;
import lombok.Getter;
import org.alexandr.kudryavtsev.System.Dates.HomeRequest;

@Getter
public class ProcessingDevice {

    private final int deviceNum;
    private HomeRequest homeRequestNow;
    private final double minTime;
    private final double maxTime;
    private double startTimeHomeRequest;

    public ProcessingDevice(int deviceNum,
                            double minTime,
                            double maxTime) {
        this.deviceNum = deviceNum;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.homeRequestNow = null;
        this.startTimeHomeRequest = 0;
    }

    public double setHomeRequest(HomeRequest homeRequest,
                                 double startTimeHomeRequest) {
        this.startTimeHomeRequest = startTimeHomeRequest;
        this.homeRequestNow = homeRequest;
        return this.minTime + Math.random() * (this.maxTime - this.minTime);
    }

    public boolean isFree() {
        return this.homeRequestNow == null;
    }

}
