package org.alexandr.kudryavtsev.System;

import lombok.Getter;
import lombok.Setter;
import org.alexandr.kudryavtsev.Statistics.StatisticController;
import org.alexandr.kudryavtsev.System.Dates.Buffer;
import org.alexandr.kudryavtsev.System.Dates.HomeRequest;
import org.alexandr.kudryavtsev.System.Devices.ProcessingDevice;
import org.alexandr.kudryavtsev.System.Managers.CompanySelectionManager;
import org.alexandr.kudryavtsev.System.Managers.CompanyStagingManager;
import org.alexandr.kudryavtsev.Utils.Action;
import org.alexandr.kudryavtsev.Utils.ActionType;

import java.util.ArrayList;
@Getter
@Setter
public class GeneralSystem {

    private final StatisticController statisticController;
    private int homeDeviceCount;
    private int processingDeviceCount;
    private double timeNow;
    private Buffer buffer;
    private CompanySelectionManager companySelectionManager;
    private CompanyStagingManager companyStagingManager;
    private ArrayList<Action> actions;
    private double lambda;
    private double minTime;
    private double maxTime;
    private int bufferSize;
    private ArrayList<Double> genTime;
    private ArrayList<Double> finishTime;
    private ArrayList<Integer> allRequests;
    private ArrayList<Integer> outRequests;

    public GeneralSystem(int homeDeviceCount,
                         int processingDeviceCount,
                         int bufferSize,
                         double lambda,
                         double maxTime,
                         double minTime,
                         int countRequiredRequest) {
        this.statisticController = new StatisticController(homeDeviceCount, processingDeviceCount, bufferSize, countRequiredRequest);
        this.homeDeviceCount = homeDeviceCount;
        this.processingDeviceCount = processingDeviceCount;
        this.lambda = lambda;
        this.maxTime = maxTime;
        this.minTime = minTime;
        this.timeNow = 0;
        this.bufferSize = bufferSize;
        this.buffer = new Buffer(bufferSize);
        this.companySelectionManager = new CompanySelectionManager(this.buffer, processingDeviceCount, this.minTime, this.maxTime);
        this.companyStagingManager = new CompanyStagingManager(this.buffer, homeDeviceCount, this.lambda, this.statisticController);
        this.actions = new ArrayList<>(homeDeviceCount);
        this.genTime = new ArrayList<>(homeDeviceCount);
        this.finishTime = new ArrayList<>(processingDeviceCount);
        double time = 0;
        for (int i = 0; i < homeDeviceCount; i++) {
            time = companyStagingManager.getHomeDevice(i).getTimeNextHomeRequest();
            actions.add(new Action(ActionType.NEW_REQUEST,
                        time, i));
            genTime.add(i, time);
        }
//        for (int i = 0; i < homeDeviceCount; i++) {
//            System.out.println("Генератор " + i + ": " + genTime.get(i));
//        }
        for (int i = 0; i < processingDeviceCount; i++) {
            finishTime.add(i, 0.0);
        }
        System.out.println("\n");
        actions.sort(Action::compareTo);
    }

    public void execute() {
        while (!actions.isEmpty()) {
            startAction();
        }
    }

    public Action getNextAction() {
        return actions.get(0);
    }

    public Action startAction() {
        Action action = actions.remove(0);
        this.timeNow = action.getActionTime();
        ActionType actionType = action.getActionType();
        int sourceOrDeviceNum = action.getSourceOrDeviceNum();
        double refusedTime = 0;
        if (actionType == ActionType.NEW_REQUEST) {

            if (statisticController.getCountSubmittedRequest() < statisticController.getCountRequiredRequest()) {
                double time = this.timeNow +
                        companyStagingManager.getHomeDevice(sourceOrDeviceNum).getTimeNextHomeRequest();
                genTime.set(sourceOrDeviceNum, time);
                System.out.println("\n");
                HomeRequest homeRequest = companyStagingManager.getHomeDevice(sourceOrDeviceNum).getNewHomeRequest(this.timeNow);
                companyStagingManager.addHomeRequestInBuffer(homeRequest);
                actions.add(new Action(ActionType.NEW_REQUEST, time, sourceOrDeviceNum));

                if (companySelectionManager.findFreeProcessingDevice() != -1) {
                        actions.add(new Action(ActionType.REQUEST_OUT_BUFFER, this.timeNow));
                }
                statisticController.addHomeRequest(sourceOrDeviceNum);
                actions.sort(Action::compareTo);
            }
        }
        else if (actionType == ActionType.REQUEST_OUT_BUFFER) {
            int freeDeviceID = companySelectionManager.findFreeProcessingDevice();
            if (!buffer.isEmpty()) {
                ProcessingDevice currentProcessingDevice = companySelectionManager.getProcessingDevice(freeDeviceID);
                companySelectionManager.getProcessingDevices().set(freeDeviceID, null);
                HomeRequest homeRequest = companySelectionManager.getHomeRequest(freeDeviceID);
                double finTime = this.timeNow + currentProcessingDevice.setHomeRequest(homeRequest, this.timeNow);
                finishTime.set(freeDeviceID, finTime);
                actions.add(new Action(ActionType.REQUEST_COMPLETE, finTime, currentProcessingDevice.getDeviceNum()));
                companySelectionManager.getProcessingDevices().set(freeDeviceID, currentProcessingDevice);
                actions.sort(Action::compareTo);
            }
        }
        else if (actionType == ActionType.REQUEST_COMPLETE) {
            ProcessingDevice currentProcessingDevice = companySelectionManager.getProcessingDevice(sourceOrDeviceNum);
            companySelectionManager.getProcessingDevices().set(sourceOrDeviceNum, null);
            statisticController.completeHomeRequest(currentProcessingDevice.getHomeRequestNow().getHomeDeviceNum(), sourceOrDeviceNum,
                    this.timeNow - currentProcessingDevice.getHomeRequestNow().getGeneratedTime(),
                    this.timeNow - currentProcessingDevice.getStartTimeHomeRequest());
            currentProcessingDevice.setHomeRequest(null, 0);
            finishTime.set(sourceOrDeviceNum,0.0);
            companySelectionManager.getProcessingDevices().set(sourceOrDeviceNum, currentProcessingDevice);
            actions.add(new Action(ActionType.REQUEST_OUT_BUFFER, this.timeNow));
            actions.sort(Action::compareTo);
        }
        for (int i = 0; i < homeDeviceCount; i++) {
            System.out.println("Генератор " + i + ": " + genTime.get(i));
        }
        System.out.println("\n");
        return action;
    }

    public double printGenTime(int i) {
        return genTime.get(i);
    }

    public double printFinTime(int i) {
        return finishTime.get(i);
    }

    public long getCancelFromHomeDevice (int homeDeviceNum) {
        return companyStagingManager.getCancelRequest(homeDeviceNum);
    }
    public long getAllReqFromDevice (int homeDeviceNum) {
        return companyStagingManager.getAllRequests(homeDeviceNum);
    }

    public double getTimeInSystem(int homeDeviceCount){
        return statisticController.getTimeInSystem(homeDeviceCount);
    }

    public double getTimeInDevice(int homeDeviceCount) {
        return statisticController.getTimeInDevice(homeDeviceCount);
    }

    public double getWorkTime(int processingDeviceCount) {
        return statisticController.getWorkTime(processingDeviceCount);
    }

}
