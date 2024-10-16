package org.alexandr.kudryavtsev.UI.Actions;

import org.alexandr.kudryavtsev.UI.Frames.StepModeFrame;
import org.alexandr.kudryavtsev.UI.MainGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StartStepModeAction extends AbstractAction {

    private final MainGUI mainGUI;

    public StartStepModeAction(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainGUI.getModeFrame().setVisible(false);
        mainGUI.setStepModeFrame(new StepModeFrame(mainGUI));
        mainGUI.getStepModeFrame().setSize(1000, 300);
    }
}