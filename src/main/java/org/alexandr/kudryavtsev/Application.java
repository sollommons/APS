package org.alexandr.kudryavtsev;


import org.alexandr.kudryavtsev.UI.MainGUI;

public class Application {
    public static void main(String[] args) {
        MainGUI mainGUI = new MainGUI();
        try {
            mainGUI.execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
