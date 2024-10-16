package org.alexandr.kudryavtsev.UI.Frames;

import org.alexandr.kudryavtsev.Statistics.HomeDeviceStatistic;
import org.alexandr.kudryavtsev.Statistics.ProcessingDeviceStatistic;
import org.alexandr.kudryavtsev.Statistics.StatisticController;
import org.alexandr.kudryavtsev.System.GeneralSystem;

import javax.swing.*;
import java.awt.*;

public class AutoModeFrame extends BaseFrame {

    private final GeneralSystem generalSystem;
    private final StatisticController statisticController;

    public AutoModeFrame(GeneralSystem generalSystem) {
        super("Автоматический режим");
        this.generalSystem = generalSystem;
        this.statisticController = generalSystem.getStatisticController();
        addPanelToFrame();
    }

    @Override
    protected void addPanelToFrame() {
        String[] columnNames1 = {"Источник", "Кол. заявок", "P(отк)", "Ср. t заявки в системе",
                "Ср. t ожид.", "Ср. t обслуж.", "Дисперсия t(ср.ож.)", "Дисперсия t(ср.об.)"};
        String[][] data1 = new String[Math.max(generalSystem.getHomeDeviceCount(), generalSystem.getProcessingDeviceCount())][8];
        for(int i = 0; i < generalSystem.getHomeDeviceCount(); i++) {
            if (i < generalSystem.getHomeDeviceCount()) {
                HomeDeviceStatistic homeDeviceStatistic = statisticController.getHomeDeviceStatistics().get(i);
                data1[i][0] = String.valueOf(i);
                data1[i][1] = String.valueOf(homeDeviceStatistic.getCountAllGeneratedHomeRequests());
                data1[i][2] = String.valueOf((double) homeDeviceStatistic.getCountAllCanceledHomeRequests()
                        / homeDeviceStatistic.getCountAllGeneratedHomeRequests());
                data1[i][3] = String.valueOf(homeDeviceStatistic.getTimeInSystem() / homeDeviceStatistic.getCountAllGeneratedHomeRequests());
                data1[i][4] = String.valueOf(homeDeviceStatistic.getTimeInBuffer() / homeDeviceStatistic.getCountAllGeneratedHomeRequests());
                data1[i][5] = String.valueOf(homeDeviceStatistic.getTimeInDevice() / homeDeviceStatistic.getCountAllGeneratedHomeRequests());
                data1[i][6] = String.valueOf(homeDeviceStatistic.getBufferTimeDispersion());
                data1[i][7] = String.valueOf(homeDeviceStatistic.getDeviceTimeDispersion());
            }
        }
        String[] columnNames2 = {"Прибор", "К(исп)"};
        String[][] data2 = new String[Math.max(generalSystem.getHomeDeviceCount(), generalSystem.getProcessingDeviceCount())][2];
        for(int i = 0; i < generalSystem.getProcessingDeviceCount(); i++) {
            ProcessingDeviceStatistic processingDeviceStatistic = statisticController.getProcessingDeviceStatistics().get(i);
            data2[i][0] = String.valueOf(i);
            data2[i][1] = String.valueOf(processingDeviceStatistic.getWorkTime() / generalSystem.getTimeNow());
        }
        JTable table1 = new JTable(data1, columnNames1);
        JScrollPane scroll1 = new JScrollPane(table1);
        table1.setPreferredScrollableViewportSize(new Dimension(700, 100));
        JTable table2 = new JTable(data2, columnNames2);
        JScrollPane scroll2 = new JScrollPane(table2);
        table2.setPreferredScrollableViewportSize(new Dimension(700, 100));
        JPanel temp = new JPanel(new GridLayout(2, 0));
        temp.add(scroll1);
        temp.add(scroll2);
        getContentPane().add(temp);
        setSize(1000, 500);
        revalidate();
    }

}
