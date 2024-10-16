package org.alexandr.kudryavtsev.UI.Actions;

import org.alexandr.kudryavtsev.System.GeneralSystem;
import org.alexandr.kudryavtsev.UI.MainGUI;
import org.alexandr.kudryavtsev.UI.Panels.StepByStepPanel;
import org.alexandr.kudryavtsev.Utils.Action;
import org.alexandr.kudryavtsev.Utils.ActionType;

import java.awt.*;
import java.awt.event.ActionEvent;

public class NextStepAction extends StartStepModeAction {
    public static Action action = null;
    static double time = 0;
    static int temp = 0;
    static String tempString;
    static ActionType prevActionType;
    private final GeneralSystem generalSystem;
    private final MainGUI mainGUI;

    public NextStepAction(MainGUI mainGUI) {
        super(mainGUI);
        this.mainGUI = mainGUI;
        this.generalSystem = mainGUI.getGeneralSystem();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (temp == 0) {
            action = generalSystem.startAction();
        }
        StepByStepPanel.prevFirstFreeIndex = generalSystem.getBuffer().getFirstFreeIndex();
        if (generalSystem.getBuffer().getOldestRequestIndex()!= -1) {
            StepByStepPanel.oldestRequest = generalSystem.getBuffer().getBuffer()
                    .get(generalSystem.getBuffer().getOldestRequestIndex()).getHomeDeviceNum() + "."
                    + generalSystem.getBuffer().getBuffer().get(generalSystem.getBuffer()
                    .getOldestRequestIndex()).getRequestNum();
        }
        if (generalSystem.getNextAction().getActionType() == ActionType.REQUEST_COMPLETE &&
                action.getActionType() != ActionType.REQUEST_COMPLETE) {
            StepByStepPanel.completeRequest = generalSystem.getCompanySelectionManager()
                    .getProcessingDevice(generalSystem.getNextAction().getSourceOrDeviceNum()).getHomeRequestNow()
                    .getHomeDeviceNum() + "." + generalSystem.getCompanySelectionManager()
                    .getProcessingDevice(generalSystem.getNextAction().getSourceOrDeviceNum()).getHomeRequestNow()
                    .getRequestNum();
        }
        if (action.getActionType() == ActionType.REQUEST_COMPLETE && prevActionType == ActionType.REQUEST_COMPLETE) {
            StepByStepPanel.completeRequest = tempString;
        }
        if (generalSystem.getNextAction().getActionType() == ActionType.REQUEST_COMPLETE) {
            tempString = generalSystem.getCompanySelectionManager()
                    .getProcessingDevice(generalSystem.getNextAction().getSourceOrDeviceNum()).getHomeRequestNow()
                    .getHomeDeviceNum() + "." + generalSystem.getCompanySelectionManager()
                    .getProcessingDevice(generalSystem.getNextAction().getSourceOrDeviceNum()).getHomeRequestNow()
                    .getRequestNum();
        }
        prevActionType = action.getActionType();
        if (temp != 0) {
            action = generalSystem.startAction();
        }
        if (action.getSourceOrDeviceNum() != -1 ) {
            StepByStepPanel.currentRequest = action.getSourceOrDeviceNum() + "." + (mainGUI.getStatisticController().getHomeDeviceStatistics()
                    .get(action.getSourceOrDeviceNum())
                    .getCountAllGeneratedHomeRequests() - 1);

        }
        temp++;
        time = generalSystem.getTimeNow();
        Graphics g = mainGUI.getStepModeFrame().getContentPane().getGraphics();
        mainGUI.getStepModeFrame().getContentPane().print(g);
    }
}