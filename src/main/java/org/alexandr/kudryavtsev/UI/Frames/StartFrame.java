package org.alexandr.kudryavtsev.UI.Frames;

import org.alexandr.kudryavtsev.Statistics.StatisticController;
import org.alexandr.kudryavtsev.System.GeneralSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
public class StartFrame extends BaseFrame{
    private final ArrayList<JTextField> startDataFields;
    private GeneralSystem generalSystem;
    private StatisticController statisticController;

    public StartFrame() {
        super("Начать");
        startDataFields = new ArrayList<>();
        addPanelToFrame();
    }

    public GeneralSystem getGeneralSystem() {
        return generalSystem;
    }

    public StatisticController getStatisticController() {
        return statisticController;
    }

    @Override
    protected void addPanelToFrame() {
        JPanel panel = new JPanel();
        add(panel);
        JLabel label1 = new JLabel("Количество приборов");
        label1.setPreferredSize(new Dimension(200, 40));
        JLabel label2 = new JLabel("Количество генераторов");
        label2.setPreferredSize(new Dimension(200, 40));
        JLabel label3 = new JLabel("Количество заявок");
        label3.setPreferredSize(new Dimension(200, 40));
        JLabel label4 = new JLabel("Размер буффера");
        label4.setPreferredSize(new Dimension(200, 40));
        JLabel label5 = new JLabel("Минимум времени обработки");
        label5.setPreferredSize(new Dimension(200, 40));
        JLabel label6 = new JLabel("Максимум времени обработки");
        label6.setPreferredSize(new Dimension(200, 40));
        JLabel label7 = new JLabel("Лямбда для распределения");
        label7.setPreferredSize(new Dimension(200, 40));
        panel.add(label1);
        JTextField text1 = new JTextField("3", 20);
        panel.add(text1);
        panel.add(label2);
        JTextField text2 = new JTextField("3", 20);
        panel.add(text2);
        panel.add(label3);
        JTextField text3 = new JTextField("2400", 20);
        panel.add(text3);
        panel.add(label4);
        JTextField text4 = new JTextField("3", 20);
        panel.add(text4);
        panel.add(label5);
        JTextField text5 = new JTextField("0.3", 20);
        panel.add(text5);
        panel.add(label6);
        JTextField text6 = new JTextField("0.5", 20);
        panel.add(text6);
        panel.add(label7);
        JTextField text7 = new JTextField("2.0", 20);
        panel.add(text7);
        JButton button = new JButton(new SetDataAction());
        button.setText("Начать");
        panel.add(button);
        setSize(500, 400);
        revalidate();
        startDataFields.add(text1);
        startDataFields.add(text2);
        startDataFields.add(text3);
        startDataFields.add(text4);
        startDataFields.add(text5);
        startDataFields.add(text6);
        startDataFields.add(text7);
    }

    class SetDataAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            generalSystem = new GeneralSystem(Integer.parseInt(startDataFields.get(1).getText()),
                    Integer.parseInt(startDataFields.get(0).getText()),
                    Integer.parseInt(startDataFields.get(3).getText()),
                    Double.parseDouble(startDataFields.get(6).getText()),
                    Double.parseDouble(startDataFields.get(5).getText()),
                    Double.parseDouble(startDataFields.get(4).getText()),
                    Integer.parseInt(startDataFields.get(2).getText()));
            statisticController = generalSystem.getStatisticController();
            setVisible(false);
            revalidate();
        }
    }

}
