package org.alexandr.kudryavtsev.System.Managers;

import org.alexandr.kudryavtsev.Statistics.StatisticController;
import org.alexandr.kudryavtsev.System.Dates.Buffer;
import org.alexandr.kudryavtsev.System.Dates.HomeRequest;
import org.alexandr.kudryavtsev.System.Devices.HomeDevice;

import java.util.ArrayList;

public class CompanyStagingManager {

    private final Buffer buffer;
    private final int homeDeviceCount;
    private ArrayList<HomeDevice> homeDevices;
    private StatisticController statisticController;

    public CompanyStagingManager(Buffer buffer,
                                 int homeDeviceCount,
                                 double lambda,
                                 StatisticController statisticController) {
        this.buffer = buffer;
        this.homeDeviceCount = homeDeviceCount;
        initArrayOfDevice(lambda);
        this.statisticController = statisticController;
    }

    private void initArrayOfDevice(double lambda) {
        homeDevices = new ArrayList<>(homeDeviceCount);
        for (int i = 0; i < homeDeviceCount; i++) {
            homeDevices.add(new HomeDevice(i, lambda));
        }
    }

    public void addHomeRequestInBuffer(HomeRequest homeRequest) {
        if (buffer.getFirstFreeIndex() == -1) {
            HomeRequest homeRequest1 = buffer.getBuffer().get(buffer.getOldestRequestIndex());
            statisticController.cancelHomeRequest(homeRequest1.getHomeDeviceNum(),
                    homeRequest.getGeneratedTime() - homeRequest1.getGeneratedTime());
            buffer.getBuffer().set(buffer.getOldestRequestIndex(), homeRequest);
            //buffer.setLastRequestIndex(buffer.getOldestRequestIndex());
            buffer.setNewOldestRequestIndex();
            return;
        }
        buffer.getBuffer().set(buffer.getFirstFreeIndex(), homeRequest);
        //buffer.setLastRequestIndex(buffer.getFirstFreeIndex());
        buffer.setNewOldestRequestIndex();
        buffer.setNewFirstFreeIndex();
    }

    public HomeDevice getHomeDevice(int i) {
        return this.homeDevices.get(i);
    }

}
