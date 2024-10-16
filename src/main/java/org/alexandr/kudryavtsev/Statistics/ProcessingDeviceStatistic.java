package org.alexandr.kudryavtsev.Statistics;

import lombok.Getter;

@Getter
public class ProcessingDeviceStatistic {

    private double workTime;

    public ProcessingDeviceStatistic() {
        this.workTime = 0;
    }

    public void addWorkTime(double time) {
        workTime += time;
    }

}
