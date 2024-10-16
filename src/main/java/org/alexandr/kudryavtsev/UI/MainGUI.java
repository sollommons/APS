package org.alexandr.kudryavtsev.UI;

import lombok.Getter;
import lombok.Setter;
import org.alexandr.kudryavtsev.Statistics.StatisticController;
import org.alexandr.kudryavtsev.System.GeneralSystem;
import org.alexandr.kudryavtsev.UI.Frames.ModeFrame;
import org.alexandr.kudryavtsev.UI.Frames.StartFrame;
import javax.swing.*;
@Getter
@Setter
public class MainGUI {

    private StartFrame startFrame;
    private ModeFrame modeFrame;
    private JFrame stepModeFrame;
    private GeneralSystem generalSystem;
    StatisticController statisticController;

    public void execute() throws InterruptedException {
        startFrame = new StartFrame();
        while (startFrame.isVisible()) {
            Thread.sleep(100);
        }
        generalSystem = startFrame.getGeneralSystem();
        statisticController = startFrame.getStatisticController();
        modeFrame = new ModeFrame(this);
    }

}