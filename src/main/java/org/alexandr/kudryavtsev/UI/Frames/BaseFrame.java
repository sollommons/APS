package org.alexandr.kudryavtsev.UI.Frames;

import javax.swing.*;
import java.awt.*;

abstract class BaseFrame extends JFrame {

    public BaseFrame(String name) {
        super();
        settingFrame(name);
    }
    private void settingFrame(String name) {
        setVisible(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        setBounds(toolkit.getScreenSize().width/4, toolkit.getScreenSize().height/4, 400, 500);
        setTitle(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    abstract protected void addPanelToFrame();

}
