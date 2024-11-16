package org.alexandr.kudryavtsev.Statistics;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class StatisticController {

    private final int processingDeviceCount;
    private final int homeDeviceCount;
    private final int countRequiredRequest;
    private final int sizeOfBuffer;
    private int countSubmittedRequest;
    private ArrayList<HomeDeviceStatistic> homeDeviceStatistics;
    private ArrayList<ProcessingDeviceStatistic> processingDeviceStatistics;

    public StatisticController(int homeDeviceCount,
                               int processingDeviceCount,
                               int sizeOfBuffer,
                               int countRequiredRequest) {
        this.homeDeviceCount = homeDeviceCount;
        this.processingDeviceCount = processingDeviceCount;
        this.sizeOfBuffer = sizeOfBuffer;
        this.countRequiredRequest = countRequiredRequest;
        this.countSubmittedRequest = 0;
        initHomeDeviceStatics(this.homeDeviceCount);
        initProcessingDeviceStatistics(this.processingDeviceCount);
    }

    private void initHomeDeviceStatics(int homeDeviceCount) {
        this.homeDeviceStatistics = new ArrayList<>(homeDeviceCount);
        for (int i = 0; i < homeDeviceCount; i++) {
            this.homeDeviceStatistics.add(new HomeDeviceStatistic());
        }
    }

    private void initProcessingDeviceStatistics(int processingDeviceCount) {
        this.processingDeviceStatistics = new ArrayList<>(processingDeviceCount);
        for (int i = 0; i < processingDeviceCount; i++) {
            this.processingDeviceStatistics.add(new ProcessingDeviceStatistic());
        }
    }

    public void addHomeRequest(int homeDeviceNum) {
        this.homeDeviceStatistics.get(homeDeviceNum).addCountAllGeneratedHomeRequests();
        this.countSubmittedRequest++;
    };

    public void cancelHomeRequest(int homeDeviceNum, double timeInSystem) {
        this.homeDeviceStatistics.get(homeDeviceNum).addCountCanceledHomeRequests();
        this.completeHomeRequest(homeDeviceNum, 0, timeInSystem, 0.0);
    };

    public void completeHomeRequest(int homeDeviceNum, int processingDeviceNum, double timeInSystem, double timeInDevice)
    {
        this.processingDeviceStatistics.get(processingDeviceNum).addWorkTime(timeInDevice);
        this.homeDeviceStatistics.get(homeDeviceNum).addTotalTime(timeInSystem);
        this.homeDeviceStatistics.get(homeDeviceNum).addBufferTime(timeInSystem - timeInDevice);
        this.homeDeviceStatistics.get(homeDeviceNum).addTimeInDevice(timeInDevice);
    };

    public long getHomeDeviceStatCancel(int homeDeviceNum) {
        return homeDeviceStatistics.get(homeDeviceNum).getCountAllCanceledHomeRequests();
    }

    public long getHomeDeviceStatAll (int homeDeviceCount) {
        return homeDeviceStatistics.get(homeDeviceCount).getCountAllGeneratedHomeRequests();
    }

    public double getTimeInSystem(int homeDeviceCount){
        return homeDeviceStatistics.get(homeDeviceCount).getTimeInSystem();
    }

    public double getTimeInDevice(int homeDeviceCount) {
        return homeDeviceStatistics.get(homeDeviceCount).getTimeInDevice();
    }

    public double getWorkTime(int processingDeviceCount) {
        return processingDeviceStatistics.get(processingDeviceCount).getWorkTime();
    }
}
