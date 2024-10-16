package org.alexandr.kudryavtsev.Utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Action implements Comparable<Action>{

    private ActionType actionType;
    private double actionTime;
    private int sourceOrDeviceNum;

    public Action(ActionType actionType,
                  double actionTime) {
        this.actionTime = actionTime;
        this.actionType = actionType;
        this.sourceOrDeviceNum = -1;
    }

    public Action(ActionType actionType,
                  double actionTime,
                  int sourceOrDeviceNum) {
        this.actionTime = actionTime;
        this.actionType = actionType;
        this.sourceOrDeviceNum = sourceOrDeviceNum;
    }

    @Override
    public int compareTo(Action temp) {
        if (this.actionTime < temp.getActionTime()) {
            return -1;
        }
        if (this.actionTime > temp.getActionTime())  {
            return 1;
        }
        return this.actionType.compareTo(temp.getActionType());
    }
}
