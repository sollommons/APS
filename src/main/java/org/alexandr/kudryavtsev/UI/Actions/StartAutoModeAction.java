package org.alexandr.kudryavtsev.UI.Actions;

import org.alexandr.kudryavtsev.UI.Frames.AutoModeFrame;
import org.alexandr.kudryavtsev.UI.MainGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StartAutoModeAction extends AbstractAction {
    private final MainGUI mainGUI;

    public StartAutoModeAction(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainGUI.getModeFrame().setVisible(false);
        mainGUI.getGeneralSystem().execute();
        new AutoModeFrame(mainGUI.getGeneralSystem());
    }
}