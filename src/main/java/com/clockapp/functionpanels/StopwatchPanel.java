package com.clockapp.functionpanels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.ArrayList;

import static com.clockapp.ClockUtil.convertDurationToString;
import static com.clockapp.ClockUtil.convertTime;

public class StopwatchPanel extends JPanel {
    private long lastWatchTime;
    private long timePassed;
    private long timePaused;
    private boolean isPaused = false;
    private int lapCount = 1;
    private int fastestLap = 0;
    private int slowestLap = 0;
    private java.util.List<String[]> lapRecords = new ArrayList<>();
    public StopwatchPanel() {
        init();
    }

    private void init() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel stopwatch = new JLabel(String.format("%02d:%02d:%02d:%02d", 0, 0, 0, 0));
        stopwatch.setFont(new Font("Arial", Font.BOLD, 48));
        stopwatch.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        this.add(stopwatch, gbc);

        JPanel controlPanel = new JPanel(new GridLayout(1, 0, 10, 0));
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(controlPanel, gbc);

        Timer timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timePassed = System.currentTimeMillis() - lastWatchTime;
                Duration duration = Duration.ofMillis(timePassed);
                if (isPaused) {
                    duration = duration.plusMillis(timePaused);
                }
                String durationToString = convertDurationToString(duration);
                stopwatch.setText(durationToString);
            }
        });

        JButton startButton = new JButton("Start");
        controlPanel.add(startButton);

        // lap button should be disabled unless the timer is running
        JButton lapButton = new JButton("Lap");
        lapButton.setEnabled(false);
        controlPanel.add(lapButton);

        JButton resetButton = new JButton("Reset");
        controlPanel.add(resetButton);

        // table to contain lap times
        String[] columnNames = {"Laps", "Fastest/Slowest", "Time", "Total"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        table.setForeground(Color.BLUE); // Set text color
        table.setBackground(Color.LIGHT_GRAY); // Set background color
        table.setEnabled(false);

        // Add JTextArea to a JScrollPane (optional)
        JScrollPane scrollPane = new JScrollPane(table);

        // Set GridBagConstraints for the JTextArea
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 50, 20, 50); // Add padding
        this.add(scrollPane, gbc);

        // logic for start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startButton.getText().equals("Start")) {
                    lastWatchTime = System.currentTimeMillis();
                    timer.start();
                    lapButton.setEnabled(true);
                    startButton.setText("Pause");
                } else if (startButton.getText().equals("Pause")) {
                    timer.stop();
                    startButton.setText("Start");
                    timePaused += timePassed;
                    isPaused = true;
                    lapButton.setEnabled(false);
                } else {
                    // Should not enter this block
                    System.err.println("Start/Stop button error");
                }
            }
        });

        // logic for reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                startButton.setText("Start");
                isPaused = false;
                lapButton.setEnabled(false);
                timePaused = 0;
                timePassed = 0;
                stopwatch.setText(String.format("%02d:%02d:%02d:%02d", 0, 0, 0, 0));
                model.setRowCount(0);
                lapRecords = new ArrayList<>();
                lapCount = 1;
            }
        });

        // logic for lap button
        lapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lapTime = stopwatch.getText();
                long currLapTime = convertTime(lapTime);
                long prevLapTime = lapRecords.isEmpty() ?  0 : convertTime(lapRecords.get(lapRecords.size() - 1)[3]);
                long split = currLapTime - prevLapTime;
                Duration splitDuration = Duration.ofMillis(split);
                String splitString = convertDurationToString(splitDuration);
                String[] currLap = {String.valueOf(lapCount), "", splitString, lapTime};
                lapCount++;
                lapRecords.add(currLap);
                updateLapRecords(lapRecords);

                model.setRowCount(0);
                for(String[] record : lapRecords) {
                    model.insertRow(0, record);
                }
            }
        });
    }

    private void updateLapRecords(java.util.List<String[]> lapRecords) {
        if (lapRecords.size() <= 1) {
            // do nothing, there is not fastest or slowest lap if there is only 1 lap
        } else if (lapRecords.size() == 2) {
            String[] firstRecord = lapRecords.get(0);
            String[] secondRecord = lapRecords.get(1);
            long firstLapTime = convertTime(firstRecord[2]);
            long secondLapTime = convertTime(secondRecord[2]);
            if (firstLapTime >= secondLapTime) {
                firstRecord[1] = "Slowest";
                secondRecord[1] = "Fastest";
                fastestLap = 1;
                slowestLap = 0;
            } else {
                firstRecord[1] = "Fastest";
                secondRecord[1] = "Slowest";
                fastestLap = 0;
                slowestLap = 1;
            }
            lapRecords.set(0, firstRecord);
            lapRecords.set(1, secondRecord);
        } else {
            int latestRecordIndex = lapRecords.size() - 1;
            String[] latestRecord = lapRecords.get(latestRecordIndex);
            String[] fastestRecord = lapRecords.get(fastestLap);
            String[] slowestRecord = lapRecords.get(slowestLap);
            long latestLapTime = convertTime(latestRecord[2]);
            long fastestLapTime = convertTime(fastestRecord[2]);
            long slowestLapTime = convertTime(slowestRecord[2]);
            if (latestLapTime <= fastestLapTime) {
                latestRecord[1] = "Fastest";
                fastestRecord[1] = "";
                lapRecords.set(lapRecords.size() - 1, latestRecord);
                lapRecords.set(fastestLap, fastestRecord);
                fastestLap = latestRecordIndex;
            } else if (latestLapTime >= slowestLapTime) {
                latestRecord[1] = "Slowest";
                slowestRecord[1] = "";
                lapRecords.set(lapRecords.size() - 1, latestRecord);
                lapRecords.set(slowestLap, slowestRecord);
                slowestLap = latestRecordIndex;
            } else {
                // do nothing, the latest lap is neither fastest nor slowest
            }
        }
    }
}
