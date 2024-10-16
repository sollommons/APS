package org.alexandr.kudryavtsev.UI.Panels;

import org.alexandr.kudryavtsev.System.Dates.Buffer;
import org.alexandr.kudryavtsev.System.Dates.HomeRequest;
import org.alexandr.kudryavtsev.System.Devices.ProcessingDevice;
import org.alexandr.kudryavtsev.System.GeneralSystem;
import org.alexandr.kudryavtsev.UI.Actions.NextStepAction;
import org.alexandr.kudryavtsev.UI.MainGUI;
import org.alexandr.kudryavtsev.Utils.Action;
import org.alexandr.kudryavtsev.Utils.ActionType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StepByStepPanel extends JPanel {
    private final MainGUI mainGUI;
    private final GeneralSystem generalSystem;
    public static String currentRequest;
    public static String oldestRequest;
    public static String completeRequest;
    public static int prevFirstFreeIndex;
    private final Font front1 = new Font("TimesRoman", Font.BOLD, 12);

    public StepByStepPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.generalSystem = mainGUI.getGeneralSystem();
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setFont(front1);
        this.setBackground(Color.WHITE);
        for (int i = 10; i <=60; i += 50) {
            graphics.drawRect(20, i, 100, 30);
            graphics.drawLine(120, i + 15, 150, i + 15);
        }
        for (int i = 115; i <=165; i+= 25) {
            graphics.drawOval(65, i, 1, 1);
        }
        graphics.drawRect(20, 190, 100, 30);
        graphics.drawString("И0", 30, 30);
        graphics.drawString("И1", 30, 80);
        graphics.drawString("И" + (generalSystem.getHomeDeviceCount() - 1), 30, 210);

        int dc = generalSystem.getProcessingDeviceCount();
        for (int i = 0; i < dc; i++) {
            graphics.drawRect(600, 10 + (i) * 50, 100, 30);
            graphics.drawLine(570, 25 + i * 50 , 600,25 + i * 50);
            graphics.drawLine(700, 25 + i * 50 , 730,25 + i * 50);
            graphics.drawChars(new char[]{'Д', String.valueOf(i).charAt(0)}, 0, 2, 610, 30 + (i) * 50);
        }
        graphics.drawRect(800, 110, 100, 30);
        graphics.drawLine(570, 25 + 50 * (dc - 1), 570, 25);
        graphics.drawLine(730, 25 + 50 * (dc - 1), 730, 25);
        graphics.drawLine(730, 125, 800, 125);
        graphics.drawString("Выход", 810, 127);

        int bs = generalSystem.getBufferSize();
        for (int i = 0; i < bs; i++) {
            graphics.drawRect(310, 10 + (i + 1) * 50, 100, 30);
            graphics.drawLine(360, 40 + i * 50 , 360,40 + i * 50 + 20);
            graphics.drawChars(new char[]{'Б', String.valueOf(i).charAt(0)}, 0, 2, 320, 30 + (i + 1) * 50);

        }

        graphics.drawLine(120, 25, 180, 25);
        graphics.drawLine(120, 195, 150, 195);
        graphics.drawLine(150, 195, 150, 25);

        graphics.drawRect(180, 10, 100, 30);
        graphics.drawLine(280, 25, 310, 25);

        graphics.drawRect(310, 10, 100, 30);
        graphics.drawLine(410, 25, 440, 25);

        graphics.drawRect(440, 10, 100, 30);
        graphics.drawLine(540, 25, 570, 25);

        graphics.drawRect(180, 60, 100, 30);
        graphics.drawLine(230, 40, 230, 60);

        graphics.drawString("ДП", 190, 30);
        graphics.drawString("БП", 320, 30);
        graphics.drawString("ДВ", 450, 30);

        graphics.drawString("Отказ", 190, 80);
        String time = String.valueOf(generalSystem.getTimeNow());
        try {
            time = time.substring(0, time.indexOf('.') + 4);
        } catch (Exception e) {

        }
        graphics.drawString("Время: " + time, 850,70);
    }

    @Override
    public void print(Graphics graphics)
    {
        graphics.setFont(front1);
        graphics.clearRect(0, 0, 1000, 400);
        this.paint(graphics);
        List<ProcessingDevice> d = generalSystem.getCompanySelectionManager().getProcessingDevices();
        Buffer buffer1 = generalSystem.getBuffer();
        Action action = new NextStepAction(mainGUI).action;

        if (action.getActionType() == ActionType.NEW_REQUEST) {
            if (prevFirstFreeIndex == -1) {
                graphics.setColor(Color.PINK);
                graphics.fillRect(181, 61, 99, 29);
                graphics.setColor(Color.black);
                graphics.drawString("Отказ " + oldestRequest, 190, 80);
            }
            graphics.setColor(Color.CYAN);
            if (buffer1.getFirstFreeIndex() == -1) {
                graphics.fillRect(311, 11 + (buffer1.getLastRequestIndex() + 1) * 50, 99, 29);

            } else {
                graphics.fillRect(311, 11 + (buffer1.getFirstFreeIndex()) * 50, 99, 29);
            }
            graphics.setColor(Color.black);
            graphics.drawString(currentRequest, 220, 30);
        }

        if (action.getActionType() == ActionType.REQUEST_COMPLETE) {
            graphics.setColor(Color.GREEN);
            graphics.fillRect(601, 11 + (action.getSourceOrDeviceNum()) * 50, 99, 29);
            graphics.setColor(Color.YELLOW);
            graphics.fillRect(801, 111, 98, 28);
            graphics.setColor(Color.BLACK);
            graphics.drawString("Д" + action.getSourceOrDeviceNum() + "   Свобод.", 610, 30 + ((action.getSourceOrDeviceNum()) * 50));
            graphics.drawString("Выход   " + completeRequest, 810, 127);
        }

        for (int i = 0; i < generalSystem.getBufferSize(); i++) {
            ArrayList<HomeRequest> buffer = buffer1.getBuffer();
            if (!(buffer.get(i) == null)) {
                String id = "Б" + i + "     " + buffer.get(i).getHomeDeviceNum() + "." + (buffer.get(i).getRequestNum());
                graphics.drawString(id, 320, 30 + (i + 1) * 50);
            }
            if (buffer1.getOldestRequestIndex() == i) {
                graphics.drawString("O", 390, 25 + (i + 1) * 50);
            }
            if (buffer1.getLastRequestIndex() == i) {
                graphics.drawString("L", 390, 35 + (i + 1) * 50);
            }
            if (buffer1.getFirstFreeIndex() == i) {
                graphics.drawString("FF", 390, 30 + (i + 1)* 50);
            }
        }

        for (int i = 0; i < generalSystem.getProcessingDeviceCount(); i++) {
            ProcessingDevice temp = d.get(i);
            if (!temp.isFree()) {
                graphics.drawString(temp.getHomeRequestNow().getHomeDeviceNum() + "." + temp.getHomeRequestNow().getRequestNum(), 650, 30 + (i) * 50);
            }
        }
    }
}